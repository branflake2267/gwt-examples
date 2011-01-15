  package org.gonevertical.demo.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.gonevertical.demo.client.LoginData;
import org.gonevertical.demo.client.rpc.RpcService;
import org.gonevertical.demo.server.jdo.AppTokenJdo;
import org.gonevertical.demo.server.jdo.AppTokenStore;

import com.google.gdata.client.GoogleService;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.blogger.BloggerService;
import com.google.gdata.data.Feed;
import com.google.gdata.data.blogger.BlogEntry;
import com.google.gdata.data.blogger.BlogFeed;
import com.google.gdata.model.atom.Entry;
import com.google.gdata.util.ServiceException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RpcServiceImpl extends RemoteServiceServlet implements RpcService {

  private String consumerKey;
  private String consumerSecret;

  public LoginData getLoginData(String currentUrl) {
    Google_Login gl = new Google_Login();
    return gl.getLoginData(currentUrl);
  }

  public boolean getHasToken(String scope) {
    return AppTokenStore.getHasToken(scope);
  }

  public String getBlogList() {
    initProperties();
    
    String scope = "http://www.blogger.com/feeds/";
    
    AppTokenJdo appToken = AppTokenStore.getToken(scope);
    if (appToken == null) {
      // TODO throw error, need a token for blogger
      return null;
    }
    
    String token = appToken.getAccessTokenKey();
    String tokenSecret = appToken.getAccessTokenSecret();
    
    GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
    oauthParameters.setOAuthConsumerKey(consumerKey);
    oauthParameters.setOAuthConsumerSecret(consumerSecret);
    oauthParameters.setOAuthToken(token);
    oauthParameters.setOAuthTokenSecret(tokenSecret);

    BloggerService blogService = new BloggerService("Gone_Vertical_LLC-DemoGwtGdataOauth-v2");
    try {
      blogService.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
    } catch (OAuthException e) {
      e.printStackTrace();
    }

    URL url = null;
    try {
      url = new URL("http://www.blogger.com/feeds/default/blogs");
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    
    BlogFeed blogFeed = null;
    try {
      blogFeed = blogService.getFeed(url, BlogFeed.class);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ServiceException e) {
      e.printStackTrace();
    } 
    
    List<BlogEntry> blogEntries = blogFeed.getEntries(); 
    
    if (blogEntries == null || blogEntries.size() == 0) {
      return null;
    }

    String s = "";
    for (int i = 0; i < blogEntries.size(); i++) { 
      s += blogEntries.get(i).getTitle().getPlainText() + "<br/>\n";
    } 

    return s;
  }

  /**
   * get properties ready for use. I'm hiding my secret in a text file
   */
  private void initProperties() {

    InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/app.properties");
    Properties props = new Properties();
    try {
      props.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    consumerKey = props.getProperty("consumerKey");
    consumerSecret = props.getProperty("consumerSecret");

    System.out.println("consumerSecret=" + consumerSecret);

  }

}
