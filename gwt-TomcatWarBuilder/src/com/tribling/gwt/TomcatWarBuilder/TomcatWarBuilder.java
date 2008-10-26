package com.tribling.gwt.TomcatWarBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * build the WAR for Apache Tomcat servlet deployment
 * 
 * This code works great for building your gwt projects and deploying them with Tomcat manager
 * check http://gwt-examples.googlecode.com for updates.
 * 
 * http://localhost:8081/manager/html - upload your file and wala, your done
 * In my opinion using the Tomcat manager is so fast and efficient to deploy 
 * and application, I'll never change to another platform.
 * 
 * Built for linux and windows compiling, haven't had a chance to test this on a mac, 
 * but should work the same as the linux version
 * 
 * @author branflake2267 - Brandon Donnelson
 * 
 * Watch Console for output. Vars will be outputed there.
 * 
 * 
 * 
 * Wants In Future TODO - deploy to tomcat automatically 
 * TODO - fix gwt-files classpath in project-compile after import of svn project?
 * 
 * Documentation http://tomcat.apache.org/tomcat-6.0-doc/index.html
 */
public class TomcatWarBuilder {

	// gwt-linux location
	private String gwtHome;

	// add this if your project-compile class path is relative
	// Add trailing slash "/dir/dir/gwt-linux/"
	// private String GWT_HOME_Manual = "/opt/gwt-linux/";
	private String path_GWT_HOME_Manual = null;

	// proejct vars
	private String projectCompileFile; // project compile file location
	private String projectCompileFileContents;
	private String classPackage;
	private String projectName;
	private String[] projectDirs;
	private String projectDir;
	private String projectGWTxmlFile;
	private String projectGWTxmlFileLocation;
	private String projectGWTxmlFileContents;
	private String servletClassName; // xml servlet class - server side class
	private String rpcServicePath; // xml servlet path
	private String servletClassNameIMPL;
	// private String[] ServerProjectDirs; //skipping for now
	private String classFileContents;
	private String[] classLibs;
	private String webXML;

	// show wait while compiling
	// TODO - haven't added this yet, 
	// will add a worker to compile and loop for watching until its done
	private boolean wait = false;

	// flag notating the war compile data was set
	private boolean setData = false;

	// variables that control the compile
	private WarBuilderData data = null;

	// temp folder name
	private String tempBuildFolderFullPath;

	/**
	 * constructor - make the instance
	 */
	public void TomCatWarBuilder() {
		// nothing to do yet
	}

	/**
	 * start the build process
	 * 
	 * NOTE: don't forget to set the war builder data first
	 */
	public void start() {
		if (setData == false) {
			System.out.println("set the WarCompileData first");
			System.exit(1);
		}
		startProcess();
	}

	/**
	 * set the vars the control the war build - set this first
	 * 
	 * @param data
	 */
	public void setTomcatCompileData(WarBuilderData data) {
		setData = true;
		this.data = data;
	}

	/**
	 * TODO save this for later - then get rid of
	 */
	private void old() {

		// project directory (examples below)
		data.projectDirectory = "/home/branflake2267/workspace/gwt-SandBox";
		// ProjectDirectory = "/home/branflake2267/workspace/gwt-GV";
		// ProjectDirectory = "/home/branflake2267/workspace/gwt-Feedback";
		// ProjectDirectory =
		// "/home/branflake2267/workspace/gwt-test-DisplayDate";
		// ProjectDirectory =
		// "/home/branflake2267/workspace/gwt-test-Clicklistener";
		// ProjectDirectory = "/home/branflake2267/workspace/gwt-test-RPC-adv";
		// ProjectDirectory =
		// "/home/branflake2267/workspace/gwt-test-Login-Manager";
		// ProjectDirectory = "/home/branflake2267/workspace/gwt-Calendar";
		// ProjectDirectory =
		// "/home/branflake2267/workspace/gwt-test-UrchinTracker";
		// ProjectDirectory = "/home/branflake2267/workspace/gwt-test-History";
		// ProjectDirectory =
		// "/home/branflake2267/workspace/gwt-test-RichTextEditor";
		// ProjectDirectory =
		// "/home/branflake2267/workspace/gwt-test-MySQLConn";

	}

