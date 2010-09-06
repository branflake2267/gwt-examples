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
    log.info("Offset: " + key.getOffset() + " Line: " + line);

    String skipRow1 = context.getConfiguration().get("skipRow1");
    if (key.getOffset() == 0 && skipRow1 != null && skipRow1.equals("0") == true ) {
      return;
    }
    
    //book, chapter, verse, content, version

    String delimiter = context.getConfiguration().get("delimiter");
    if (delimiter == null || delimiter.length() == 0) {
      delimiter = ",";
    }
    
    String[] values = line.split(delimiter);

    int book = Integer.parseInt(values[0].replaceAll("\"", "")); // book
    int chapter = Integer.parseInt(values[1].replaceAll("\"", "")); // chapter
    int verse = Integer.parseInt(values[2].replaceAll("\"", "")); // verse
    String content = values[3]; // content 
    String version = values[4]; // version

    content = content.replaceAll("\"", "");
    version = version.replaceAll("\"", "");

    Entity e = new Entity("Verse");
    e.setProperty("book", book);
    e.setProperty("chapter", chapter);
    e.setProperty("verse", verse);
    e.setProperty("content", content);
    e.setProperty("version", version);
    
    DatastoreMutationPool mutationPool = this.getAppEngineContext(context).getMutationPool();
    mutationPool.put(e);
  }

}

