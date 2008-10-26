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
 * @author branflake2267 - Brandon Donnelson
 *
 * Watch Console for output. Vars will be outputed there.  
 *
 *
 * 
 * Wants In Future
 * TODO - deploy to tomcat automatically
 * TODO - fix gwt-files classpath in project-compile after import? 
 * 
 * Documentation
 * http://tomcat.apache.org/tomcat-6.0-doc/index.html
 */
public class TomcatWarBuilder {

	// gwt-linux location
	private  String GWT_HOME;
	
	//add this if your project-compile class path is relative
	//Add trailing slash "/dir/dir/gwt-linux/"
	//private  String GWT_HOME_Manual = "/opt/gwt-linux/";
	private  String path_GWT_HOME_Manual = null;

	// proejct vars
	private  String projectCompileFile; // project compile file location
	private  String projectCompileFileContents;
	private  String projectModuleName;
	private  String projectName;
	private  String[] projectDirs;
	private  String projectDir;
	private  String projectGWTxmlFile;
	private  String projectGWTxmlFileLocation;
	private  String projectGWTxmlFileContents;
	private  String servletClassName; // xml servlet class - server side class for rpc
	private  String servletPath; // xml servlet path
	private  String servletClassNameIMPL;
	// private  String[] ServerProjectDirs; //skipping for now
	private  String classFileContents;
	private  String[] classLibs;
	private  String webXML;
	
	// show wait while compiling
	private boolean wait = false;
	
	// flag notating the war compile data was set
	private boolean setData = false;

	// variables that control the compile
	private WarCompileData cData = null;
	
	/**
	 * constructor - make the instance
	 * 
	 */
	public void TomCatWarBuilder() {
		//nothing to do yet
	}
	
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
	public void setTomcatCompileData(WarCompileData data) {
		setData = true;
		cData = data;
	}
	
	/**
	 * TODO save this for later - then get rid of
	 */
	private void old() {
		
		// project directory (examples below)
		cData.projectDirectory = "/home/branflake2267/workspace/gwt-SandBox";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-GV";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-Feedback";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-test-DisplayDate";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-test-Clicklistener";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-test-RPC-adv";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-test-Login-Manager";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-Calendar";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-test-UrchinTracker";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-test-History";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-test-RichTextEditor";
		//ProjectDirectory = "/home/branflake2267/workspace/gwt-test-MySQLConn";

	}
	
	/**
	 * process everything - and build war archive
	 * @throws Exception 
	 */
	private void startProcess() {
		
		//testing - todo
		getCurrentProjectDirectory();
		
		//set Temp Build Folder 
		setTempBuildFolder(); //use gwt-project/production for now.
		
		// find file project-compile to read its contents
		checkProjectListForCompile();

		// read the compile file ./project.compile
		readProjectCompileFile();

		// figure Compile Vars
		getProjectVars();

		//Compile Project
		compileProject();
		
		
		/* START WAR BUILD */

		// delete previous production build
		deleteProductionFolder(0);
		
		// create directories for production build
		createDirectoriesForBuild();

		//create web xml file
		createWebXMLFile();
		
		//create index.jsp page
		createIndexPage();
		
		//copy www, jars, classes to production folder
		copyFilesToProductionBuildFolder();

		//zip into war file
		zipProject();

		// rename the war file to desired name
		renameWar();
		
		
		
		// TODO - upload to production/design tomcat server
		// http://localhost:8080/manager/deploy?path=/footoo&war=file:/path/to/foo

		
		// delete production folder when done
		if (cData.deleteTempFolder == true) {
			deleteProductionFolder(1);
		}
		
		//Done with everything
		System.out.println("");
		System.out.println("All Done!");
	}
	
