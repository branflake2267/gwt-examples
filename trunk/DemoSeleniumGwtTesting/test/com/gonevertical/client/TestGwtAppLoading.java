package com.gonevertical.client;

import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * {@link http://c.gwt-examples.com} 
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestGwtAppLoading extends TestCase {

  private static WebDriver driver;

  @BeforeClass
  public static void initWebDriver() throws IOException {
    
    // set the path to the chrome driver
    // http://code.google.com/p/chromium/downloads/list
    System.setProperty("webdriver.chrome.driver", "/Users/branflake2267/bin/chromedriver");
    
    // chrome driver will load with no extras, so lets tell it to load with gwtdev plugin
    // get plugin here in chrome url type > "chrome://plugins/" > hit top right details + > find gwt dev mode pluging location:
    DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    String gwtDevPluginPath = "--load-plugin=/Users/branflake2267/Library/Application Support/Google/Chrome/Default/Extensions/jpjpnpmbddbjkfaccnmhnkdgjideieim/1.0.9738_0/Darwin-gcc3/gwtDev.plugin";
    capabilities.setCapability("chrome.switches", Arrays.asList(gwtDevPluginPath));
    
    // init driver
    driver = new ChromeDriver(capabilities);
  }

  @AfterClass
  public static void theEnd() {
    driver.quit();
  }

  @Before
  public void before() {
  }

  @After
  public void after() {
  }

  @Test
  public void testElement() {
    
    
    // navigate to gwt app
    String pathToGwtApp = "http://127.0.0.1:8888/index.html?gwt.codesvr=127.0.0.1:9997";
    driver.get(pathToGwtApp);

    
    WebElement element = driver.findElement(By.id("content"));
    // do something with the element
    
    
    // set a break point on this, b/c there isn't any pausing
    System.out.println("finished");
    
  }
  
}