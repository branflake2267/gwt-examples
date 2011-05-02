package org.gonevertical.democanvas.client;

import org.gonevertical.democanvas.client.rpc.RpcInit;
import org.gonevertical.democanvas.client.rpc.RpcServiceAsync;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

/**
 * this is a bit messy from experimentation
 * 
 * @author branflake2267
 *
 */
public class FileUploaderWidget_v2 extends Composite {

  public static final int thumbsizeHeight = 150;
  
  private ClientPersistence cp;
  private RpcServiceAsync rpcCore;
  
  private VerticalPanel pWidget = new VerticalPanel();
  private VerticalPanel pUpload = new VerticalPanel();
  private VerticalPanel pFullSize = new VerticalPanel();
  private VerticalPanel pThumb = new VerticalPanel();

  private String url;

  private int changeEvent;
  
  private String fileName;

  private int uploadCount;
  
  private PushButton bCancel = new PushButton("X");

  private FileUpload fileUpload;

  /**
   * constructor
   * 
   * @param cp
   */
  public FileUploaderWidget_v2(ClientPersistence cp) {
    this.cp = cp;
    
    HorizontalPanel hp = new HorizontalPanel();
    hp.add(pUpload);
    pUpload.setWidth("100%");
    hp.setCellWidth(pUpload, "100%");
    hp.setCellHorizontalAlignment(pUpload, HasHorizontalAlignment.ALIGN_CENTER);
    hp.setCellVerticalAlignment(pUpload, HasVerticalAlignment.ALIGN_MIDDLE);
    hp.add(bCancel);
    hp.setCellHorizontalAlignment(bCancel, HasHorizontalAlignment.ALIGN_RIGHT);
    hp.setCellVerticalAlignment(bCancel, HasVerticalAlignment.ALIGN_MIDDLE);
    
    pWidget.add(hp);
    hp.setWidth("100%");
    pWidget.setCellWidth(hp, "100%");
    pWidget.add(pFullSize);
    pWidget.add(pThumb);
    
    initWidget(pWidget);
    
    pWidget.setWidth("100%");
    
    //pWidget.addStyleName("test2");
    //hp.addStyleName("test3");
    //pFullSize.addStyleName("test3");
    
    setup();
  }
  
  private void setup() {
    rpcCore = RpcInit.init();
    
    bCancel.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        removeFromParent();
      }
    });
  }

  public void draw() {
    
    fileUpload = new FileUpload();
    pUpload.add(fileUpload);
    
    Element e = fileUpload.getElement();
    
    observeImageData(this, e);
  }

  // TODO add file name send back, contentType
  
  /**
   * observe changes on the file upload input
   * 
   * @param fuw this class (widget)
   * @param e
   */
  private static native void observeImageData(FileUploaderWidget_v2 fuw, Element e) /*-{
    function handleFileSelect(evt) {
      var files = evt.target.files;
      var output = [];
      for (var i = 0, f; f = files[i]; i++) {
         var name = f.name;
         var type = f.type; 
         var reader = new FileReader();  
         reader.onload = function (evt) { 
           var base64 = evt.target.result;
           fuw.@org.gonevertical.democanvas.client.FileUploaderWidget_v2::setImage(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(base64, name, type);
         }  
         reader.readAsDataURL(files[i]); 
      }
    }
    e.addEventListener('change', handleFileSelect, false);
  }-*/;
  
  /**
   * process base64 mime imagedata content string
   * 
   * @param imageData
   */
  public void setImage(String imageData, String fileName, String contentType) {
    this.fileName = fileName;
    //this.contentType = contentType;
    
    RootPanel.get().add(new HTML("setImage()=" + imageData));
    
    fileUpload.setEnabled(false);
    bCancel.setEnabled(false);
    
    fireChange(EventManager.FILE_UPLOADING);
    
    final Image image = new Image(imageData);
    image.addStyleName("test1");
    //pProcess.setVisible(false);
    pFullSize.add(image);
    image.addLoadHandler(new LoadHandler() {
      public void onLoad(LoadEvent event) {
        drawToCanvas(image);
      }
    });
  }
  
  private void drawToCanvas(Image image) {
    
    drawThumbToScreen(image);

    upload(image);
    
  }

  private void drawThumbToScreen(Image image) {

    int size = 75;
    
    int width = image.getWidth();
    int height = image.getHeight();
    
    double scaleToRatio = 0.50D; // TODO oops, getting image loading problem. event loading, fix later...
    
    ImageData imageDataForThumb = ImageUtils.scaleImage(image, scaleToRatio);
    
    // for debugging
    Canvas canvasTmp = Canvas.createIfSupported();
    canvasTmp.setCoordinateSpaceHeight((int) imageDataForThumb.getHeight());
    canvasTmp.setCoordinateSpaceWidth((int) imageDataForThumb.getWidth());
    Context2d context = canvasTmp.getContext2d();
    context.putImageData(imageDataForThumb, 0, 0);
    pThumb.add(canvasTmp);
    
    canvasTmp.addStyleName("test2");
  }

  private void upload(final Image image) {

    Canvas canvasTmp = Canvas.createIfSupported();
    canvasTmp.setCoordinateSpaceHeight((int) image.getHeight());
    canvasTmp.setCoordinateSpaceWidth((int) image.getWidth());
    Context2d context = canvasTmp.getContext2d();
    ImageElement imageElement = ImageElement.as(image.getElement());
    context.drawImage(imageElement, 0, 0);
    
    String contentType = "image/png";
    
    String fileBase64 = canvasTmp.toDataUrl(contentType);
    
    UploadImage iu = new UploadImage(cp);
    iu.setFile(1234568759L, fileName, contentType, fileBase64);
    
    
    // TODO enable this if your testing it!!!!!!!!!!
    iu.upload();
    
    
    
    iu.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {
        UploadImage u = (UploadImage) event.getSource();
        int e = u.getChangeEvent();
        fireChange(e);
      }
    });
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
  
  
}