	/**
	 * process everything - and build war archive
	 * 
	 * @throws Exception
	 */
	private void startProcess() {

		// testing - todo
		getCurrentProjectDirectory();

		// set Temp Build Folder
		setTempBuildFolder(); // use gwt-project/production for now.

		// find file project-compile to read its contents
		checkProjectListForCompile();

		// read the compile file ./project.compile
		readProjectCompileFile();

		// figure Compile Vars
		getProjectVars();

		// Compile Project
		compileProject();

		/* START WAR BUILD */

		// delete previous production build
		deleteProductionFolder(0);

		// create directories for production build
		createDirectoriesForBuild();

		// create web xml file
		createWebXMLFile();

		// create index.jsp page
		createIndexPage();

		// copy www, jars, classes to production folder
		copyFilesToProductionBuildFolder();

		// zip into war file
		zipProject();

		// rename the war file to desired name
		renameWar();

		// TODO - upload to production/design tomcat server
		//http://localhost:8080/manager/deploy?path=/footoo&war=file:/path/to/foo

		// delete production folder when done
		if (data.deleteTempFolder == true) {
			deleteProductionFolder(1);
		}

		// Done with everything
		System.out.println("");
		System.out.println("All Done!");
	}

	/**
	 * get project Vars
	 * 
	 * @throws Exception
	 */
	private void getProjectVars() {

		// GWT_HOME location
		getGwtHome();

		// ProjectClassName
		getProjectClassName();

		// figure the project directory
		getProjectDirectoryFromClassName();

		// Project Name from getProjectDirectoryFromClassName
		getProjectName();

		// figure out the name of the projects xml file
		getProjectsXMLFile();

		// read project.gwt.xml file
		readProjectXMLFileContents();

		// servlet class name - from client side
		getServletClassFromXMLFile();

		// get servlet path
		getServeletUrlPath();

		// get servlet Name (server class name)
		getServerClassNameIMPL();

		// get class path contents
		getClassPathContents();

		// get class libs
		getClassLibs();
	}

	/**
	 * create the servlet /WEB-INF/web.xml file
	 * 
	 * Reference http://tomcat.apache.org/tomcat-6.0-doc/config/context.html
	 * http://www.caucho.com/resin/doc/filters.xtp
	 * 
	 * 
	 * Name your application ROOT.war to make it the root application for your
	 * domain
	 * 
	 * 
	 * <error-page> <error-code>404</error-code> <location>/404.html</location>
	 * </error-page>
	 */
	private String createWebXMLFileContents() {

		// xml definition
		String WebXML = ""
				+ "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n"
				+

				"<web-app id=\"/\" xmlns=\"http://java.sun.com/xml/ns/j2ee\" "
				+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
				+ "xsi:schemaLocation=\"http://java.sun.com/xml/ns/j2ee "
				+ "http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd\" "
				+ "version=\"2.4\">\n"
				+

				// servlet name and description
				"\t<display-name>gwt-" + projectName + " Compiled: "
				+ getDate() + "</display-name>\n"
				+ "\t<description>Google Web Toolkit Project</description>\n";

		// test
		/*
		 * couldn't make this work yet WebXML += "" + "<context-param>\n" +
		 * "\t<param-name>path</param-name>\n" +
		 * "\t<param-value>/what</param-value>\n" + "</context-param>\n" +
		 * "<context-param>\n" + "\t<param-name>docBase</param-name>\n" +
		 * "\t<param-value>" + ProjectName + "</param-value>\n" +
		 * "</context-param>\n";
		 */

		// use welcome file list to go directly to the application
		if (data.goDirectlyToApplication == true) {
			WebXML += "" + "<welcome-file-list>\n"
					+ "\t<welcome-file>"
					+ projectName
					+ ".html</welcome-file>\n"
					+ // go directly to the applications homepage
					"\t<welcome-file>index.jsp</welcome-file>\n"
					+ // you could use these
					"\t<welcome-file>index.html</welcome-file>\n"
					+ "\t<welcome-file>index.htm</welcome-file>\n"
					+ "</welcome-file-list>\n";
		}

		// enable web page authentication to tomcat-users.xml
		if (servletClassName != null) {
			WebXML += "" + "\n<!-- reference project.gwt.xml for servlet class path  -->\n" + "<servlet>\n"
					+ "\t<servlet-name>" + projectName + "</servlet-name>\n"
					+ "\t<servlet-class>" + servletClassName
					+ "</servlet-class>\n" + "</servlet>\n"
					+ "<servlet-mapping>\n" + "\t<servlet-name>" + projectName
					+ "</servlet-name>\n" + "\t<url-pattern>" + rpcServicePath
					+ "</url-pattern>\n" + "</servlet-mapping>\n";
		}

		// add user security if turned on
		WebXML += createWebXMLFileContents_Security();
		WebXML += "</web-app>\n";

		// optional
		WebXML += "\n<!-- Compiled by the TomcatWarBuilder on http://gwt-examples.googlecode.com -->\n";

		return WebXML;
	}

