package org.gonevertical.MultiFileUpload.server.blob;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gonevertical.MultiFileUpload.server.ServerPersistence;
import org.gonevertical.MultiFileUpload.server.jdo.BlobJdo;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Upload extends HttpServlet {

	// init the blog store service
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doPost(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
	  
    String accessToken = request.getParameter("AccessToken");
    String accessSecret = request.getParameter("AccessSecret");
    if (accessToken == null || 
        accessToken.trim().length() == 0 || 
        accessSecret == null || accessSecret.trim().length() == 0) {
      return;
    }
	  
	  // TODO STOP Vandalism, b/c there is no security on this example for simplicity
	  if (1==1) {
	    return;
	  }
	  
	  // TODO - if then return it via ... serve?blob-key=...
	  
	  //System.out.println("uploading");
		
	  // limit size
		//int len = request.getContentLength();
		//int mb = (1024 * 1024) * 1;
		//if (len > mb) { 
			//throw new RuntimeException("Sorry that file is to large. Try < 1024 or 1MB file");
    //}
		
		// debug output the parameters that came in on the form
		Enumeration paramNames = request.getParameterNames();
    while(paramNames.hasMoreElements()) {
      
      String paramName = (String) paramNames.nextElement();
      String[] paramValues = request.getParameterValues(paramName);
      System.out.println(paramName + "=" + paramValues[0].toString());
      
    }
    
    String file = request.getParameter("File");
    String fileName = request.getParameter("FileName");
    String filePath = request.getParameter("FilePath");
    String directorySelected = request.getParameter("DirectorySelected");
    String virtualPath = request.getParameter("VirtualPath");
    

		
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
		BlobKey blobKey = blobs.get("File");

		//TODO does the file already exist and if so, is there duplicates of jdo or file, delete dups?
    ServerPersistence sp = new ServerPersistence();
    BlobJdo bj = new BlobJdo(sp);
    bj.save(fileName, filePath, directorySelected, virtualPath, blobKey);
		
    
    // serve back if need be
    /*
		if (blobKey == null) {
			res.sendRedirect("/");
		} else {
			res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
		}
		*/
		
	}

}