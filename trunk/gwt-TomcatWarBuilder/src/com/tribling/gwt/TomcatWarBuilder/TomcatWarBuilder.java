package com.tribling.gwt.TomcatWarBuilder;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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
 * build the war file for tomcat gwt server deploy
 * addes the libraries you need too.
 * @author branflake2267 - Brandon Donnelson
 *
 * Watch Console for output. Vars will be outputted there.  
 *
 * !!!!!!!!!!!Compile your project in the eclipse gwt debugger before you run this
 *
 * Wants
 * TODO - figure out what project directory this file is in when ran as java application
 * TODO - Compile project first
 * TODO - upload to server via tomcat deploy
 * 
 * Maybes
 * TODO - dont zip .svn*
 * TODO - Ask for username and password for tomcat deploy, instead of static var
 * 
 * Documentation
 * http://tomcat.apache.org/tomcat-6.0-doc/index.html
 */
public class TomcatWarBuilder {

	// project vars
	private static String ProjectDirectory;

	// GWT-linux location
	private static String GWT_HOME;

	// GWT-linux Version
	private static boolean isGWT15_M1 = false;
	
	// Project vars
	private static String projectCompileFile; // project compile file location
	private static String projectCompileFileContents;
	private static String projectModuleName;
	private static String projectName;
	private static String[] projectDirs;
	private static String projectDir;
	private static String projectGWTxmlFile;
	private static String projectGWTxmlFileLocation;
	private static String projectGWTxmlFileContents;
	private static String servletClassName; // xml servlet class
	private static String servletPath; // xml servlet path
	private static String servletClassNameIMPL;
	// private static String[] ServerProjectDirs; //skipping for now
	private static String classFileContents;
	private static String[] classLibs;
	private static String webXML;
	
	/* change the vars in the constructor */
	
	//temp build folder in project directory
	//BEWARE - if you change this - u must change this directory in this method -> getZipPath()
	//don't change, still fouls things up
	private static String tempBuildFolder = "/production";
	
	//default location is project root. Set this for another location
	//set with no trailing slash like "/home/branflake2267/warFiles"
	//private static String TempWarFileLocation = "/home/branflake2267"; 
	private static String tempWarFileLocation = null; 
	
	//add this if your project-compile class path is relative
	//Add trailing slash "/dir/dir/gwt-linux/"
	//private static String GWT_HOME_Manual = "/opt/gwt-linux/";
	private static String path_GWT_HOME_Manual = null;
	
	//acts like .htaccess security - popup login
	//add security xml - uses tomcat-users.xml user security
	private static boolean askForLogin = false;

	// go directly to the application as the home page
	private static boolean goDirectlyToApplication = true;

	// change the name of the war file at the end - like to ROOT.war for root app for host
	private static boolean renameWarFile = false;
	private static String renameWarFileNameTo = "ROOT.war";
	
	
	/**
	 * main method
	 * 
	 * @param args
	 * @throws IOException
	 * 
	 * 
	 * TODO - upload to server
	 * 
	 */
	public static void main(String[] args) throws IOException {
		
		
		//****FIRST**** -> Compile your project in the eclipse gwt debugger before you run this
		
		
		/* Choices Below */
		
		
		// project directory (examples below)
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
		ProjectDirectory = "/home/branflake2267/workspace/gwt-test-MySQLConn";
		
		// Ask for credentials to use application 
		askForLogin = false;
		
		
		// For virtual hosting - rename your war file to what you want the application context to be
		// OR rename to ROOT.war for hosts root app
		renameWarFile = false; //true = turn on rename for servlet context(filename)
		
		//rename to
		renameWarFileNameTo = "ROOT.war"; 

		
		/********** OPTIONAL ***********/
		
		// go to application instead of going to index.jsp true is recommended
		goDirectlyToApplication = true;
		
		//Output goes to ~/std directory
		isGWT15_M1 = false; //they put the compiled files in a different directory
		
		
		/* End Choices */
		
		
		
		// go get em
		startProcess();
	}

