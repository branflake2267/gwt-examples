package org.gonevertical.democanvas.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BlobDataFilter implements IsSerializable {

  private long offset = 0;
  private long limit = 50;
  
  private String key;
  
  private ArrayList<String> blobKeys;
  
  private long parentThingId;
  
  private long thingId;
  private long keyStuffId;
  
  /**
   * get the total
   */
  private boolean totalGet = true;
  
  private Integer trans_width;
  private Integer trans_height;
  private Integer trans_xOffset;
  private Integer trans_yOffset;
  private Boolean tile;
  
  /**
   * constructor - data transport
   */
  public BlobDataFilter() {
  }
  
  public void setLimit(long offset, long limit) {
    this.offset = offset;
    this.limit = limit;
  }
  
  /**
   * get start of range
   * @return
   */
  public long getRangeOffset() {
    return offset;
  }

  /**
   * get end of range finish - this is a work around for the offset
   * @return
   */
  public long getRangeFinish() {
    long finish = offset + limit;
    return finish;
  }
  
  public long getOffset() {
    return offset;
  }
  
  public long getLimit() {
    return limit;
  }
  
  public void setBlobKey(String blobKey) {
    this.key = blobKey;
  }
  
  public String getBlobKey() {
    return key;
  }

  public void setBlobKeys(ArrayList<String> blobKeys) {
    this.blobKeys = blobKeys;
  }

  public ArrayList<String> getBlobKeys() {
    return blobKeys;
  }

  public void setParentThingId(long thingId) {
    this.parentThingId = thingId;
  }
  
  public long getParentThingId() {
    return parentThingId;
  }

  public void setTotalGet(boolean b) {
    totalGet = b;
  }
  
  public boolean getTotalGet() {
    return totalGet;
  }
  
  public void setBlobKeyStuffId(long stuffId) {
    this.keyStuffId = stuffId;
  }
  
  public long getBlobKeyStuffId() {
    return keyStuffId;
  }
  
  public void setThingId(long thingId) {
    this.thingId = thingId;
  }
  
  public long getThingId() {
    return thingId;
  }

  public void setTransformWidth(Integer width) {
    this.trans_width = width;
  }
  public Integer getTransformWidth() {
    return trans_width;
  }

  public void setTransformHeight(Integer height) {
    this.trans_height = height;
  }
  public Integer getTransformHeight() {
    return trans_height;
  }

  public void setTransformXoffset(Integer xOffset) {
    this.trans_xOffset = xOffset;
  }
  public Integer getTransformXOffset() {
    return trans_xOffset;
  }

  public void setTransformYoffset(Integer yOffset) {
    this.trans_yOffset = yOffset;
  }
  public Integer getTransformYOffset() {
    return trans_yOffset;
  }

  public void setTile(Boolean tile) {
    this.tile = tile;
  }
  
  public Boolean getTile() {
    return tile;
  }
  
}
