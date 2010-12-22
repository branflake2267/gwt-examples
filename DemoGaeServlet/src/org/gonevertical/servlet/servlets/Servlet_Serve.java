package org.gonevertical.servlet.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Servlet_Serve extends HttpServlet {
	
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doGet(HttpServletRequest request, HttpServletResponse res) throws IOException {
	  
	  String path = request.getPathInfo();
	  
	  System.out.println("Path: " + path);
	  
	  /**
	   * serve home page
	   */
    if (path == null || (path != null && (path.equals("/") == true || path.equals("/DemoGaeServlet.html") == true))) {
      new Index().home(request, res);
      return;
    }
	  
	  if (path != null && path.matches("/serve.*") == false) {
	    path = "/serve/tour" + path;
	  }
	  


		
	  
	}
	
}