	/**
	 * add security to web.xml file - Need to login to view servlet application
	 * 
	 * Roles are in /etc/tomcat5.5/tomcat-users.xml - roles are like
	 * groups - user needs to be apart of role. You can change the role below
	 * to a different role in the list, as long as the users are part of
	 * that role that want to login
	 * 
	 * Recommendation, after setting youself up as a user in tomcat-users.xml
	 * use the http://localhost:8081/admin (tomcat admin) to administor the tomcat configuration
	 * 
	 * @return 
	 */
	private String createWebXMLFileContents_Security() {
		String WebXML = ""
				+ "<!-- Built by TomcatWarBuilder - http://gwt-examples.googlecode.com -->\n\n"
				+ "\n<!-- Define a Security Constraint on this Application -->\n"
				+ "<security-constraint>\n"
				+ "\t<web-resource-collection>"
				+ "\t<web-resource-name>Entire Application</web-resource-name>\n"
				+ "\t<url-pattern>/*</url-pattern>\n"
				+ "\t</web-resource-collection>\n"
				+ "\t<auth-constraint>\n"
				+ "\t<role-name>admin</role-name>\n"
				+ "\t</auth-constraint>\n"
				+ "</security-constraint>\n\n"
				

				+ "<login-config>\n"
				+ "\t<auth-method>BASIC</auth-method>\n"
				+ "\t<realm-name>Your Realm Name</realm-name>\n" // something a person will recognise when the credentials question pops up
				+ "</login-config>\n\n"
				

				+ "<security-role>\n"
				+ "\t<description>The role that is required to log in to the Manager Application</description>\n"
				+ "\t<role-name>admin</role-name>\n" + "</security-role>\n\n"; // this role has to exist in tomcat-users.xml! use localhost:8080/admin to mange it.

		if (data.askForLogin == true) {
			return WebXML;
		} else {
			return "";
		}
	}

	/**
	 * get todays date for adding to the project name in the xml file
	 * gives a bearing of when we built this project 
	 * 
	 * @return
	 */
	private String getDate() {
		Date date = new Date();

		String now;
		try {
			now = date.toString();
		} catch (Exception e) {
			e.printStackTrace();
			now = " getDate(); error ";
		}

		return now;
	}

	/**
	 * create an index page for easy application access, if not directly going to app. 
	 * This can be used for quickly testing, especially if your using your application as an include
	 * on another site.
	 * 
	 * @return
	 */
	private String createIndexPageContents() {

		String linkToModle = projectName + ".html";
		String linkToModuleDesc = linkToModle;

		String indexPage = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n"
				+ "<html>\n"
				+ "<body>"
				+ ""
				+ "\t<a href=\""
				+ linkToModle
				+ "\">"
				+ linkToModuleDesc
				+ "</a> Quick link to your gwt module."
				+ "</body>"
				+ "</html>\n";

		return indexPage;
	}

	/**
	 * create file ./production/index.jsp
	 */
	private void createIndexPage() {

		String sep = getPathsFileSeparator();

		String File = data.tempBuildFolderName + sep + "index.jsp";
		createFile(File, createIndexPageContents());
	}

	/**
	 * Get the project directory this script resides in
	 * 
	 * figure out how to get the path of this script
	 */
	private void getCurrentProjectDirectory() {
		// don't know how to get the eclipse project directory yet that this
		// file is in
	}

	/**
	 * setup temp build folder, staging area for files
	 */
	private void setTempBuildFolder() {

		String sep = getPathsFileSeparator();
		this.tempBuildFolderFullPath = data.projectDirectory + sep + data.tempBuildFolderName;
	}

