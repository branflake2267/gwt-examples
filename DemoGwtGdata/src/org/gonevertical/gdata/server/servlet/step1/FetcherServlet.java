package org.gonevertical.gdata.server.servlet.step1;

import com.google.gdata.client.http.AuthSubUtil;

import java.io.IOException;

import javax.servlet.http.*;

public class FetcherServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    // Store the incoming request URL
    String nextUrl = request.getRequestURL().toString();
    
    nextUrl.replace("step1", "step2");

    // Generate the AuthSub URL
    String requestUrl = AuthSubUtil.getRequestUrl(nextUrl, "http://docs.google.com/feeds/", false, true);

    response.getWriter().print("<a href=\"" + requestUrl + "\">Request token for the Google Documents Scope</a>");
  
  }
}
