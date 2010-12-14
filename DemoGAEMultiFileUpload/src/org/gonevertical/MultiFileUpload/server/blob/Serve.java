package org.gonevertical.MultiFileUpload.server.blob;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gonevertical.MultiFileUpload.server.ServerPersistence;
import org.gonevertical.MultiFileUpload.server.jdo.BlobJdo;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Serve extends HttpServlet {
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
	  
	  String path = req.getPathInfo();
	  path = "/serve" + path;

	  ServerPersistence sp = new ServerPersistence();
	  BlobJdo bj = new BlobJdo(sp);
	  String sblobKey = bj.queryBlobKey(path);
	  
	  
	  // Serve Blob
	  if (sblobKey != null) {
	    BlobKey blobKey = new BlobKey(sblobKey);
		  blobstoreService.serve(blobKey, res);
	  } else {
	    // serve uknown pic
	  }
		
	  
	}
	
}
