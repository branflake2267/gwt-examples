package org.gonevertical.MultiFileUpload.server.blobs;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Servlet_Blob extends HttpServlet {

  // init the blog store service
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  public void doPost(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {

    
    
    // security
    String accessToken = request.getParameter("AccessToken");
    String accessSecret = request.getParameter("AccessSecret");
    
    System.out.println("getBlob Servlet: " + accessToken + " " + accessSecret);
    
    if (accessToken == null || 
        accessToken.trim().length() == 0 || 
        accessSecret == null || accessSecret.trim().length() == 0) {
      return;
    }
    
    res.setContentType("text/html");

    PrintWriter out = res.getWriter();

    out.println(getBlobStoreUrl());

  }

  public void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {

    // security
    String consumerKey = request.getParameter("consumerKey");
    String consumerSecret = request.getParameter("consumerSecret");
    if (consumerKey == null || 
        consumerKey.trim().length() == 0 || 
        consumerSecret == null || consumerSecret.trim().length() == 0) {
      return;
    }
    
    res.setContentType("text/html");

    PrintWriter out = res.getWriter();

    out.println(getBlobStoreUrl());
  }

  public String getBlobStoreUrl() {
    String url = blobstoreService.createUploadUrl("/upload");
    return url;
  }

}