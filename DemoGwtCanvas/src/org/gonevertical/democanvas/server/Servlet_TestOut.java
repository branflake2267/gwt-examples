package org.gonevertical.democanvas.server;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.repackaged.com.google.common.util.Base64DecoderException;

public class Servlet_TestOut extends HttpServlet {

  
  //public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    //echo(request);
    
  //}
  
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    String contenType = request.getContentType();
    System.out.println("ContentType=" + contenType);
    
    String method = request.getMethod();
    System.out.println("Method=" + method);

    
    echoHeaders(request);
    
    echo(request);
    
    echoParameters(request);
    
    echoAttributes(request);
    
    /*
    String strBase64 = request.getParameter("FileBase64");
    if (strBase64 != null) {
      byte[] fileBytes = null;
      try {
        fileBytes = Base64.decode(strBase64.getBytes());
      } catch (Base64DecoderException e) {
        e.printStackTrace();
      }
      System.out.println("filebytes: " + fileBytes);
    }
    */
    
    String s = request.getParameter("oid");
    System.out.println("oid=" + s);
  }
  
  private void echoAttributes(HttpServletRequest request) {
    System.out.println("~~~~~~~~~~~~Echo Attributes START");
    Enumeration<String> attrs = request.getAttributeNames();
    if (attrs == null) {
      return;
    }
    while(attrs.hasMoreElements()) {
      String headerName = attrs.nextElement();
      String value = request.getHeader(headerName);
      System.out.println("Attribute::: " + headerName + "=" + value);
    }
    System.out.println("~~~~~~~~~~~~Echo Attributes End");
  }

  private void echoParameters(HttpServletRequest request) {
    System.out.println("");
    System.out.println("~~~~~~~~~~~~Echo Parameters START");
    Enumeration<String> names = request.getParameterNames();
    if (names == null) {
      System.out.println("why are the parameters null???");
      return;
    }
    while(names.hasMoreElements()) {
      String name = names.nextElement();
      String value = request.getParameter(name);
      System.out.println("Parameter::: name=" + name + " value=" + value);
    }
    System.out.println("~~~~~~~~~~~~Echo Parameters END");
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
