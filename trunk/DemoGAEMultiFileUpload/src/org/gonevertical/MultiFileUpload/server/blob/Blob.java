package org.gonevertical.MultiFileUpload.server.blob;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Blob extends HttpServlet {

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

    res.setContentType("text/html");

    PrintWriter out = res.getWriter();

    out.println(getBlobStoreUrl());

  }

  public void doGet(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
    
    String accessToken = request.getParameter("AccessToken");
    String accessSecret = request.getParameter("AccessSecret");
    if (accessToken == null || 
        accessToken.trim().length() == 0 || 
        accessSecret == null || accessSecret.trim().length() == 0) {
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