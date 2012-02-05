package com.gonevertical.client.views.peopleedit.editor;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.gonevertical.client.app.ClientFactory;
import com.gonevertical.client.app.core.LoadingWidget;
import com.gonevertical.client.app.events.EditEvent;
import com.gonevertical.client.app.events.EditEvent.Edit;
import com.gonevertical.client.app.events.EditEventHandler;
import com.gonevertical.client.app.requestfactory.PeopleDataRequest;
import com.gonevertical.client.app.requestfactory.dto.PeopleDataProxy;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.testing.EditorHierarchyPrinter;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.gwt.client.RequestFactoryEditorDriver;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class EditPersonWorkFlow extends Composite {
  
  public static enum State {
    EDITING,
    SAVING;
  }
  
  /**
   * workflow ui creator
   */
  private static EditPersonWorkFlowUiBinder uiBinder = GWT.create(EditPersonWorkFlowUiBinder.class);
  @UiField FlowPanel pPersonEdit;
  @UiField LoadingWidget wLoading;
  @UiField PushButton bFinish;
  @UiField PushButton bSave;
  
  /**
   * workflow xml ui binder
   */
  interface EditPersonWorkFlowUiBinder extends UiBinder<Widget, EditPersonWorkFlow> {}
  
  /** 
   * Empty interface declaration, similar to UiBinder
   */
  interface Driver extends RequestFactoryEditorDriver<PeopleDataProxy, PersonEditor> {}
 
  /**
   * Create the Driver
   */
  private Driver driver = GWT.create(Driver.class);
  
  /**
   * app's client factory
   */
  private ClientFactory clientFactory;
  
  /**
   * the ui composite editor
   */
  private PersonEditor editor;
  
  /**
   * constructor - init the edit workflow
   * @param clientFactory
   */
  public EditPersonWorkFlow(ClientFactory clientFactory) {
    this.clientFactory = clientFactory;
    initWidget(uiBinder.createAndBindUi(this));
    
    setState(State.EDITING);
    
    bFinish.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        fireEvent(new EditEvent<PeopleDataProxy>(Edit.FINISHED));
      }
    });
  }
  
  /**
   * on persist do this
   * @return
   */
  private Receiver<? super PeopleDataProxy> getReciever() {
    Receiver<PeopleDataProxy> r = new Receiver<PeopleDataProxy>() {
      public void onSuccess(PeopleDataProxy response) {
        fireEvent(new EditEvent<PeopleDataProxy>(Edit.SAVED, response));
        
        // re-init context for another save
        edit(response);
      }
      public void onFailure(ServerFailure error) {
        super.onFailure(error);
        fireEvent(new EditEvent<PeopleDataProxy>(Edit.ERRORS));
      }
      public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
        super.onConstraintViolation(violations);
        fireEvent(new EditEvent<PeopleDataProxy>(Edit.VIOLATION));
      }
    };
    return r;
  }

  /**
   * edit object
   * @param peopleDataProxy
   */
  public void edit(PeopleDataProxy peopleDataProxy) {
    initEditor();

    // setup request context
    PeopleDataRequest context = clientFactory.getRequestFactory().getPeopleDataRequest();
    
    // create a new object when null
    if (peopleDataProxy == null) { 
      peopleDataProxy = context.create(PeopleDataProxy.class);
    }
    
    driver.edit(peopleDataProxy, context);
    
    // wire up the persist like a call back when save happens
    // decouple using the event handler to send data
    // also bring back the children, driver.getPaths() is like saying .with("todos")
    context.persist().using(peopleDataProxy).with(driver.getPaths()).to(getReciever());
  }

  /**
   * setup the driver and editor and add to ui
   */
  private void initEditor() {
    if (editor != null) {
      return;
    }
    
    // PersonEditor is a that extends Editor<Person>
    editor = new PersonEditor();
    
    // Initialize the driver with the top-level editor
    driver.initialize(clientFactory.getRequestFactory(), editor);

    // Put the UI on the screen.
    pPersonEdit.add(editor);
  }

  /**
   * flush the editors values and save them, like flushing the toilet and the stuff goes to the sewer plant the database
   * you can hit save over and over, or auto save, and the delta(change) will be sent to the server
   * you can see it here in dev mode: http://localhost:8888/_ah/admin/datastore?kind=PeopleData
   */
  private void save() {
    
    // TODO comes back null, maybe due to no sub editors
//    if (driver.hasErrors()) {
//      // A sub-editor reported errors
//      System.out.println("test");
//    }
    
    fireEvent(new EditEvent<PeopleDataProxy>(Edit.SAVING));
    setState(State.SAVING);
    
    
    // helps for debugging the editor
    String s = EditorHierarchyPrinter.toString(driver);
    System.out.println("EditorHierarchyPrinter = " + s);
    
    
    // fire the persist, which is already added to the context of this call
    // the persist call back is caught above
    RequestContext req = driver.flush();
    req.fire(new Receiver<Void>() {
      
      public void onSuccess(Void response) {
        setState(State.EDITING);
      }
      
      public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
        setState(State.EDITING);
        super.onConstraintViolation(violations);
      }
      
      public void onFailure(ServerFailure error) {
        setState(State.EDITING);
        super.onFailure(error);
      }
    });
    
  }
  
  /**
   * set widget state stuff
   * @param state
   */
  private void setState(State state) {
    if (state == State.EDITING) {
      wLoading.showLoading(false);
      bSave.setEnabled(true);
      
    } else if (state == State.SAVING) {
      wLoading.showLoading(true);
      bSave.setEnabled(false);
    } 
  }
  
  @UiHandler("bSave")
  void onBSaveClick(ClickEvent event) {
    save();
  }
  
  public final HandlerRegistration addEditHandler(EditEventHandler<PeopleDataProxy> handler) {
    return addHandler(handler, EditEvent.TYPE);
  }
  
}
