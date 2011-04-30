package org.gonevertical.democanvas.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class DemoGwtCanvas implements EntryPoint {

  private LoadingWidget wLoading;

  public void onModuleLoad() {

    ClientPersistence cp = new ClientPersistence();
    
    wLoading = new LoadingWidget();
    
    FileUploaderWidget_v2 fuw = new FileUploaderWidget_v2(cp);
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(fuw);
    hp.add(wLoading);
    
    VerticalPanel pWidget = new VerticalPanel();
    
    VerticalPanel pCenter = new VerticalPanel();
    pCenter.setWidth("100%");
    pCenter.add(pWidget);
    pCenter.setCellHorizontalAlignment(pWidget, HorizontalPanel.ALIGN_CENTER);
    
    RootPanel.get().add(pCenter);
    
    pWidget.add(new HTML("Try uploading an image in chrome only. Due to FileReader API availability."));
    pWidget.add(fuw);
    fuw.draw();

    fuw.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        FileUploaderWidget_v2 f = (FileUploaderWidget_v2) event.getSource();
        int e = f.getChangeEvent();
        processChange(e);
      }
    });
    
    
  }

  private void processChange(int e) {
    
    if (e == EventManager.FILE_UPLOADING) {
      wLoading.show("Uploading in progress...");
      
    } else if (e == EventManager.FILE_DONEUPLOADING) {
      wLoading.hide();
    }
    
  }

}
