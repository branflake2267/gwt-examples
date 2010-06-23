package org.gonevertical.core.client.ui.login;

import org.gonevertical.core.client.oauth.Sha1;
import org.gonevertical.core.client.ui.admin.thing.ThingData;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ChangePasswordData implements IsSerializable {

  private ThingData thingData = null;
  
  private String password = null;

  private String signature = null;
  
  public void setData(ThingData thingData, String newPassword) {
    this.thingData = thingData;
    this.password = newPassword;
  }
  
  public ThingData getThingData() {
    return thingData;
  }
  
  public String getSecret() {
    return password;
  }
  
  public void sign() {
    Sha1 sha = new Sha1();
    this.signature = sha.b64_sha1(getSignatureBaseString());
  }
  
  public boolean verifySignature() {
    Sha1 sha = new Sha1();
    boolean pass = false;
    if (this.signature.equals(sha.b64_sha1(getSignatureBaseString()))) {
      pass = true;
    }
    return pass;
  }
  
  private String getSignatureBaseString() {
    String s = "";
    s += "password=" + password + "&";
    s += "thingId=" + thingData.getThingId();
    return s;
  }
  
}
