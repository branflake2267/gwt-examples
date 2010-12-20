package org.gonevertical.MultiFileUpload.client.blobs;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BlobDataFilter implements IsSerializable {

  private long start = 0;
  private long limit = 150;
  
  private String key;
  
  private ArrayList<String> blobKeys;
  
  public void setLimit(long start, long limit) {
    this.start = start;
    this.limit = limit;
  }
  
  /**
   * get start of range
   * @return
   */
  public long getRangeStart() {
    return start;
  }

  /**
   * get end of range finish - this is a work around for the offset
   * @return
   */
  public long getRangeFinish() {
    long finish = start + limit;
    return finish;
  }
  
  public void setBlobKey(String key) {
    this.key = key;
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
  
  
}
