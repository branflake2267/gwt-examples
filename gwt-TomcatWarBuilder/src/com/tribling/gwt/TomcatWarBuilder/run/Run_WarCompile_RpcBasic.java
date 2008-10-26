package com.tribling.gwt.TomcatWarBuilder.run;

import com.tribling.gwt.TomcatWarBuilder.TomcatWarBuilder;
import com.tribling.gwt.TomcatWarBuilder.WarCompileData;

public class Run_WarCompile_RpcBasic {

	/**
	 * run this from console or debug configuration in eclipse
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// refer to this object for variable options
		WarCompileData data = new WarCompileData();
		
		// choose your operating system
		// [0=linux, 1=windows]		
		
		data.os = 0;
		
		// project directory (examples below)
		data.projectDirectory = "/home/branflake2267/workspace/gwt-test-RPC-basic";

		// rename war file to [ROOT.war | project.war]
		// ROOT.war is the virtual host root servlet context "/" 
		data.renameWarFile = false; //true = turn on will rename the servlet context (filename)
		
		// if renameWarFile = true then rename to:
		data.renameWarFileNameTo = "ROOT.war"; 
		

		/********** OPTIONAL ***********/
		
		// go to application instead of going to index.jsp true is recommended
		data.goDirectlyToApplication = true;
		
		// Ask for credentials to use application 
		data.askForLogin = false;
		
		data.deleteTempFolder = false;
		
		/********** OPTIONAL ***********/
		
		
		// set up the object that will build the war 
		TomcatWarBuilder build = new TomcatWarBuilder();
		build.setTomcatCompileData(data);
		build.start();
	}

}
