package org.gonevertical.MultiFileUpload.server.blob;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Serve extends HttpServlet {
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
	  
	  String a = req.getPathInfo();
	  String b = req.getPathTranslated();
	  
	  System.out.println("test");
		//BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		//blobstoreService.serve(blobKey, res);
		
	}
	
}
