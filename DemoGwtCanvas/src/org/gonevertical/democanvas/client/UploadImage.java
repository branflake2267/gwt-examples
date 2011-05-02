package org.gonevertical.democanvas.client;

import java.util.HashMap;
import java.util.Random;

import org.gonevertical.democanvas.client.rpc.RpcInit;
import org.gonevertical.democanvas.client.rpc.RpcServiceAsync;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class UploadImage extends Composite {

  private ClientPersistence cp;
  private RpcServiceAsync rpc;
  private String url;
  private long parentId;
  private String fileBase64;
  private String fileName;
  private String fileContentType;
  private int changeEvent;
  private static Random random = new Random(); 

  public UploadImage(ClientPersistence cp) {
    this.cp = cp;
    rpc = RpcInit.init();
  }

  public void setFile(long linkToParentId, String fileName, String fileContentType, String fileBase64) {
    this.parentId = linkToParentId;
    this.fileName = fileName;
    this.fileContentType = fileContentType;
    this.fileBase64 = fileBase64;

    fixExt();
    splitBase64();
  }

  private void fixExt() {
    fileName = fileName.replaceFirst("\\..*", ".png");
    System.out.println("UploadImage.fixExt(): " + fileName);
  }

  private void splitBase64() {
    if (fileBase64 == null || fileBase64.length() == 0 || fileBase64.contains(",") == false) {
      return;
    }
    String[] fileBase64Split = fileBase64.split(",");
    if (fileBase64Split == null || fileBase64Split.length == 0) {
      return;
    }
    fileBase64 = fileBase64Split[1].trim();
  }

  public void upload() {
    getRpc_NewBlobKeyUrl();
  }

  private void getRpc_NewBlobKeyUrl() {
    cp.showLoading(true);

    // TODO use this in the future
    BlobDataFilter filter = new BlobDataFilter();

    rpc.getBlobStoreUrl(filter, new AsyncCallback<String>() {
      public void onSuccess(String url) {
        cp.showLoading(false);
        setUrl(url);
        send();
      }

      public void onFailure(Throwable caught) {
        cp.setRpcFailure(caught);
      }
    });

  }

  private void setUrl(String url) {
    this.url = url;
  }

  private void send() {
    cp.showLoading(true);

    String boundary = createBoundary();

    String requestData = getRequestData(boundary);

    //System.out.println(requestData);
    //url = "/TestOut/";

    //Window.alert("requestData=" + requestData);
    RootPanel.get().add(new HTML("<br>requestData=" + requestData));

    RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, url);
    builder.setHeader("Content-Type", "multipart/form-data; boundary=" + boundary);
    builder.setHeader("Content-Length", Long.toString(requestData.length()));
    try {
      builder.sendRequest(requestData, new RequestCallback() {
        public void onResponseReceived(Request request, Response response) {
          cp.showLoading(false);
          if (response.getStatusCode() == 200) {
            processResponse(response);
          }
        }
        public void onError(Request request, Throwable exception) {
          fireChange(EventManager.FILE_DONEUPLOADING);
          cp.showLoading(false);
          exception.printStackTrace();
        }
      });
    } catch (RequestException e) {
      cp.showLoading(false);
      e.printStackTrace();
    }
  }

  private String createBoundary() {
    return "----GoneVerticalBoundary" + getRandomStr() + getRandomStr();
  }

  private String getRandomStr() {
    return Long.toString(random.nextLong(), 36);
  }

  private String getRequestData(String boundary) {
    String s = "";

    
    s += "--" + boundary + "\r\n";
    s += getRequestParameter("oid", 1234567890 + "");

    /*
    s += "--" + boundary + "\r\n";
    s += getRequestParameter("ltid", parentId + "");
    */

    s += "--" + boundary + "\r\n";
    s += getRequest_Image(fileName, fileContentType, fileBase64);

    s += "--" + boundary + "--\r\n"; //end

    return s;
  }

  private String getRequestParameter(String key, String value) {
    return "Content-Disposition: form-data; name=\"" + key + "\"\r\n\r\n" + value + "\r\n";
  }

  private String getRequest_Image(String fileName, String contentType, String file) {
    String s = "";
    s += "Content-Disposition: form-data; name=\"File\"; filename=\"" + fileName.trim() + "\"\r\n";
    s += "Content-Type: " + contentType.trim() + "\r\n";
    s += "Content-Transfer-Encoding: base64 \r\n\r\n";
    s += file.trim();
    s += "\r\n";
    return s;
  }

  public int getChangeEvent() {
    return changeEvent;
  }

  private void fireChange(int changeEvent) {
    this.changeEvent = changeEvent;
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }

  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }

  private void processResponse(Response response) {
    
    if (1==1) {
      return;
    }
    
    if (response == null) {
      // TODO error
      return;
    }

    String result = response.getText();

    System.out.println("response=" + result);

    String err = "Sorry. I the server glitched and wasn't able to upload that file. Debug: " + response;

    if (result == null || result.contains("Error") == true) {
      Window.alert(err);
      return;
    }

    // some reason we have <pre...></>
    result = result.replaceAll("<.*?>", "");
    result = result.replaceAll("&amp;", "&");

    // did it send back a stuffId
    HashMap<String, String> params = Global_String.getParameters("&", result);
    if (params == null) {
      Window.alert(err);
      return;
    }

    String stid = params.get("tid");
    long tId = 0;
    if (stid != null) {
      try {
        tId = Long.parseLong(stid);
      } catch (NumberFormatException e) {
        e.printStackTrace();
        Window.alert(err);
        return;
      }
    }

    String ssid = params.get("sid");
    long stuffId = 0;
    if (ssid != null) {
      try {
        stuffId = Long.parseLong(ssid);
      } catch (NumberFormatException e) {
        e.printStackTrace();
        Window.alert(err);
        return;
      }
    }


  }

 
}
