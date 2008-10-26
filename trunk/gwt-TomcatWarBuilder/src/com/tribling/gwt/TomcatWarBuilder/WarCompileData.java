package com.tribling.gwt.TomcatWarBuilder;

/**
 * This sets up the variables that control the build
 * 
 * @author branflake2267
 *
 */
public class WarCompileData {
	
	// operating system
	// [0:linux, 1:windows]
	public int os = 0;
	
	// project directory - absolute path to your project root
	public String projectDirectory;

	// rename war file at end
	public boolean renameWarFile = true;
	
	// rename war file to [ROOT.war | project.war]
	// ROOT.war is the virtual host root servlet context "/" 
	public String renameWarFileNameTo = "ROOT.war"; 

	
	
	/********** OPTIONAL ***********/
	
	// temp build folder
	public String tempBuildFolderName = "production";
	
	// go to application instead of going to index.jsp true is recommended
	public boolean  goDirectlyToApplication = true;
	
	// Ask for credentials to use gwt application 
	public boolean askForLogin = false;
	
	public boolean deletetempBuildFolder = true;
	
	//default location is project root. Set this for another location
	//set with no trailing slash like "/home/branflake2267/warFiles"
	//private  String TempWarFileLocation = "/home/branflake2267"; 
	public String tempWarFileLocation = null;

	// after all done - delete the temp folder
	public boolean deleteTempFolder = true; 
	
	/********** OPTIONAL ***********/
}
