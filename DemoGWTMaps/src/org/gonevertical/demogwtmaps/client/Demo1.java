package org.gonevertical.demogwtmaps.client;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HTML;

public class Demo1 extends Composite {
  private VerticalPanel verticalPanel;

  public Demo1() {
    
    verticalPanel = new VerticalPanel();
    initWidget(verticalPanel);
    verticalPanel.setWidth("502px");
    
    HTML htmlMapDemo = new HTML("Map Demo 1", true);
    verticalPanel.add(htmlMapDemo);
    
  }
  
  public void draw() {
    
    Maps.loadMapsApi("ABQIAAAAoVxd5Qo5vFe3MnANAR_5IhRYx8axsN4AsyKitnhUfALC-LKhAxSZDF7IXgIu8dKxMD0q3HFZZusQvQ", "2", false, new Runnable() {
      public void run() {
        buildUi();
      }
    });
    
  }

  private void buildUi() {
    // Open a map centered on Cawker City, KS USA
    LatLng cawkerCity = LatLng.newInstance(39.509, -98.434);

    final MapWidget map = new MapWidget(cawkerCity, 2);
    map.setSize("500PX", "500px");
    map.setUIToDefault();
    map.addMapType(MapType.getHybridMap());
    map.setCurrentMapType(MapType.getHybridMap());
    
    // Add some controls for the zoom level
    //map.addControl(new LargeMapControl());

    // Add a marker
    map.addOverlay(new Marker(cawkerCity));

    // Add an info window to highlight a point of interest
    map.getInfoWindow().open(map.getCenter(), new InfoWindowContent("World's Largest Ball of Sisal Twine"));

    final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
    dock.addNorth(map, 500);

    // Add the map to the HTML host page
    getVerticalPanel().add(dock);
  }
  
  public VerticalPanel getVerticalPanel() {
    return verticalPanel;
  }
  
}
