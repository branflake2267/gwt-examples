package org.gonevertical.MultiFileUpload.client.blobs;

import org.gonevertical.MultiFileUpload.client.rpc.RpcInit;
import org.gonevertical.MultiFileUpload.client.rpc.RpcServiceAsync;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
    
    int r = blobData.length + 1;
    int c = 7;
    Grid grid = new Grid(r, c);
    pWidget.add(grid);
    
    grid.setWidget(0, 0, new HTML("key/name"));
    grid.setWidget(0, 1, new HTML("filename"));
    grid.setWidget(0, 2, new HTML("size"));
    grid.setWidget(0, 3, new HTML("content_type"));
    grid.setWidget(0, 4, new HTML("creation"));
    grid.setWidget(0, 5, new HTML("Path"));
    grid.setWidget(0, 6, new HTML("Delete"));
    
    
    for (int i=0; i < blobData.length; i++) {
      
      String link = "<a target=\"_blank\" href=\"/serve?blob-key=" + blobData[i].getKey() + "\">Download</a>";
      
      HTML na = new HTML(link);
      HTML fn = new HTML(blobData[i].getFilename());
      HTML si = new HTML(Long.toString(blobData[i].getSize()));
      HTML ct = new HTML(blobData[i].getContentType());
      HTML cr = new HTML(blobData[i].getCreation().toGMTString());
      HTML pa = new HTML(blobData[i].getPath());
      
      DeleteWidget dw = new DeleteWidget(blobData[i].getKey());
      
      grid.setWidget(i+1, 0, na);
      grid.setWidget(i+1, 1, fn);
      grid.setWidget(i+1, 2, si);
      grid.setWidget(i+1, 3, ct);
      grid.setWidget(i+1, 4, cr);
      grid.setWidget(i+1, 5, pa);
      grid.setWidget(i+1, 6, dw);
      
     
      dw.addChangeHandler(new ChangeHandler() {
        public void onChange(ChangeEvent event) {
          getBlobsList();
        }
      });
    }
    
  }
  
  public void getBlobsList() {
    
    rpc.getBlobs(filter, new AsyncCallback<BlobData[]>() {
      public void onSuccess(BlobData[] blobData) {
        process(blobData);
      }
      
      public void onFailure(Throwable caught) {
        Window.alert("couldn't get blob list, rpc failed");
      }
    });
    
  }

 
  
}
