package org.gonevertical.MultiFileUpload.server.blobs;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gonevertical.MultiFileUpload.server.ServerPersistence;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Servlet_Upload extends HttpServlet {

	// init the blog store service
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doPost(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
	  
	  // security
    String accessToken = request.getParameter("AccessToken");
    String accessSecret = request.getParameter("AccessSecret");
    if (accessToken == null || 
        accessToken.trim().length() == 0 || 
        accessSecret == null || accessSecret.trim().length() == 0) {
      return;
    }
	  
	  // limit size
		//int len = request.getContentLength();
		//int mb = (1024 * 1024) * 1;
		//if (len > mb) { 
			//throw new RuntimeException("Sorry that file is to large. Try < 1024 or 1MB file");
    //}
		

    // get form params
    String file = request.getParameter("File");
    String fileName = request.getParameter("FileName");
    String filePath = request.getParameter("FilePath");
    String directorySelected = request.getParameter("DirectorySelected");
    String virtualPath = request.getParameter("VirtualPath");
    

    


		// new blob key
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
		BlobKey blobKey = blobs.get("File");

		
    ServerPersistence sp = new ServerPersistence();
    BlobJdo bj = new BlobJdo(sp);
    bj.save(fileName, filePath, directorySelected, virtualPath, blobKey);
		
    
		
	}

}