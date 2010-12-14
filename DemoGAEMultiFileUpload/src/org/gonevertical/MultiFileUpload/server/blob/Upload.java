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
	  
	  //System.out.println("uploading");
		
		//int len = request.getContentLength();
		//int mb = (1024 * 1024) * 1;
		//if (len > mb) { 
			//throw new RuntimeException("Sorry that file is to large. Try < 1024 or 1MB file");
    //}
		
		
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

    ServerPersistence sp = new ServerPersistence();
    BlobJdo bj = new BlobJdo(sp);
    bj.save(fileName, filePath, directorySelected, virtualPath, blobKey);
		
		if (blobKey == null) {
			res.sendRedirect("/");
		} else {
			res.sendRedirect("/serve?blob-key=" + blobKey.getKeyString());
		}
		
	}

}