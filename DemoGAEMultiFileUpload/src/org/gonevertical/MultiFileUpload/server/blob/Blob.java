package org.gonevertical.MultiFileUpload.server.blob;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.sun.media.jai.opimage.RescaleCRIF;

public class Blob extends HttpServlet {

  // init the blog store service
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  public void doPost(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {

    res.setContentType("text/html");

    PrintWriter out = res.getWriter();

    out.println(getBlobStoreUrl());

  }

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    res.setContentType("text/html");

    PrintWriter out = res.getWriter();

    out.println(getBlobStoreUrl());
  }

  public String getBlobStoreUrl() {
    String url = blobstoreService.createUploadUrl("/upload");
    return url;
  }

}