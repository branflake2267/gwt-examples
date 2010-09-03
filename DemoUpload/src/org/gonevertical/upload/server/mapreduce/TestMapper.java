package org.gonevertical.upload.server.mapreduce;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.tools.mapreduce.AppEngineMapper;

import org.apache.hadoop.io.NullWritable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class TestMapper extends AppEngineMapper<Key, Entity, NullWritable, NullWritable> {
  
  private static final Logger log = Logger.getLogger(TestMapper.class.getName());

  public TestMapper() {
  }

  @Override
  public void taskSetup(Context context) {
    log.warning("Doing per-task setup");
  }

  @Override
  public void taskCleanup(Context context) {
    log.warning("Doing per-task cleanup");
  }

  @Override
  public void setup(Context context) {
    log.warning("Doing per-worker setup");
  }

  @Override
  public void cleanup(Context context) {
    log.warning("Doing per-worker cleanup");
  }

  // This is a silly mapper that's intended to show some of the capabilities of the API.
  @Override
  public void map(Key key, Entity value, Context context) {
    log.warning("Mapping key: " + key);
    if (value.hasProperty("skub")) {
      // Counts the number of jibbit and non-jibbit skub.
      // These counts are aggregated and can be seen on the status page.
      if (value.getProperty("skub").equals("Pro")) {
        context.getCounter("Skub", "Pro").increment(1);
      } else {
        context.getCounter("Skub", "Anti").increment(1);
      }
    }
  }
}