	/**
	 * process everything - and build war archive
	 * @throws IOException
	 */
	private static void startProcess() throws IOException {
		
		/********************/
		/* get/setup Project Vars */
		/********************/
	
		
		//Compile Project
		//TODO - compile the projectdirectory project
		
		
		
		//testing - todo
		getCurrentProjectDirectory();
		
		//set Temp Build Folder 
		setTempBuildFolder(); //use gwt-project/production for now.
		
		// find file project-compile to read its contents
		checkProjectListForCompile();

		// read the compile file
		readProjectCompileFile();

		// figure Compile Vars
		getProjectVars();

		
		
		/********************/
		/* START WAR WAR BUILD */
		/********************/

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
		
		
		
		//TODO - upload to production/design tomcat server
		//http://localhost:8080/manager/deploy?path=/footoo&war=file:/path/to/foo

		
		
		//delete production folder when done
		
		
		//Done with everything
		System.out.println("");
		System.out.println("All Done");
	}
	
	/**
	 * get project Vars
	 */
	private static void getProjectVars() {
		
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
	private static String createWebXMLFileContents() {
		
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
		if (goDirectlyToApplication == true) {
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
	private static String createWebXMLFileContents_Security() {
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
		
		if (askForLogin == true) {
			return WebXML;
		} else {
			return "";
		}
	}
	
	/**
	 * get todays date
	 * @return
	 */
	private static String getDate() {
		Date date = new Date();
		String now = date.toString();
		return now;
	}
	
	/**
	 * create an index page for easy module access
	 * @return
	 */
	private static String createIndexPageContents() {
	
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
	private static void createIndexPage() {
		String File = tempBuildFolder + "/index.jsp";
		createFile(File, createIndexPageContents());
	}
	
	/**
	 * Get the project directory this script resides in
	 * 
	 * figure out how to get the path of this script
	 */
	private static void getCurrentProjectDirectory() {
		//don't know how to get the eclipse project directory yet that this file is in
	}
	
	/**
	 * setup temp build folder, staging area for files
	 */
	private static void setTempBuildFolder() {
		tempBuildFolder = ProjectDirectory + tempBuildFolder;
	}

	/**
	 * delete the previous production folder
	 */
	private static void deleteProductionFolder(int when) {

		String s = "";
		if (when == 0) {
			s = "Previous";
		}
		
		String sDir = tempBuildFolder;
		File dir = new File(sDir);
		
		System.out.println("Deleting " +s + " production directory contents: " + sDir);
	
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
	private static boolean deleteDir(File dir) {
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
	private static void createWebXMLFile() {
		String File = tempBuildFolder + "/WEB-INF/web.xml";
		createFile(File, createWebXMLFileContents());
	}
	
	/**
	 * create Folders for build
	 */
	private static void createDirectoriesForBuild() {

		System.out.println("");
		System.out.println("Starting Build");

		// create ./production folder for staging
		String ProductionFolder = tempBuildFolder;
		createFolder(ProductionFolder);

		// create folder ./production/WEB-INF
		String ProductionWebInf = tempBuildFolder + "/WEB-INF";
		createFolder(ProductionWebInf);

		// create folder ./production/WEB-INF/classes
		String ProductionWebInfClasses = tempBuildFolder + "/WEB-INF/classes";
		createFolder(ProductionWebInfClasses);

		// create folder ./production/WEB-INF/lib
		String ProductionWebInfLib = tempBuildFolder + "/WEB-INF/lib";
		createFolder(ProductionWebInfLib);
	}

	
	/**
	 * copy the files in the correct folders
	 * 
	 * @throws IOException
	 */
	private static void copyFilesToProductionBuildFolder() throws IOException {

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
	private static void copyCompiledClasses() {
		
		String src = ProjectDirectory + "/bin";
		String dest = tempBuildFolder + "/WEB-INF/classes";
		
		try {
			copyFiles(src, dest);
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}

	}
	
	/**
	 * copy files - //copy ./www to ./production/
	 */
	private static void copyWWWFiles() throws IOException {
		
		String addDir = "";
		if (isGWT15_M1 == true) {
			addDir = "/std";
		}
		
		System.out.println("Copying WWW Files");
		
		String src = ProjectDirectory + "/www/" + projectModuleName + addDir;
		
		//Archive/www/*files - have to change the servelt context path if you change this
		//String dest = TempBuildFolder + "/www";
		
		//root production folder - much easier to use right off the bat - If you stick it in folders deeper, you have to change the url-path web.xml
		String dest = tempBuildFolder;
		
		try {
			copyFiles(src, dest);
		} catch (IOException e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	/**
	 * copy class jars to the libs folder
	 * 
	 * @throws IOException
	 */
	private static void copyJarFiles() throws IOException {

		System.out.println("Copying Jars" + classLibs.length);
		
		String src = null;
		String dest = null;

		for (int i = 0; i < (classLibs.length); i++) {
			src = classLibs[i];
			String DestFile = getDestDirectory(src);
			dest = tempBuildFolder + "/WEB-INF/lib/" + DestFile;
			
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
	private static void getClassPathContents() {
		String classpathfile = ProjectDirectory + "/.classpath";
		classFileContents = readFile(new File(classpathfile));
		System.out.println("ClassFileContents: " + classFileContents);
	}

	/**
	 * get jar files location
	 * 
	 * TODO - don't know how to process dir constant JUNIT_HOME, need to get eclipse enviro var, do this later "/"
	 * 
	 */
	private static void getClassLibs() {

		//kind=['|\"]lib['|\"].*?
		Pattern p = Pattern.compile("path=['\"](/.*?jar)['\"]");
		Matcher matcher = p.matcher(classFileContents);

		// Count the matches
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

		//need gwt-servlet?? is there an rpc method? -lest do it by default
		boolean AddGwtServlet = true;
		
		//get rid of gwt-user.jar
		jars = deleteGwtUserJar(jars, AddGwtServlet);
		
		classLibs = jars;
	}
	
	/**
	 * Switch out gwt-user.jar with gwt-servlet.jar
	 * @param jars
	 * @param AddGwtServlet - tell it to replace gwt-servlet.jar and not erase it
	 * @return
	 */
	private static String[] deleteGwtUserJar(String[] jars, boolean AddGwtServlet) {
		String jar;
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
	private static void getProjectsXMLFile() {
		int depth = projectDirs.length - 1;
		String ProjectName = projectDirs[depth];

		projectGWTxmlFile = ProjectName + ".gwt.xml";
		System.out.println("ProjectGWTxmlFile: " + projectGWTxmlFile);

		projectGWTxmlFileLocation = ProjectDirectory + "/src" + projectDir + "/" + projectGWTxmlFile;
		System.out.println("ProjectGWTxmlFileLocation: "+ projectGWTxmlFileLocation);
	}

	/**
	 * read xml files contents
	 */
	private static void readProjectXMLFileContents() {
		projectGWTxmlFileContents = readFile(new File(projectGWTxmlFileLocation));
		System.out.println("ProjectGWTxmlFileContents: " + projectGWTxmlFileContents);
	}

	
	/**
	 * get servlet class
	 */
	private static void getServletClassFromXMLFile() {

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
	 */
	private static void getServeletUrlPath() {

		Pattern p = Pattern.compile("path=['\"](.*?)['\"]");
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
	private static void getProjectDirectoryFromClassName() {

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
	private static void getProjectName() {
		projectName = projectDirs[projectDirs.length-1].toString();
		System.out.println("ProjectName: " + projectName);
	}
	
	/**
	 * get path/directory in array
	 * @param File
	 * @return
	 */
	private static String getDestDirectory(String File) {
		String[] dirs = File.split("/");
		String dir = dirs[dirs.length - 1];
		return dir;
	}

	
	/**
	 * get the server class name server/"ServiceImpl"
	 * 
	 * TODO - add logic to use single name for class like "class" and not just "com.domain.gwt.class.client.class"
	 */
	private static void getServerClassNameIMPL() {

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

	private static void getProjectClassName() {

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
	 * 
	 */
	private static void getGWT_HOME() {

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
	private static void readProjectCompileFile() {

		String Dir = ProjectDirectory + "/" + projectCompileFile;
		File ProjectCompileFile = new File(Dir);

		projectCompileFileContents = readFile(ProjectCompileFile);
		System.out.println("FileContents: " + projectCompileFileContents);
	}

	/**
	 * read the project xml file to get contents
	 */
	private static void readProjectGWTxmlFile() {

		String Dir = ProjectDirectory + "/" + projectGWTxmlFile;
		File ProjectCompileFile = new File(Dir);

		projectGWTxmlFileContents = readFile(ProjectCompileFile);
		System.out.println("FileContents: " + projectGWTxmlFileContents);
	}

	
	/**
	 * get file list in the directory (find project-compile)
	 */
	private static void checkProjectListForCompile() {
		File file;
		String FileList[];

		//ls the directory for files
		file = new File(ProjectDirectory);
		FileList = file.list();

		if (FileList == null) {
			System.out.println("Error reading current directory.");
			System.exit(1);
		}

		//look for ./project-compile
		findProjectCompile(FileList);
	}

	
	/**
	 * find the project gwt-compile file
	 * @param FileList
	 */
	private static void findProjectCompile(String[] FileList) {

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
	private static boolean checkForCompile(String file) {

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
	private static boolean createFolder(String Dir) {
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
	private static String readFile(File file) {

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
	 * @param strPath
	 * @param dstPath
	 * @throws IOException
	 */
	private static void copyFiles(String strPath, String dstPath) throws IOException {

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
	private static void createFile(String File, String Contents) {
		
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
	 * @throws IOException
	 */
	private static void zipProject() throws IOException {
		
		System.out.println("Starting Zipping of War");
		
		String ZipFile = getWarFilePathAndName();
	    String ZipUp = tempBuildFolder;
	    
	    System.out.println("WarZipFile: " + ZipFile + " ZipUpDir:" +ZipUp);
	    
	    //create a ZipOutputStream to zip the data to 
	    ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(ZipFile));
	    zipDir(ZipUp, zos); 

	    zos.close(); //close the stream 
	}
	
	/**
	 * get war file with entire path
	 * @return
	 */
	private static String getWarFilePathAndName() {
		String warFile = null; 
		if (tempWarFileLocation == null) {
			warFile = ProjectDirectory + "/" + projectName + ".war";
		} else {
			warFile = tempWarFileLocation + "/" + projectName + ".war";
		}
		return warFile;
	}
	
	/**
	 * get war directory path
	 * @return
	 */
	private static String getWarDir() {
		String warDir = null; 
		if (tempWarFileLocation == null) {
			warDir = ProjectDirectory;
		} else {
			warDir = tempWarFileLocation;
		}
		return warDir;
	}
	
	/**
	 * archive temp build folder
	 * @param dir2zip
	 * @param zos
	 * 
	 * TODO - skip .svn files
	 */
	private static void zipDir(String dir2zip, ZipOutputStream zos) {
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
			//handle exception 
		}
	}
	
	/**
	 * Figure out the build path, so we can set the zip archive to relative path
	 * @param Path
	 * @return
	 */
	private static String getZipPath(String Path) {
		
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
	private static void renameWar() {
		
		if (renameWarFile == false && renameWarFileNameTo != null) {
			return;
		}
		
		if (renameWarFileNameTo.equals("")) {
			System.out.println("renameWarFielNameTo is null, add that field");
		}
		
		String warFile = getWarFilePathAndName();
		String warDir = getWarDir();
		
		String newName = warDir + "/" + renameWarFileNameTo;
		
		
	    File file = new File(warFile);
	    File file2 = new File(newName);

	    boolean success = file.renameTo(file2);
	    if (!success) {
	        System.out.println("failed to rename war.");
	    } else {
	    	System.out.println("Rename War: " + file2);
	    }
	}
	
}//end
