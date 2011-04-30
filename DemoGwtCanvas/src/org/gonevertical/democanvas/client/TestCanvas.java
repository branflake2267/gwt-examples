package org.gonevertical.democanvas.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class TestCanvas {

  //private Canvas canvasScreen;
  //private Context2d contextScreen;
  //private FileUpload fileUpload;

  private FlexTable flexTable = new FlexTable();

  public void onModuleLoad() {
    RootPanel.get().add(new HTML("tiles"));
    RootPanel.get().add(flexTable);
    //loadImage();

    flexTable.setCellPadding(0);
    flexTable.setCellSpacing(0);

    RootPanel.get().setWidth("3000px");

    flexTable.setStyleName("test1");

    HTML h = new HTML("<input type=\"file\" id=\"files\" name=\"files\" />");
    RootPanel.get().add(h);

    drawNative(this);
  }

  public void setString(String s) {
    RootPanel.get().add(new HTML(s));

    Image img = new Image(s);
    RootPanel.get().add(img);

    loadImage(img);
  }

  public static native void drawNative(TestCanvas testCanvas) /*-{

    function handleFileSelect(evt) {

      var files = evt.target.files; // FileList object
      var output = [];
      for (var i = 0, f; f = files[i]; i++) {

        //output.push('<li><strong>', f.name, '</strong> (', f.type || 'n/a', ') - ', f.size, '</li>');


         var reader = new FileReader();  
         reader.onload = function (evt) {  
               //alert(evt.target.result);

               x.@org.gonevertical.democanvas.client.TestCanvas::setString(Ljava/lang/String;)(evt.target.result);
         }  
         reader.readAsDataURL(files[i]);

      }

      //document.getElementById('list').innerHTML = '<ul>' + output.join('') + '</ul>';
    }

    $doc.getElementById('files').addEventListener('change', handleFileSelect, false);
  }-*/;



  private void loadImage() {
    final Image image = new Image("/images/pano.jpg");
    RootPanel.get().add(image);
    image.setVisible(false);

    image.addLoadHandler(new LoadHandler() {
      public void onLoad(LoadEvent event) {
        startTilingLevels(image);
      }
    });

  }

  private void loadImage(final Image image) {
    RootPanel.get().add(image);
    image.setVisible(false);

    image.addLoadHandler(new LoadHandler() {
      public void onLoad(LoadEvent event) {
        startTilingLevels(image);
      }
    });

  }

  private void startTilingLevels(Image image) {

    int level = 0;
    startTiling(image, level);

  }

  private void startTiling(Image image, int level) {

    int cutsize = 500;

    int width = image.getWidth();
    int height = image.getHeight();
    double scaleToRatio = (double) cutsize / (double) (height / (level + 1));

    System.out.println("scaleToRatio=" + scaleToRatio + " width=" + width + " x height=" + height);

    ImageData imageData = scaleImage(image, scaleToRatio);

    // debug to screen
    addImageToScreen(imageData, "scaled image");

    tileImage(imageData, cutsize);
  }

  private void tileImage(ImageData imageData, int cutsize) {
    int width = imageData.getWidth();
    int height = imageData.getHeight();

    System.out.println("tileImage width=" + width + " height=" + height + " cutsize=" + cutsize);

    double cols = Math.ceil((double) width / (double) cutsize);
    double rows = Math.ceil((double) height / (double) cutsize);

    for (int r=0; r < rows; r++) {

      for (int c=0; c < cols; c++) {

        tileImage(imageData, cutsize, r, c);
      }

    }

  }

  private void tileImage(ImageData imageData, int cutsize, int r, int c) {

    int sx = c * cutsize; // x offset
    int sy = r * cutsize; // y offset
    int sw = cutsize; // width
    int sh = cutsize; // height

    if (sx + sw > imageData.getWidth()) {
      sw = imageData.getWidth() - sx;
    }

    if (sy + sh > imageData.getHeight()) {
      sh = imageData.getHeight() - sy;
    }

    System.out.println("r=" + r + " c=" + c +" sx=" + sx + " sy=" + sy + " sw=" + sw + " sh=" + sh);

    ImageData newImageData = cropImage(imageData, sx, sy, sw, sh);

    addImage(newImageData, r, c);
  }

  private ImageData cropImage(ImageData imageData, double sx, double sy, double sw, double sh) {

    Canvas canvasTmp = Canvas.createIfSupported();
    Context2d context = canvasTmp.getContext2d();

    canvasTmp.setCoordinateSpaceHeight((int) imageData.getHeight()+100);
    canvasTmp.setCoordinateSpaceWidth((int) imageData.getWidth()+100);

    // draw image to canvas
    context.putImageData(imageData, 0, 0);

    // get image data
    //imageData = context.getImageData(0, 0, imageData.getWidth(), imageData.getHeight());
    ImageData newImageData = context.getImageData(sx, sy, sw, sh);

    addImageToScreen(newImageData, "tile sw=" + sx + " sy=" + sy + " sw=" + sw + " sh=" + sh);

    return newImageData;
  }

  private void addImage(ImageData imageData, int r, int c) {
    int width = imageData.getWidth();
    int height = imageData.getHeight();

    Canvas canvasTmp = Canvas.createIfSupported();
    canvasTmp.setCoordinateSpaceHeight(height);
    canvasTmp.setCoordinateSpaceWidth(width);
    Context2d context = canvasTmp.getContext2d();

    context.putImageData(imageData, 0, 0);

    flexTable.setWidget(r, c, canvasTmp);
    //RootPanel.get().add(canvasTmp);

    String im = canvasTmp.toDataUrl("image/png");
    //System.out.println("imageData=" + im);
  }

  /**
   * image - an ImageElement object
   * 
    sx - the x coordinate of the upper-left corner of the source rectangle
    sy - the y coordinate of the upper-left corner of the source rectangle
    sw - the width of the source rectangle
    sh - the width of the source rectangle
    dx - the x coordinate of the upper-left corner of the destination rectangle
    dy - the y coordinate of the upper-left corner of the destination rectangle
    dw - the width of the destination rectangle
    dh - the height of the destination rectangle

   */
  private ImageData scaleImage(Image image, double scaleToRatio) {

    Canvas canvasTmp = Canvas.createIfSupported();
    Context2d context = canvasTmp.getContext2d();

    double ch = (image.getHeight() * scaleToRatio) + 100;
    double cw = (image.getWidth() * scaleToRatio) + 100;

    canvasTmp.setCoordinateSpaceHeight((int) ch);
    canvasTmp.setCoordinateSpaceWidth((int) cw);

    ImageElement imageElement = ImageElement.as(image.getElement());

    // s = source
    // d = destination 
    double sx = 0;
    double sy = 0;
    double sw = imageElement.getWidth();
    double sh = imageElement.getHeight();

    double dx = 0;
    double dy = 0;
    double dw = imageElement.getWidth();
    double dh = imageElement.getHeight();

    // tell it to scale image
    context.scale(scaleToRatio, scaleToRatio);

    // draw image to canvas
    context.drawImage(imageElement, sx, sy, sw, sh, dx, dy, dw, dh);

    // get image data
    double w = (dw * scaleToRatio);
    double h = dh * scaleToRatio;
    ImageData imageData = context.getImageData(0, 0, w, h);

    System.out.println("scaledImage w=" + w + " h=" + h);

    return imageData;
  }


  private ImageData cropImage(Image image, double sx, double sy, double sw, double sh) {

    Canvas canvasTmp = Canvas.createIfSupported();
    Context2d context = canvasTmp.getContext2d();

    canvasTmp.setCoordinateSpaceHeight((int) sh+100);
    canvasTmp.setCoordinateSpaceWidth((int) sw+100);

    ImageElement imageElement = ImageElement.as(image.getElement());

    double dx = 0;
    double dy = 0;
    double dw = sw;
    double dh = sh;

    // draw image to canvas
    context.drawImage(imageElement, sx, sy, sw, sh, dx, dy, dw, dh);

    // get image data
    double w = sw;
    double h = sh;
    ImageData imageData = context.getImageData(0, 0, w, h);

    return imageData;
  }

  private ImageData scaleAndCropImage(Image image, double scaleToRatio, double sx, double sy, double sw, double sh) {

    Canvas canvasTmp = Canvas.createIfSupported();
    //RootPanel.get().add(canvasTmp);
    Context2d context = canvasTmp.getContext2d();

    canvasTmp.setCoordinateSpaceHeight((int) (image.getHeight() * scaleToRatio)+100);
    canvasTmp.setCoordinateSpaceWidth((int) (image.getWidth() * scaleToRatio)+100);

    ImageElement imageElement = ImageElement.as(image.getElement());

    // tell it to scale image
    context.scale(scaleToRatio, scaleToRatio);

    // draw image to canvas
    // s = source
    // d = destination     
    double dx = 0;
    double dy = 0;
    context.drawImage(imageElement, dx, dy);

    // get image data - if you go greater than the scaled image nothing will show up
    ImageData imageData = context.getImageData(sx, sy, sw, sh);

    return imageData;
  }

  // for debug
  private void addImageToScreen(ImageData imageData, String s) {

    HTML h = new HTML(s);
    RootPanel.get().add(h);

    Canvas canvasTmp = Canvas.createIfSupported();
    RootPanel.get().add(canvasTmp);
    canvasTmp.setStyleName("test1");
    canvasTmp.setCoordinateSpaceHeight((int) imageData.getHeight());
    canvasTmp.setCoordinateSpaceWidth((int) imageData.getWidth());
    Context2d context = canvasTmp.getContext2d();

    context.putImageData(imageData, 0, 0);

    //flexTable.setWidget(r, c, canvasTmp);
    RootPanel.get().add(canvasTmp);
  }

}
