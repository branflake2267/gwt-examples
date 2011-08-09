package local.hackathon.client.watermark;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.ImageData;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class ImageUtils {

  public static ImageData cropImage(Image image, double sx, double sy, double sw, double sh) {
    
    Canvas canvasTmp = Canvas.createIfSupported();
    Context2d context = canvasTmp.getContext2d();

    canvasTmp.setCoordinateSpaceHeight((int) sh+10);
    canvasTmp.setCoordinateSpaceWidth((int) sw+10);
    
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
    
    canvasTmp.removeFromParent();
    
    return imageData;
  }
  
  public static ImageData scaleAndCropImage(Image image, double scaleToRatio, double sx, double sy, double sw, double sh) {
    
    Canvas canvasTmp = Canvas.createIfSupported();
    //RootPanel.get().add(canvasTmp);
    Context2d context = canvasTmp.getContext2d();
    
    double ch = new BigDecimal(image.getHeight()).multiply(new BigDecimal(scaleToRatio)).setScale(0, RoundingMode.DOWN).intValue();
    double cw = new BigDecimal(image.getWidth()).multiply(new BigDecimal(scaleToRatio)).setScale(0, RoundingMode.DOWN).intValue();
  
    canvasTmp.setCoordinateSpaceHeight((int) ch);
    canvasTmp.setCoordinateSpaceWidth((int) cw);
    
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
  
  /**
   * for debugging
   * 
   * @param imageData
   */
  public static void addImageToScreen(ImageData imageData) {

    Canvas canvasTmp = Canvas.createIfSupported();
    canvasTmp.setCoordinateSpaceHeight((int) imageData.getHeight()+10);
    canvasTmp.setCoordinateSpaceWidth((int) imageData.getWidth()+10);
    Context2d context = canvasTmp.getContext2d();
    
    context.putImageData(imageData, 0, 0);
    
    //flexTable.setWidget(r, c, canvasTmp);
    RootPanel.get().add(canvasTmp);
  }
  
  public static ImageData scaleImage(Image image, int scaleToHeight) {
    if (scaleToHeight == 0) {
      System.out.println("ImageUtils.scaleImage(): Error: scaleToHeight=0");
      return null;
    }
    int width = image.getWidth();
    int height = image.getHeight();
    double scaleToRatio = (double) scaleToHeight / (double) height;
    ImageData imageData = scaleImage(image, scaleToRatio);
    return imageData;
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
  public static ImageData scaleImage(Image image, double scaleToRatio) {
    
    if (scaleToRatio == 0) {
      System.out.println("ImageUtils.scaleImage(): Error: Why is scaleToRatio=0?");
      return null;
    }
    
    //System.out.println("PanoTiler.scaleImage(): scaleToRatio=" + scaleToRatio + " width=" + image.getWidth() + " x height=" + image.getHeight());
    
    Canvas canvasTmp = Canvas.createIfSupported();
    Context2d context = canvasTmp.getContext2d();

    double ch = new BigDecimal(image.getHeight()).multiply(new BigDecimal(scaleToRatio)).setScale(0, RoundingMode.DOWN).intValue();
    double cw = new BigDecimal(image.getWidth()).multiply(new BigDecimal(scaleToRatio)).setScale(0, RoundingMode.DOWN).intValue();
    
    //System.out.println("scaleImage(): ch=" + ch + " cw=" + cw);
    
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
    ImageData imageData = null;
    try {
      imageData = context.getImageData(0, 0, cw, ch);
    } catch (Exception e) {
      System.out.println("ImageUtils.scaleImage(): Error: ScaleToRatio=" + scaleToRatio + " Probably couldn't get width on image before");
      e.printStackTrace();
    }

    //System.out.println("ImageUtils.scaleImage(): Info: scaled image size width=" + cw + " height=" + ch);
    
    return imageData;
  }
  
  public static ImageData cropImage(ImageData imageData, double sx, double sy, double sw, double sh) {
    
    Canvas canvasTmp = Canvas.createIfSupported();
    canvasTmp.setStyleName("mainCanvas");
    Context2d context = canvasTmp.getContext2d();

    canvasTmp.setCoordinateSpaceHeight((int) imageData.getHeight()+10);
    canvasTmp.setCoordinateSpaceWidth((int) imageData.getWidth()+10);
    
    // draw image to canvas
    context.putImageData(imageData, 0, 0);
         
    // get image data
    //imageData = context.getImageData(0, 0, imageData.getWidth(), imageData.getHeight());
    ImageData newImageData = context.getImageData(sx, sy, sw, sh);

    return newImageData;
  }

}