	/**
	 * get project Vars
	 * @throws Exception 
	 */
	private void getProjectVars() {
		
		// GWT_HOME location
		getGWT_HOME();

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
	 * Reference
	 * http://tomcat.apache.org/tomcat-6.0-doc/config/context.html
	 * http://www.caucho.com/resin/doc/filters.xtp
	 *
	 *
	 * Name your application ROOT.war to make it the root application for your domain
	 * 
     *
		<error-page>
		    <error-code>404</error-code>
		    <location>/404.html</location>
		</error-page>

	 */
	private String createWebXMLFileContents() {
		
		// xml definition
		String WebXML = "" +
			"<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
		
			"<web-app id=\"/\" xmlns=\"http://java.sun.com/xml/ns/j2ee\" " +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
				"xsi:schemaLocation=\"http://java.sun.com/xml/ns/j2ee " +
				"http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd\" " +
				"version=\"2.4\">\n" +
				
		  // servlet name and description
		  "\t<display-name>gwt-" + projectName + " Compiled: " + getDate() + "</display-name>\n" +
		  "\t<description>Google Web Toolkit Project</description>\n";
		
		
		// test
		/* couldn't make this work yet
		WebXML += "" +
			"<context-param>\n" +
				"\t<param-name>path</param-name>\n" +
				"\t<param-value>/what</param-value>\n" +
			"</context-param>\n" +
			"<context-param>\n" +
				"\t<param-name>docBase</param-name>\n" +
				"\t<param-value>" + ProjectName + "</param-value>\n" +
			"</context-param>\n";
		*/
		
		// use welcome file list to go directly to the application
		if (cData.goDirectlyToApplication == true) {
		WebXML += "" +
			 "<welcome-file-list>\n" +
			 	"\t<welcome-file>" + projectName + ".html</welcome-file>\n" + // go directly to the applications the homepage
			 	"\t<welcome-file>index.jsp</welcome-file>\n" + // you could use these
			 	"\t<welcome-file>index.html</welcome-file>\n" +
			 	"\t<welcome-file>index.htm</welcome-file>\n" +
			 "</welcome-file-list>\n";
		}
		
		// enable web page authentication to tomcat-users.xml 
		if (servletClassName != null) {
		 WebXML += "" +
		 	 "\n<!-- server side gwt stuff-->\n" +
			 "<servlet>\n" + 
			   "\t<servlet-name>" + projectName + "</servlet-name>\n" +
			   "\t<servlet-class>" + servletClassName + "</servlet-class>\n" +
			 "</servlet>\n" +
			 "<servlet-mapping>\n" +
			    "\t<servlet-name>" + projectName + "</servlet-name>\n" +
			    "\t<url-pattern>" + servletPath + "</url-pattern>\n" +
			  "</servlet-mapping>\n";
		 }
		
		//add user security if turned on
		WebXML += createWebXMLFileContents_Security();
		WebXML += "</web-app>\n";
		
		//optional
		WebXML += "\n<!-- Compiled by the TomcatWarBuilder on gwt-examples.googlecode.com -->\n";
		
		return WebXML;
	}
	
	/**
	 * add security to web.xml file - Need to login to view servlet application
	 * @return
	 * 
	 * Roles are in /etc/tomcat5.5/tomcat-users.xml - roles are like groups - user needs to be apart of role
	 * You can change role below to a different role in the list, 
	 * as long as the users are part of that role that want to login
	 */
	private String createWebXMLFileContents_Security() {
		String WebXML = "" +
		    "<!-- Built by TomcatWarBuilder - http://gwt-examples.googlecode.com -->\n\n" +
			"\n<!-- Define a Security Constraint on this Application -->\n" +
			"<security-constraint>\n" +
				"\t<web-resource-collection>" +
				"\t<web-resource-name>Entire Application</web-resource-name>\n" +
				"\t<url-pattern>/*</url-pattern>\n" +
				"\t</web-resource-collection>\n" +
				"\t<auth-constraint>\n" +
				"\t<role-name>admin</role-name>\n" +
				"\t</auth-constraint>\n" +
			"</security-constraint>\n\n" +

			"<login-config>\n" +
				"\t<auth-method>BASIC</auth-method>\n" +
				"\t<realm-name>Your Realm Name</realm-name>\n" +
			"</login-config>\n\n" +

			"<security-role>\n" +
				"\t<description>The role that is required to log in to the Manager Application</description>\n" +
				"\t<role-name>admin</role-name>\n" +
			"</security-role>\n\n";
		
		if (cData.askForLogin == true) {
			return WebXML;
		} else {
			return "";
		}
	}
	
	/**
	 * get todays date
	 * @return
	 */
	private String getDate() {
		Date date = new Date();
		
		String now;
		try {
			now = date.toString();
		} catch (Exception e) {
			e.printStackTrace();
			// timezone issue come up?
			now = " getDate(); error ";
		}
		
		return now;
	}
	
	/**
	 * create an index page for easy module access
	 * @return
	 */
	private String createIndexPageContents() {
	
		String LintToModule = projectName +".html";
		String LinkToModuleDesc = LintToModule;
		
		String IndexPage = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n" +
		"<html>\n" +
		"<body>" +
		"" +
		"\t<a href=\""+LintToModule+"\">"+LinkToModuleDesc+"</a> Quick link to your gwt module." +
		"</body>" +
		"</html>\n";
		
		return IndexPage;
	}
	
	/**
	 * create file ./production/index.jsp
	 */
	private void createIndexPage() {
		
		String sep = getPathsFileSeparator();
		
		String File = cData.tempBuildFolderName + sep + "index.jsp";
		createFile(File, createIndexPageContents());
	}
	
	/**
	 * Get the project directory this script resides in
	 * 
	 * figure out how to get the path of this script
	 */
	private void getCurrentProjectDirectory() {
		//don't know how to get the eclipse project directory yet that this file is in
	}
	
	/**
	 * setup temp build folder, staging area for files
	 */
	private void setTempBuildFolder() {
		
		// TODO check for / or \\ on the tempBuildFolder and get rid of it
		
		String sep = getPathsFileSeparator();
		cData.tempBuildFolderName = cData.projectDirectory + sep + cData.tempBuildFolderName;
	}

	/**
	 * delete the previous production folder
	 */
	private void deleteProductionFolder(int when) {

		String s = "";
		if (when == 0) {
			s = "Previous";
		}
		
		String sDir = cData.tempBuildFolderName;
		File dir = new File(sDir);
		
		System.out.println("Deleting " + s + " production directory contents: " + sDir);
	
		//delete the production folder contents
		try {
			deleteDir(dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * delete folder contents
	 * @param dir
	 * @return
	 */
	private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
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
		
		String File = cData.tempBuildFolderName + sep + "WEB-INF" + sep + "web.xml";
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
		String ProductionFolder = cData.tempBuildFolderName;
		createFolder(ProductionFolder);

		// create folder ./production/WEB-INF
		String ProductionWebInf = cData.tempBuildFolderName + sep + "WEB-INF";
		createFolder(ProductionWebInf);

		// create folder ./production/WEB-INF/classes
		String ProductionWebInfClasses = cData.tempBuildFolderName + sep + "WEB-INF" + sep + "classes";
		createFolder(ProductionWebInfClasses);

		// create folder ./production/WEB-INF/lib
		String ProductionWebInfLib = cData.tempBuildFolderName + sep + "WEB-INF" + sep + "lib";
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
		String src = cData.projectDirectory + sep + "bin";
		String dest = cData.tempBuildFolderName + sep +"WEB-INF" + sep + "classes";
		
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
	 * www is the compiled project directory
	 * production is the temp directory for setting up the war archive
	 * 
	 * Notes
	 * Archive/www/*files - have to change the servelt context path if you change this
	 * String dest = TempBuildFolder + "/www";
	 */
	private void copyWWWFiles() {
		
		System.out.println("Copying gwt WWW files to the temp directory");
		
		String sep = getPathsFileSeparator();
		
		// source directory
		String src = cData.projectDirectory + sep + "www" + sep + projectModuleName;
		
		// destination directory 
		String dest = cData.tempBuildFolderName;
		
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
			dest = cData.tempBuildFolderName + sep + "WEB-INF" + sep + "lib" + sep + DestFile;
			
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
	 * get class path file contents
	 * search .classpath for libs to add
	 */
	private void getClassPathContents() {
		
		String sep = getPathsFileSeparator();
		
		String classpathfilelocation = cData.projectDirectory + sep +".classpath";
		
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
	 * TODO - don't know how to process dir constant JUNIT_HOME, need to get eclipse enviro var, do this later "/"
	 * 
	 * TODO - path seperator in regexp
	 */
	private void getClassLibs() {

		//kind=['|\"]lib['|\"].*?
		Pattern p = Pattern.compile("path=['\"](/.*?jar)['\"]");
		Matcher matcher = p.matcher(classFileContents);

		// Count the matches first
		int i = 0;
		while (matcher.find()) {
			//System.out.println("Found Lib in ClassPath: " + i);
			i++;
		}

		//init the array to proper size
		String[] jars = new String[i];

		matcher.reset();//reset position

		//save the jars to array
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
	 * @param AddGwtServlet - tell it to replace gwt-servlet.jar and not erase it
	 * @return
	 */
	private String[] deleteGwtUserJar(String[] jars, boolean AddGwtServlet) {
		String jar = null;
		for(int i=0; i<jars.length; i++) {
			jar = jars[i].toString();
			
			boolean has = jars[i].contains("gwt-user.jar");
			if (has == true) {
				if (AddGwtServlet == true) {
					System.out.println("ClassLibs Changing gwt-user.jar to gwt-servlet.jar");
					jars[i] = GWT_HOME + "gwt-servlet.jar";
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
		projectGWTxmlFileLocation = cData.projectDirectory + sep + "src" + projectDir + sep  + projectGWTxmlFile;
		
		System.out.println("ProjectGWTxmlFile: " + projectGWTxmlFile);
		System.out.println("ProjectGWTxmlFileLocation: "+ projectGWTxmlFileLocation);
	}

	/**
	 * read xml files contents
	 * @throws Exception 
	 */
	private void readProjectXMLFileContents() {
		
		File file = new File(projectGWTxmlFileLocation);
		projectGWTxmlFileContents = readFile(file);
		
		System.out.println("ProjectGWTxmlFileContents: " + projectGWTxmlFileContents);
		
		if (projectGWTxmlFileContents.length() == 0) {
			System.out.println("Could not read your GWT XML file. debug: readProjectXMLFileContents");
			System.exit(1);
		}
		
	}

	
	/**
	 * get servlet class
	 * 
	 * TODO - add os file path seperator to regexp
	 * 
	 */
	private void getServletClassFromXMLFile() {

		Pattern p = Pattern.compile("<servlet.*?class=[\"'](.*?)[\"'].*?>");
		Matcher m = p.matcher(projectGWTxmlFileContents);
		boolean found = m.find();

		if (found == true) {
			servletClassName = m.group(1);
		}

		//debug
		System.out.println("ServletClassName: " + servletClassName);
		System.out.println("");
	}

	
	/**
	 * get servlet path
	 * 
	 * TODO - fix this Brandon - need to correct regexp!!!!!!!
	 * 
	 */
	private void getServeletUrlPath() {

		// entry-point class
		Pattern p = Pattern.compile("<entry-point.*?class.*?=.*?['\"](.*?)['\"].*?>");
		Matcher m = p.matcher(projectGWTxmlFileContents);
		boolean found = m.find();

		if (found == true) {
			servletPath = m.group(1);
		}

		//debug
		System.out.println("ServletPath: " + servletPath);
	}

	/**
	 * get the directory structure - this wont work with a project named with 1 name and no . in it
	 * 
	 * TODO - add logic to use single name for class like "class" and not just "com.domain.gwt.class.client.class"
	 */
	private void getProjectDirectoryFromClassName() {

		String[] dirs = projectModuleName.split("\\.");

		projectDirs = dirs; //can use this else where

		String dir = "";
		for (int i = 0; i < (dirs.length - 1); i++) {
			dir = dir + "/" + dirs[i].toString();
		}

		projectDir = dir;
		System.out.println("ProjectDir: " + dir);
	}

	/**
	 * get project name from class name
	 */
	private void getProjectName() {
		projectName = projectDirs[projectDirs.length-1].toString();
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
	 * get the server class name server/"ServiceImpl"
	 * 
	 * TODO - add logic to use single name for class like "class" and not just "com.domain.gwt.class.client.class"
	 */
	private void getServerClassNameIMPL() {

		if (servletClassName == null) {
			return;
		}
		
		String[] dirs = servletClassName.split("\\.");

		//ServerProjectDirs = dirs; //can use this else where

		/* not useing for now
		String dir = "";
		for (int i=0; i < (dirs.length-1); i++) {
			dir = dir + "/" +  dirs[i].toString();
		}
		 */

		servletClassNameIMPL = dirs[dirs.length - 1].toString();

		System.out.println("ServletClassNameIMPL: " + servletClassNameIMPL);
	}

	/**
	 * get the package name (and class) that the src files are in com.tribling.gwt.test.client.MyProject
	 */
	private void getProjectClassName() {

		Pattern p = Pattern.compile("\"\\$@\"\040(.*?);");
		Matcher m = p.matcher(projectCompileFileContents);
		boolean found = m.find();

		if (found == true) {
			projectModuleName = m.group(1);
		}

		//debug
		System.out.println("ProjectClassName: " + projectModuleName);
	}

	/**
	 * get the location for the gwt files from eclipse class path contents
	 * 
	 * errors when the class path is relative. 
	 * 
	 * TODO - look up the relative path
	 * TODO - add get os file path seperator for regexp
	 * 
	 */
	private void getGWT_HOME() {

		if (path_GWT_HOME_Manual != null) {
			GWT_HOME = path_GWT_HOME_Manual;
			return;
		}
		
		Pattern p = Pattern.compile(":(/.*)gwt-user.jar:");
		Matcher m = p.matcher(projectCompileFileContents);
		boolean found = m.find();

		if (found == true) {
			GWT_HOME = m.group(1);
		} else {
			System.out.println("Can't find GWT_Home Directory, in the ProjectCompileFileContents Classpath. debug getClassPathContents()");
			System.exit(1);
		}
		
		//debug
		System.out.println("GWT_HOME dir: " + GWT_HOME);
	}

	/**
	 * read the gwt-compile folder to get contents
	 */
	private void readProjectCompileFile() {

		String sep = getPathsFileSeparator();
		
		String directory = cData.projectDirectory + sep + projectCompileFile;
		File ProjectCompileFile = new File(directory);
		
		projectCompileFileContents = readFile(ProjectCompileFile);
		
		System.out.println("FileContents: " + projectCompileFileContents);
		
	}
	
	/**
	 * read the project xml file to get contents
	 * 
	 * note: this isn't used at the moment
	 */
	private void readProjectGWTxmlFile() {

		String sep = getPathsFileSeparator();
		
		String Dir = cData.projectDirectory + sep + projectGWTxmlFile;
		File ProjectCompileFile = new File(Dir);

		projectGWTxmlFileContents = readFile(ProjectCompileFile);
		System.out.println("FileContents: " + projectGWTxmlFileContents);
	}

	/**
	 * get file list in the directory (find project-compile)
	 */
	private void checkProjectListForCompile() {
		File file;
		String FileList[];

		// ls the directory for files
		file = new File(cData.projectDirectory);
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

			//debug output
			//System.out.println("Checking for compile: inFile: "+ file);

			if (found == true) {
				projectCompileFile = file;
				return;
			}
		}
	}

	/**
	 * get the file that says project-compile
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

		//System.out.println("FOUND?" + found);

		return found;
	}

	/**
	 * create new directory
	 * @param Dir
	 * @return
	 */
	private  boolean createFolder(String Dir) {
		boolean status = false;
		String NewDir = null;

		if (Dir != null) {
			NewDir = Dir;
			status = new File(NewDir).mkdir();
		}

		//debug
		System.out.println("Created Production Dir: " + NewDir);

		return status;
	}

	/**
	 * read file contents
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
			//trace the error
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

		File src = new File(strPath);
		File dest = new File(dstPath);

		//skip .svn files
		if (src.isDirectory() == true && src.getName() != ".svn") {

			dest.mkdirs();
			String list[] = src.list();

			for (int i = 0; i < list.length; i++) {

				String src1 = src.getAbsolutePath() + "/" + list[i];
				String dest1 = dest.getAbsolutePath() + "/" + list[i];

				if (list[i].toString().matches(".svn") != true) { //skip .svn dirs
					//debug 
					//System.out.println("copying: " + src1 + " to " + dest1);
					
					copyFiles(src1, dest1);
				}
			}
		} else {

			FileInputStream fin = new FileInputStream(src);
			FileOutputStream fout = new FileOutputStream(dest);

			System.out.println("Copying File src:" + src + " dest:" + dest);
			
			int c;
			while ((c = fin.read()) >= 0)
				fout.write(c);

			fin.close();
			fout.close();

		}
	}

	/**
	 * create file with these contents
	 * @param File
	 * @param Contents
	 */
	private void createFile(String File, String Contents) {
		
	    try {
	        File file = new File(File);
	    
	        // Create file if it does not exist
	        boolean success = file.createNewFile();
	        if (success) {
	        	// Write to file
                BufferedWriter out = new BufferedWriter(new FileWriter(File, true));
                out.write(Contents);
                out.close();

	        } else {
	            // File already exists
	        	(new File(File)).delete();
	        	createFile(File, Contents);
	        }
	    } catch (IOException e) {
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
	    String zipUp = cData.tempBuildFolderName;
	    
	    System.out.println("WarZipFile: " + zipFile + " ZipUpDir:" + zipUp);
	    
	    FileOutputStream file = null;
		try {
			file = new FileOutputStream(zipFile);
		} catch (FileNotFoundException e) {
			System.out.println("Could not make the zip file. debug: zipProject");
			e.printStackTrace();
			System.exit(1);
		}
	    
	    //create a ZipOutputStream to zip the data to 
	    ZipOutputStream zos = new ZipOutputStream(file);
	    zipDir(zipUp, zos); 

	    try {
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} //close the stream 
	}
	
	/**
	 * get war file with entire path
	 * 
	 * @return
	 */
	private String getWarFilePathAndName() {
		String warFile = null; 
		if (cData.tempWarFileLocation == null) {
			warFile = cData.projectDirectory + "/" + projectName + ".war";
		} else {
			warFile = cData.tempWarFileLocation + "/" + projectName + ".war";
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
		if (cData.tempWarFileLocation == null) {
			warDir = cData.projectDirectory;
		} else {
			warDir = cData.tempWarFileLocation;
		}
		return warDir;
	}
	
	/**
	 * archive temp build folder
	 * 
	 * @param dir2zip
	 * @param zos
	 * 
	 * TODO - skip .svn files
	 */
	private void zipDir(String dir2zip, ZipOutputStream zos) {
		try {
			//create a new File object based on the directory we have to zip File    
			File zipDir = new File(dir2zip);

			//get a listing of the directory content 
			String[] dirList = zipDir.list();

			byte[] readBuffer = new byte[2156];
			int bytesIn = 0;

			//loop through dirList, and zip the files 
			for (int i = 0; i < dirList.length; i++) {

				File f = new File(zipDir, dirList[i]);

				if (f.isDirectory()) {
					//if the File object is a directory, call this 
					//function again to add its content recursively 
					String filePath = f.getPath();
					zipDir(filePath, zos);
					//loop again 
					continue;
				}

				//if we reached here, the File object f was not a directory 
				//create a FileInputStream on top of f 
				FileInputStream fis = new FileInputStream(f);

				//create a new zip entry 
				
				String filePath = f.getPath();
				filePath = getZipPath(filePath); //change the path to relative to the production dir
				ZipEntry anEntry = new ZipEntry(filePath);

				//place the zip entry in the ZipOutputStream object 
				zos.putNextEntry(anEntry);

				//now write the content of the file to the ZipOutputStream 
				while ((bytesIn = fis.read(readBuffer)) != -1) {
					zos.write(readBuffer, 0, bytesIn);
				}
				//close the Stream 
				fis.close();
			}
		} catch (Exception e) {
			System.out.println("Could not do the zipDir process. debug zipDir()");
			System.exit(1);
		}
	}
	
	/**
	 * Figure out the build path, so we can set the zip archive to relative path
	 * 
	 * TODO add os file path seperator
	 * 
	 * @param Path
	 * @return
	 */
	private String getZipPath(String Path) {
		
		String path = null;
		
		///.*production/
		Pattern p = Pattern.compile("/.*production/(.*)");
		Matcher m = p.matcher(Path);
		boolean found = m.find();

		if (found == true) {
			path = m.group(1);
		}

		//debug
		System.out.println("Zip path: " + path);
		return path;
	}
	
	/**
	 * rename war file to desired name
	 */
	private void renameWar() {
		
		String sep = getPathsFileSeparator();
		
		if (cData.renameWarFile == false && cData.renameWarFileNameTo != null) {
			return;
		}
		
		if (cData.renameWarFileNameTo.equals("")) {
			System.out.println("renameWarFielNameTo is null, add that field");
		}
		
		String warFile = getWarFilePathAndName();
		String warDir = getWarDir();
		
		String newName = warDir + sep + cData.renameWarFileNameTo;
		
		
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
		String s = "";
		if (cData.os == 0) {
			s = "/";
		} else if (cData.os == 1) {
			s = "\\";
		}
		
		// debug
		if (s.length() == 0) {
			System.out.println("no os was selected");
		}
		
		return s;
	}
	
	/**
	 * compile the eclipse project first
	 */
	private void compileProject() {
		
		String sep = getPathsFileSeparator();
		String runPath = cData.projectDirectory + sep + projectCompileFile;

		System.out.println("Compiling gwt application - This may take a minute to complete! Be patient.");

		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(runPath);
			proc.waitFor();
			// TODO - show wait - need a worker
		} catch (Throwable t) {
			System.out.println("If you get a permission error on linux, 'chmod 777 project-compile'");
			t.printStackTrace();
			System.exit(1);
		}

		System.out.println("Done compiling gwt application");
		
	}
	
	private void showWait() {
		int i = 0;
		do  {
			System.out.print(".");
			if (i == 40) {
				i = 0;
				System.out.println(".");
			}
			i ++;
		} while(wait == true);
		
	}
	
}//end
