package org.gonevertical.upload.server.mapreduce;

import java.util.logging.Logger;
import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.mapreduce.AppEngineMapper;
import com.google.appengine.tools.mapreduce.BlobstoreRecordKey;
import com.google.appengine.tools.mapreduce.DatastoreMutationPool;

/**
 * http://127.0.0.1:8888/serve?blob-key=H_8TrZ0qIBsuJVT7qSNy2A
 * @author branflake2267
 *
 */
public class ImportMapper extends AppEngineMapper<BlobstoreRecordKey, byte[], NullWritable, NullWritable> {

  private static final Logger log = Logger.getLogger(ImportMapper.class.getName());

  @Override
  public void map(BlobstoreRecordKey key, byte[] segment, Context context) {
    
    String line = new String(segment);

    log.info("at offset: " + key.getOffset());
    log.info("got line: " + line);
    
    if (key.getOffset() == 0) {
      log.info("at offset: " + key.getOffset() + " skipping row 0, is the header fields line: " + line);
      return;
    }
    
    System.out.println("offset: " + key.getOffset() + " line: " + line);

    //book, chapter, verse, content, version

    String[] values = line.split(",");

    int book = Integer.parseInt(values[0].replaceAll("\"", "")); // book
    int chapter = Integer.parseInt(values[1].replaceAll("\"", "")); // chapter
    int verse = Integer.parseInt(values[2].replaceAll("\"", "")); // verse
    String content = values[3]; // content 
    String version = values[4]; // version

    content = content.replaceAll("\"", "");
    version = version.replaceAll("\"", "");

    Entity v = new Entity("Verse");
    v.setProperty("book", book);
    v.setProperty("chapter", chapter);
    v.setProperty("verse", verse);
    v.setProperty("content", content);
    v.setProperty("version", version);
    
    DatastoreMutationPool mutationPool = this.getAppEngineContext(context).getMutationPool();
    mutationPool.put(v);
  }

}

