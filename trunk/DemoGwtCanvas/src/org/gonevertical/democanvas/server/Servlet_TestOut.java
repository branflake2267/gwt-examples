package org.gonevertical.democanvas.server;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.repackaged.com.google.common.util.Base64DecoderException;

public class Servlet_TestOut extends HttpServlet {

  
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    echo(request);
    
  }
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    echoHeaders(request);
    
    echo(request);
    
    
    String strBase64 = request.getParameter("FileBase64");
    byte[] fileBytes = null;
    try {
      fileBytes = Base64.decode(strBase64.getBytes());
    } catch (Base64DecoderException e) {
      e.printStackTrace();
    }
    
    
    System.out.println("filebytes: " + fileBytes);
  }

  private void echoHeaders(HttpServletRequest request) {
    Enumeration<String> headers = request.getHeaderNames();
    while(headers.hasMoreElements()) {
      String headerName = headers.nextElement();
      String value = request.getHeader(headerName);
      System.out.println("Header::: " + headerName + "=" + value);
    }
  }

  private void echo(HttpServletRequest request) throws IOException {
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Start");
    
    ServletInputStream in = request.getInputStream();
    int data = in.read();
    while(data != -1) {
      System.out.print((char)data);
      data = in.read();
    }
    in.close();
    
    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~End");
  }
  
  
}
