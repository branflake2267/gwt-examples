package org.gonevertical.MultiFileUpload.client.blobs;

import java.util.ArrayList;

import org.gonevertical.MultiFileUpload.client.rpc.RpcInit;
import org.gonevertical.MultiFileUpload.client.rpc.RpcServiceAsync;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Blobs extends Composite {

  private RpcServiceAsync rpc;
  
  private VerticalPanel pWidget = new VerticalPanel();

  private BlobDataFilter filter = new BlobDataFilter();

  private Grid grid;
  
  
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
      pWidget.add(new HTML("No Blobs yet"));
      return;
    }
    
    int r = blobData.length + 1;
    int c = 8;
    grid = new Grid(r, c);
    pWidget.add(grid);
    
    PushButton bDelete = new PushButton("X");
    
    // turn off
    bDelete.setEnabled(false);
    
    bDelete.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        deleteMulti();
      }
    });
    
    CheckBox cb1 = new CheckBox();
    cb1.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        selectAll();
      }
    });
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(bDelete);
    hp.add(cb1);
    
    grid.setWidget(0, 0, new HTML("key/name"));
    grid.setWidget(0, 1, new HTML("filename"));
    grid.setWidget(0, 2, new HTML("size"));
    grid.setWidget(0, 3, new HTML("content_type"));
    grid.setWidget(0, 4, new HTML("creation"));
    grid.setWidget(0, 5, new HTML("Path"));
    grid.setWidget(0, 6, new HTML("Delete"));
    grid.setWidget(0, 7, hp);
    
    
    for (int i=0; i < blobData.length; i++) {
      
      CheckBox cb = new CheckBox();
      
      String link = "<a target=\"_blank\" href=\"" + blobData[i].getPath() + "\">Download</a>";
      String link2 = "<a target=\"_blank\" href=\"" + blobData[i].getPath() + "\">" + blobData[i].getPath() + "</a>";
      
      HTML na = new HTML(link);
      HTML fn = new HTML(blobData[i].getFilename());
      HTML si = new HTML(Long.toString(blobData[i].getSize()));
      HTML ct = new HTML(blobData[i].getContentType());
      HTML cr = new HTML(blobData[i].getCreation().toGMTString());
      HTML pa = new HTML(link2);
      
      DeleteWidget dw = new DeleteWidget(blobData[i].getKey());
      dw.addChangeHandler(new ChangeHandler() {
        public void onChange(ChangeEvent event) {
          delete();
        }
      });
      
      grid.setWidget(i+1, 0, na);
      grid.setWidget(i+1, 1, fn);
      grid.setWidget(i+1, 2, si);
      grid.setWidget(i+1, 3, ct);
      grid.setWidget(i+1, 4, cr);
      grid.setWidget(i+1, 5, pa);
      grid.setWidget(i+1, 6, dw);
      grid.setWidget(i+1, 7, cb);
     
      dw.addChangeHandler(new ChangeHandler() {
        public void onChange(ChangeEvent event) {
          getBlobsList();
        }
      });
    }
    
  }
  
  protected void delete() {
    getBlobsList();
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

  private void deleteMulti() {
    
    ArrayList<String> blobKeys = new ArrayList<String>();
    for (int i=1; i < grid.getRowCount(); i++) {
      CheckBox cb = (CheckBox) grid.getWidget(i, 7);
      if (cb.getValue() == true) {
        DeleteWidget dw = (DeleteWidget) grid.getWidget(i, 6);
        blobKeys.add(dw.getBlobKey());
      }
    }
    
    BlobDataFilter filter = new BlobDataFilter();
    filter.setBlobKeys(blobKeys);
    
    rpc.deleteBlob(filter, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean result) {
        getBlobsList();
      }
      public void onFailure(Throwable caught) {
      }
    });
  }
 
  private void selectAll() {
   
    for (int i=1; i < grid.getRowCount(); i++) {
      CheckBox cb = (CheckBox) grid.getWidget(i, 7);
      if (cb.getValue() == false) {
        cb.setValue(true);
      } else {
        cb.setValue(false);
      }
    }
    
  }
  
}
