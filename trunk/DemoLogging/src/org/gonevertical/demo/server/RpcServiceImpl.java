package org.gonevertical.demo.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.EmptyStackException;

import org.apache.log4j.Logger;
import org.gonevertical.demo.client.CallData;
import org.gonevertical.demo.client.RpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

	private Logger logger = Logger.getLogger(RpcServiceImpl.class);

	public CallData callServer(CallData callData) {

		if (callData.type == 1) {
			callData.note = "from server";
			logger.info("test to server");

		} else if (callData.type == 2) {

			testMethod();

		} else if (callData.type == 10) {

			callData.note = getLog();
		}


		return callData;
	}

	private String getLog() {
		File executionlocation = null;
    try {
      executionlocation = new File(RpcServiceImpl.class.getProtectionDomain().getCodeSource().getLocation().toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    String execPath = executionlocation.getParent();
    
    execPath = execPath.replaceAll("/WEB-INF", "");
    
    String path = execPath + "/logging.log";  
		
    File file = new File(path);
    
		String r = "";
		
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			br = new BufferedReader(new InputStreamReader(dis));
			String s = null;
			while ((s = br.readLine()) != null) {
				r += s + "\n";
			}
			br.close();
			fis.close();
			bis.close();
			dis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return r;
	}

	private void testMethod() {

		try {
			throw new EmptyStackException();
		} catch (Exception e) {
			logger.warn("Exception Thrown", e);
		}

	}

}
