package org.gonevertical.servlet.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Index extends HttpServlet {

  
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException  {

    home(req, res);
    
  }
  
  public void home(HttpServletRequest req, HttpServletResponse res) throws IOException {

    res.setContentType("text/html"); 
    PrintWriter out = res.getWriter();

    File file = new File("Index_Top.html");
    
    InputStream in = null;
    try {
        in = new BufferedInputStream(new FileInputStream(file));
        int ch;
        while ((ch = in.read()) !=-1) {
            out.print((char)ch);
        }
    }
    finally {
        if (in != null) in.close(); 
    }

    String title = "Servlet Example: Showing Request Headers";
    out.println(title +
                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "<B>Request Method: </B>" +
                req.getMethod() + "<BR>\n" +
                "<B>Request URI: </B>" +
                req.getRequestURI() + "<BR>\n" +
                "<B>Request Protocol: </B>" +
                req.getProtocol() + "<BR><BR>\n" +
                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
                "<TR BGCOLOR=\"#FFAD00\">\n" +
                "<TH>Header Name<TH>Header Value");
    Enumeration headerNames = req.getHeaderNames();
    while(headerNames.hasMoreElements()) {
      String headerName = (String)headerNames.nextElement();
      out.println("<TR><TD>" + headerName);
      out.println("    <TD>" + req.getHeader(headerName));
    }
    out.println("</TABLE>\n\n");
    
    
    
    String s = req.getHeader("Host");
   
    out.println("<input type=\"hidden\" id=\"host\" value=\"" + s + "\"/>");
    
    out.print("</body></html>");
    
  }
}
