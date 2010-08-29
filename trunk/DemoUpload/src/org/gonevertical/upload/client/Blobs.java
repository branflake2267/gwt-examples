package org.gonevertical.upload.client;

import org.gonevertical.upload.client.blobs.BlobData;
import org.gonevertical.upload.client.blobs.BlobDataFilter;
import org.gonevertical.upload.client.rpc.RpcInit;
import org.gonevertical.upload.client.rpc.RpcServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Blobs extends Composite {

  private RpcServiceAsync rpc;
  
  private VerticalPanel pWidget = new VerticalPanel();

  private BlobDataFilter filter = new BlobDataFilter();
  
  
  public Blobs() {
    
    initWidget(pWidget);
    
    rpc = RpcInit.init();
  }
  
  public void draw() {
    getBlobsList();
  }
  
  private void process(BlobData[] blobData) {
    
    pWidget.clear();
    
    if (blobData == null) {
      return;
    }
    
    int r = blobData.length;
    int c = 5;
    Grid grid = new Grid(r, c);
    pWidget.add(grid);
    
    for (int i=0; i < blobData.length; i++) {
      HTML fn = new HTML(blobData[i].getFilename());
      grid.setWidget(i, 0, fn);
    }
  }
  
  public void getBlobsList() {
    
    rpc.getBlobs(filter, new AsyncCallback<BlobData[]>() {
      public void onSuccess(BlobData[] blobData) {
        process(blobData);
      }
      
      public void onFailure(Throwable caught) {
        Window.alert("couldn't get blog list, rpc failed");
      }
    });
    
  }

 
  
}
