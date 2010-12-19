package org.gonevertical.MultiFileUpload.server.blob;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gonevertical.MultiFileUpload.server.ServerPersistence;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Servlet_Serve extends HttpServlet {
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doGet(HttpServletRequest request, HttpServletResponse res) throws IOException {
	  
	   // security
    String accessToken = request.getParameter("accessToken");
    String accessSecret = request.getParameter("accessSecret");
    if (accessToken == null || 
        accessToken.trim().length() == 0 || 
        accessSecret == null || accessSecret.trim().length() == 0) {
      return;
    }
	  
	  String path = request.getPathInfo();
	  
	  if (path == null) {
	    System.out.println("Serve.doGet(): path null");
	    return;
	  }
	  
	  if (path.matches("/serve.*") == false) {
	    path = "/serve/tour" + path;
	  }
	  


	  ServerPersistence sp = new ServerPersistence();
	  BlobJdo bj = new BlobJdo(sp);
	  String sblobKey = bj.queryBlobKey(path);
	  
	  //System.out.println("Serve.doGet(): " + path + " key: " + sblobKey);
	  
	  // Serve Blob
	  if (sblobKey != null) {
	    BlobKey blobKey = new BlobKey(sblobKey);
		  blobstoreService.serve(blobKey, res);
	  } else {
	    // serve uknown pic
	  }
		
	  
	}
	
}