	/**
	 * delete the previous production folder
	 */
	private void deleteProductionFolder(int when) {

		String s = "";
		if (when == 0) {
			s = "Previous";
		}

		
		File dir = new File(tempBuildFolderFullPath);

		System.out.println("Deleting " + s + " production directory contents: "	+ tempBuildFolderFullPath);

		// delete the production folder contents
		try {
			deleteDir(dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * delete folder contents
	 * 
	 * @param dir
	 * @return
	 */
	private boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	/**
	 * create file ./production/WEB-INF/web.xml
	 */
	private void createWebXMLFile() {

		String sep = getPathsFileSeparator();

		String File = data.tempBuildFolderName + sep + "WEB-INF" + sep + "web.xml";
		createFile(File, createWebXMLFileContents());
	}

	/**
	 * create Folders for build
	 */
	private void createDirectoriesForBuild() {

		System.out.println("");
		System.out.println("Starting Build");

		String sep = getPathsFileSeparator();

		// create ./production folder for staging
		String ProductionFolder = tempBuildFolderFullPath;
		createFolder(ProductionFolder);

		// create folder ./production/WEB-INF
		String ProductionWebInf = tempBuildFolderFullPath + sep + "WEB-INF";
		createFolder(ProductionWebInf);

		// create folder ./production/WEB-INF/classes
		String ProductionWebInfClasses = tempBuildFolderFullPath + sep + "WEB-INF" + sep + "classes";
		createFolder(ProductionWebInfClasses);

		// create folder ./production/WEB-INF/lib
		String ProductionWebInfLib = tempBuildFolderFullPath + sep + "WEB-INF" + sep + "lib";
		createFolder(ProductionWebInfLib);
	}

	/**
	 * copy the files in the correct folders
	 * 
	 * @throws IOException
	 */
	private void copyFilesToProductionBuildFolder() {

		// copy the www file
		copyWWWFiles();

		// copy the compiled classes
		copyCompiledClasses();

		// copy class jars to libs folder
		copyJarFiles();
	}

	/**
	 * copy the compiled classes to /production/WEB-INF/classes
	 */
	private void copyCompiledClasses() {

		String sep = getPathsFileSeparator();
		String src = data.projectDirectory + sep + "bin";
		String dest = tempBuildFolderFullPath + sep + "WEB-INF" + sep	+ "classes";

		try {
			copyFiles(src, dest);
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}

	}

	/**
	 * copy files - ./www to ./production/
	 * 
	 * www is the compiled project directory production is the temp directory
	 * for setting up the war archive
	 * 
	 * Notes Archive/www/*files - have to change the servelt context path if you
	 * change this String dest = TempBuildFolder + "/www";
	 */
	private void copyWWWFiles() {

		System.out.println("Copying gwt WWW files to the temp directory");

		String sep = getPathsFileSeparator();

		// source directory
		String src = data.projectDirectory + sep + "www" + sep	+ classPackage;

		// destination directory
		String dest = tempBuildFolderFullPath;

		// copy files
		try {
			copyFiles(src, dest);
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	/**
	 * copy class jars to the libs folder in the temp production folder
	 * 
	 * @throws IOException
	 */
	private void copyJarFiles() {

		String sep = getPathsFileSeparator();

		System.out.println("Copying Jars" + classLibs.length + " (Using .classpath for locating jars)");

		String src = null;
		String dest = null;

		for (int i = 0; i < (classLibs.length); i++) {
			src = classLibs[i];
			String DestFile = getDestinationDirectory(src);
			dest = tempBuildFolderFullPath + sep + "WEB-INF" + sep + "lib" + sep + DestFile;

			try {
				copyFiles(src, dest);
			} catch (IOException e) {
				System.err.println(e);
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

	/**
	 * get class path file contents search .classpath for libs to add
	 */
	private void getClassPathContents() {

		String sep = getPathsFileSeparator();

		String classpathfilelocation = data.projectDirectory + sep + ".classpath";

		File file = new File(classpathfilelocation);

		if (file.isFile() == false) {
			System.out.println("Can't read the .classpath file or it does not exist");
			System.exit(1);
		}

		classFileContents = readFile(file);

		System.out.println("ClassFileContents: " + classFileContents);
	}

	/**
	 * get jar files location
	 * 
	 */
	private void getClassLibs() {
		
		String sep = getPathsFileSeparator();

		// kind=['|\"]lib['|\"].*?
		String re = "path=['\"](" + sep + ".*?jar)['\"]";
		Pattern p = Pattern.compile(re);
		Matcher matcher = p.matcher(classFileContents);

		// Count the matches first
		int i = 0;
		while (matcher.find()) {
			// System.out.println("Found Lib in ClassPath: " + i);
			i++;
		}

		// init the array to proper size
		String[] jars = new String[i];

		matcher.reset();// reset position

		// save the jars to array
		i = 0;
		while (matcher.find()) {
			String match = matcher.group(1);
			System.out.println("Class libs: " + i + ". " + match);
			jars[i] = match;
			i++;
		}

		// need gwt-servlet?? is there an rpc method? -lets do it by default
		boolean addGwtServlet = true;

		// get rid of gwt-user.jar - won't need it for the server side
		jars = deleteGwtUserJar(jars, addGwtServlet);

		classLibs = jars;
	}

	/**
	 * Switch out gwt-user.jar with gwt-servlet.jar
	 * 
	 * @param jars
	 * @param AddGwtServlet
	 *            - tell it to replace gwt-servlet.jar and not erase it
	 * @return
	 */
	private String[] deleteGwtUserJar(String[] jars, boolean AddGwtServlet) {
		String jar = null;
		for (int i = 0; i < jars.length; i++) {
			jar = jars[i].toString();

			boolean has = jars[i].contains("gwt-user.jar");
			if (has == true) {
				if (AddGwtServlet == true) {
					System.out.println("ClassLibs Changing gwt-user.jar to gwt-servlet.jar");
					jars[i] = gwtHome + "gwt-servlet.jar";
				} else {
					jars[i] = null;
				}
			}
		}
		return jars;
	}

	/**
	 * get projects client xml settings
	 */
	private void getProjectsXMLFile() {

		String sep = getPathsFileSeparator();

		int depth = projectDirs.length - 1;
		String projectName = projectDirs[depth];

		projectGWTxmlFile = projectName + ".gwt.xml";
		projectGWTxmlFileLocation = data.projectDirectory + sep + "src" + projectDir + sep + projectGWTxmlFile;

		System.out.println("ProjectGWTxmlFile: " + projectGWTxmlFile);
		System.out.println("ProjectGWTxmlFileLocation: " + projectGWTxmlFileLocation);
	}

	/**
	 * read xml files contents
	 * 
	 * @throws Exception
	 */
	private void readProjectXMLFileContents() {

		File file = new File(projectGWTxmlFileLocation);
		projectGWTxmlFileContents = readFile(file);

		if (projectGWTxmlFileContents == null) {
			System.out.println("Could not read project.gwt.xml file. (" + projectGWTxmlFileLocation + ")");
		}
		System.out.println("ProjectGWTxmlFileContents: " + projectGWTxmlFileContents);
		
		if (projectGWTxmlFileContents.length() == 0) {
			System.out.println("Could not read your GWT XML file. debug: readProjectXMLFileContents");
			System.exit(1);
		}

	}

	/**
	 * get servlet class
	 * 
	 */
	private void getServletClassFromXMLFile() {

		Pattern p = Pattern.compile("<servlet.*?class=[\"'](.*?)[\"'].*?>");
		Matcher m = p.matcher(projectGWTxmlFileContents);
		boolean found = m.find();

		if (found == true) {
			servletClassName = m.group(1);
		}

		// debug
		System.out.println("ServletClassName: " + servletClassName);
		System.out.println("");
	}

	/**
	 * get servlet path
	 * 
	 * This is the url-pattern, or rpcServicePath. 
	 * Or servlet context that redirects to the rpc class
	 */
	private void getServeletUrlPath() {

		// entry-point class
		Pattern p = Pattern.compile("<entry-point.*?path.*?=.*?['\"](.*?)['\"].*?>");
		Matcher m = p.matcher(projectGWTxmlFileContents);
		boolean found = m.find();

		if (found == true) {
			rpcServicePath = m.group(1);
		}

		// debug
		System.out.println("ServletPath: " + rpcServicePath);
	}

	/**
	 * get the directory structure
	 * name and no . in it
	 * 
	 * "com.domain.gwt.class.client.class"
	 */
	private void getProjectDirectoryFromClassName() {

		String sep = getPathsFileSeparator();
		
		String[] dirs = null;
		if (classPackage.contains(".")) {
			dirs = classPackage.split("\\.");
		} else {
			dirs[0] = new String(classPackage);
		}

		projectDirs = dirs; // can use this else where

		String dir = "";
		for (int i = 0; i < (dirs.length - 1); i++) {
			dir = dir + sep + dirs[i].toString();
		}

		projectDir = dir;
		
		System.out.println("ProjectDir: " + dir);
	}

	/**
	 * get project name from class name
	 */
	private void getProjectName() {
		projectName = projectDirs[projectDirs.length - 1].toString();
		System.out.println("ProjectName: " + projectName);
	}

	/**
	 * get path/directory in array
	 * 
	 * @param File
	 * @return
	 */
	private String getDestinationDirectory(String File) {

		String sep = getPathsFileSeparator();

		String[] dirs = File.split(sep);
		String dir = dirs[dirs.length - 1];
		return dir;
	}

	/**
	 * get the server class name "ServiceImpl"
	 * 
	 * "com.domain.gwt.class.client.class"
	 */
	private void getServerClassNameIMPL() {

		if (servletClassName == null) {
			return;
		}

		// split the class into dir
		String[] dirs = null;
		if (servletClassName.contains(".")) {
			dirs = servletClassName.split("\\.");
		} else {
			dirs[0] = new String(servletClassName);
		}

		servletClassNameIMPL = dirs[dirs.length - 1].toString();

		System.out.println("ServletClassNameIMPL: " + servletClassNameIMPL);
	}

	/**
	 * get the package name (and class) that the src files are in
	 * com.tribling.gwt.test.client.MyProject or com.tribling.gwt.test.client.MyProject;
	 * 
	 */
	private void getProjectClassName() {

		//Pattern p = Pattern.compile("\"\\$@\"\040(.*?);"); 
		Pattern p = Pattern.compile(".*\040(.*?);|.*\040(.*?)");
		Matcher m = p.matcher(projectCompileFileContents);
		boolean found = m.find();

		if (found == true) {
			classPackage = m.group(1);
		}

		// debug
		System.out.println("classPackage: " + classPackage);
		System.out.println("");
	}

	/**
	 * get the location for the gwt files from eclipse class path contents
	 * 
	 * TODO - errors when the class path is relative
	 */
	private void getGwtHome() {

		String sep = getPathsFileSeparator();
		
		if (path_GWT_HOME_Manual != null) {
			gwtHome = path_GWT_HOME_Manual;
			return;
		}
		
		String re = ":(" + sep + ".*)gwt-user.jar:";
		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(projectCompileFileContents);
		boolean found = m.find();

		if (found == true) {
			gwtHome = m.group(1);
		} else {
			System.out.println("Can't find gwtHome Directory, in the ProjectCompileFileContents Classpath. debug getClassPathContents()");
			System.exit(1);
		}

		// debug
		System.out.println("gwtHome dir: " + gwtHome);
	}

	/**
	 * read the gwt-compile folder to get contents
	 */
	private void readProjectCompileFile() {

		String sep = getPathsFileSeparator();

		String directory = data.projectDirectory + sep + projectCompileFile;
		File ProjectCompileFile = new File(directory);

		projectCompileFileContents = readFile(ProjectCompileFile);

		System.out.println("FileContents: " + projectCompileFileContents);

	}

	/**
	 * read the project xml file to get contents
	 * 
	 * note: this isn't used at the moment can't remember why
	 */
	private void readProjectGWTxmlFile() {

		String sep = getPathsFileSeparator();

		String Dir = data.projectDirectory + sep + projectGWTxmlFile;
		File ProjectCompileFile = new File(Dir);

		projectGWTxmlFileContents = readFile(ProjectCompileFile);
		System.out.println("FileContents: " + projectGWTxmlFileContents);
	}

	/**
	 * get file list in the directory (find project-compile)
	 * 
	 * looking for project-compile, which builds the project from console
	 */
	private void checkProjectListForCompile() {
		File file;
		String FileList[];

		// ls the directory for files
		file = new File(data.projectDirectory);
		if (file.isDirectory() == false) {
			System.out.println("Your data.ProjectDirectory is not a directory or does not exist. (debug: " + data.projectDirectory + ")");
			System.out.println("Make sure you have the correct path");
			System.exit(1);
		}
		
		FileList = file.list();
		if (FileList == null) {
			System.out.println("Error reading current directory. debug: checkProjectListForCompile()");
			System.exit(1);
		}

		// look for ./project-compile
		findProjectCompile(FileList);
	}

	/**
	 * find the project gwt-compile file
	 * 
	 * @param FileList
	 */
	private void findProjectCompile(String[] FileList) {

		String file = null;
		boolean found = false;

		for (int i = 0; i <= FileList.length; i++) {
			file = FileList[i];
			found = checkForCompile(file);

			if (found == true) {
				projectCompileFile = file;
				return;
			}
		}
	}

	/**
	 * get the file that says project-compile
	 * 
	 * @param file
	 * @return
	 */
	private boolean checkForCompile(String file) {

		if (file == null) {
			return false;
		}
		boolean found = false;

		// create a Pattern object
		Pattern p = Pattern.compile(".*compile");
		Matcher m = p.matcher(file);
		found = m.find();

		return found;
	}

	/**
	 * create new directory
	 * 
	 * @param Dir
	 * @return
	 */
	private boolean createFolder(String Dir) {
		boolean status = false;
		String newDir = null;

		if (Dir != null) {
			newDir = Dir;
			status = new File(newDir).mkdir();
		}

		// debug
		System.out.println("Created Production Dir: " + newDir);

		return status;
	}

	/**
	 * read file contents
	 * 
	 * @param file
	 * @return
	 */
	private String readFile(File file) {

		if (file.length() == 0) {
			System.out.println("Could not read file; debug: readFile()");
		}

		String sFile = null;
		try {
			FileReader input = new FileReader(file);
			BufferedReader bufRead = new BufferedReader(input);

			String line;
			int count = 0;

			// Read first line
			line = bufRead.readLine();
			sFile = line;
			count++;

			// Read through file one line at time. Print line # and line
			while (line != null) {
				line = bufRead.readLine();
				if (line != null) {
					sFile = sFile + line;
				}
				count++;
			}
			bufRead.close();

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Usage: java ReadFile filename\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sFile;
	}

	/**
	 * copy files
	 * 
	 * @param strPath
	 * @param dstPath
	 * @throws IOException
	 */
	private void copyFiles(String strPath, String dstPath) throws IOException {

		File src = new File(strPath); // source
		File dest = new File(dstPath); // destination

		if (src.isDirectory() == true && src.getName() != ".svn") { // skip .svn files

			dest.mkdirs();
			String list[] = src.list();

			for (int i = 0; i < list.length; i++) {

				String src1 = src.getAbsolutePath() + "/" + list[i];
				String dest1 = dest.getAbsolutePath() + "/" + list[i];

				if (list[i].toString().matches(".svn") != true) { // skip .svn
					copyFiles(src1, dest1);
				}
			}
			
		} else {

			FileInputStream fin = new FileInputStream(src);
			FileOutputStream fout = new FileOutputStream(dest);

			System.out.println("Copying file src:" + src + " dest:" + dest);

			int c;
			while ((c = fin.read()) >= 0) {
				fout.write(c);
			}

			fin.close();
			fout.close();

		}
	}

	/**
	 * create file with these contents
	 * 
	 * @param createFilePath
	 * @param Contents
	 */
	private void createFile(String createFilePath, String Contents) {

		try {
			File file = new File(createFilePath);
			boolean success = file.createNewFile();
			if (success) {
				BufferedWriter out = new BufferedWriter(new FileWriter(createFilePath,true));
				out.write(Contents);
				out.close();

			} else {
				(new File(createFilePath)).delete();
				createFile(createFilePath, Contents);
			}
		} catch (IOException e) {
			System.out.println("Could not create file: (debug createFilePath: " + createFilePath + ")");
			System.out.println("Do you have write permission for this location?");
			System.exit(1);
		}

	}

	/**
	 * zip up the production folder
	 * 
	 * WAR archive is a zip archive
	 * 
	 * @throws IOException
	 */
	private void zipProject() {

		System.out.println("Zipping up archive. WAR file generation");

		String zipFile = getWarFilePathAndName();
		String zipUp = tempBuildFolderFullPath;

		System.out.println("WarZipFile: " + zipFile + " ZipUpDir:" + zipUp);

		FileOutputStream file = null;
		try {
			file = new FileOutputStream(zipFile);
		} catch (FileNotFoundException e) {
			System.out.println("Could not make the zip file. debug: zipProject");
			e.printStackTrace();
			System.exit(1);
		}

		// create a ZipOutputStream to zip the data to
		ZipOutputStream zos = new ZipOutputStream(file);
		zipTempDir(zipUp, zos);

		try {
			zos.close();
		} catch (IOException e) {
			System.out.println("could not close ZipOutputStream");
			e.printStackTrace();
		} // close the stream
	}

	/**
	 * get war file with entire path
	 * 
	 * @return
	 */
	private String getWarFilePathAndName() {
		String sep = getPathsFileSeparator();
		String warFile = null;
		if (data.tempWarFileLocation == null) {
			warFile = data.projectDirectory + sep + projectName + ".war";
		} else {
			warFile = data.tempWarFileLocation + sep + projectName + ".war";
		}
		return warFile;
	}

	/**
	 * get war directory path
	 * 
	 * @return
	 */
	private String getWarDir() {
		String warDir = null;
		if (data.tempWarFileLocation == null) {
			warDir = data.projectDirectory;
		} else {
			warDir = data.tempWarFileLocation;
		}
		return warDir;
	}

	/**
	 * archive temp build folder
	 * 
	 * @param dir2zip
	 * @param zos
	 */
	private void zipTempDir(String dir2zip, ZipOutputStream zos) {
		try {
			File zipDir = new File(dir2zip);
			String[] dirList = zipDir.list();

			byte[] readBuffer = new byte[2156];
			int bytesIn = 0;

			// loop through dirList, and zip the files
			for (int i = 0; i < dirList.length; i++) {
				File f = new File(zipDir, dirList[i]);
				if (f.isDirectory()) {
					String filePath = f.getPath();
					zipTempDir(filePath, zos);
					continue;
				}

				// if we reached here, the File object f was not a directory
				// create a FileInputStream on top of f
				FileInputStream fis = new FileInputStream(f);

				// create a new zip entry
				String filePath = f.getPath();
				
				// change the path to relative
				filePath = getZipPath(filePath); 
				
				// to the production dir
				ZipEntry anEntry = new ZipEntry(filePath);

				// place the zip entry in the ZipOutputStream object
				zos.putNextEntry(anEntry);

				// now write the content of the file to the ZipOutputStream
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
				// close the Stream
				fis.close();
			}
		} catch (Exception e) {
			System.out.println("Could not do the zipDir process. debug zipDir()");
			System.out.println("This would be an internal variable configuration error. I guess you'll have to debug it");
			System.exit(1);
		}
	}

	/**
	 * Figure out the build path, so we can set the zip archive to relative path
	 * 
	 * TODO - veryify name change works
	 * 
	 * @param path
	 * @return
	 */
	private String getZipPath(String path) {

		String sep = getPathsFileSeparator();
		
		String tmp = data.tempBuildFolderName + sep; 
		
		// production/(file)
		String re = ".*" + tmp + "(.*)";
		Pattern p = Pattern.compile(re);
		Matcher m = p.matcher(path);
		boolean found = m.find();

		String newPath = "";
		if (found == true) {
			newPath = m.group(1);
		}
		
		// debug
		System.out.println("Copying to zip archive (war): " + newPath);
		
		return newPath;
	}

	/**
	 * rename war file to desired name
	 */
	private void renameWar() {

		String sep = getPathsFileSeparator();

		if (data.renameWarFile == false && data.renameWarFileNameTo != null) {
			return;
		}

		if (data.renameWarFileNameTo.equals("")) {
			System.out.println("renameWarFielNameTo is null, add that field");
		}

		String warFile = getWarFilePathAndName();
		String warDir = getWarDir();

		String newName = warDir + sep + data.renameWarFileNameTo;

		File file = new File(warFile);
		File file2 = new File(newName);

		boolean success = file.renameTo(file2);
		if (!success) {
			System.out.println("failed to rename war.");
		} else {
			System.out.println("Rename War: " + file2);
		}
	}

	/**
	 * get file path seperator for operating system
	 * 
	 * @return
	 */
	private String getPathsFileSeparator() {
		
		String s = "/";
		if (data.os == 0) {
			s = "/";
		} else if (data.os == 1) {
			s = "\\";
		}

		// debug
		if (s.length() == 0) {
			System.out.println("no os was selected");
			System.out.println("select an os number 0 or 1, this determines what type of file system exists.");
			System.exit(1);
		}

		return s;
	}

	/**
	 * compile the eclipse project first
	 * 
	 * TODO - change permissions on folder if on os 0, so to not have to worry about manual exec permission
	 */
	private void compileProject() {

		String sep = getPathsFileSeparator();
		String runPath = data.projectDirectory + sep + projectCompileFile;

		System.out.println("Compiling gwt application - This may take a minute to complete! Be patient.");

		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(runPath);
			proc.waitFor(); // TODO - show wait - need a worker
		} catch (Throwable t) {
			System.out.println("If you get a permission error on linux, 'chmod 777 project-compile'");
			System.out.println("Was not able to exec the project-compile file to compile project");
			t.printStackTrace();
			System.exit(1);
		}

		System.out.println("Done compiling gwt application");

	}

	/**
	 * TODO - in the future use a worker to show the wait during compile
	 */
	private void showWait() {
		int i = 0;
		do {
			System.out.print(".");
			if (i == 40) {
				i = 0;
				System.out.println(".");
			}
			i++;
		} while (wait == true);

	}

}// end
