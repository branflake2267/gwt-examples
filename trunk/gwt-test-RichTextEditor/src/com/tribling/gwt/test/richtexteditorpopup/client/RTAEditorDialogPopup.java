package com.tribling.gwt.test.richtexteditorpopup.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * 
 * 
 * @author branflake2267
 *
 */
public class RTAEditorDialogPopup extends DialogBox implements ClickListener {

		// change listeners for this widget
		private ChangeListenerCollection changeListeners;
		
		// main panel
		private VerticalPanel pWidget = new VerticalPanel();
		
		private VerticalPanel pInputTest = new VerticalPanel();
		
		//rta input (in popout editor)
		private RichTextArea input = new RichTextArea();
	
		// Buttons
		private PushButton bUpdate = new PushButton("Update");
		private PushButton bClose = new PushButton("Cancel");
		
		// Vars
		private String sSelected = null;
		
		/**
		 * constructor - init widget
		 */
		public RTAEditorDialogPopup() {

			setText("RTA Edit");

			//buttons
			HorizontalPanel hpButtons = new HorizontalPanel();
			hpButtons.setSpacing(4);
			hpButtons.add(bClose);
			hpButtons.add(bUpdate);
			
			//toolbar
		    RichTextToolbar tb = new RichTextToolbar(input);

		    //setup widget
		    pWidget.add(tb);
		    pWidget.add(input);
		    pWidget.add(pInputTest); //testing below not used on post
			pWidget.add(hpButtons);
			
			// init widget
			setWidget(pWidget);
			
			//Style
			pWidget.setCellHorizontalAlignment(hpButtons, HorizontalPanel.ALIGN_CENTER);
			input.setWidth("100%");
			input.setHeight("300");
			
			// observe
			bUpdate.addClickListener(this);
			bClose.addClickListener(this);
		
		}
		
		/**
		 * get rta text
		 * 
		 * @return html
		 */
		public String getRTAText() {
			return input.getHTML();
		}
		
		/**
		 * set rta text
		 * 
		 * @param text html
		 */
		public void setRTAText(String text) {
			input.setHTML(text);
		}
		
		
		/**
		 * draw rta timed
		 * 
		 * this will work, but only the first time 
		 */
		private void drawRTATimed() {
			
			Timer t = new Timer() {
				public void run() {
					drawRta();
				}
			};
			t.schedule(5);
			
		}
		
		/**
		 * init rta after timed event
		 */
		private void drawRta() {
			
			// init rta
			RichTextArea rtaInput = new RichTextArea();
			
			// init rta toolbar
			RichTextToolbar rtaToolbar = new RichTextToolbar(rtaInput);
			
			pInputTest.add(rtaToolbar);
			pInputTest.add(rtaInput);
			
			rtaInput.setWidth("100%");
			rtaInput.setHeight("300");
			
			this.center();
		}
		
		/**
		 * observe
		 */
		public void onClick(Widget sender) {
				
			if (sender == bUpdate) {
				this.sSelected = "update";
			}
			
			if (sender == bClose) {
				this.sSelected = "close";
			}
			
			//notify calendar of change
			if (changeListeners != null) {
				changeListeners.fireChange(this);
			}

		}
		
		/**
		 * get selected 
		 * @return
		 */
		public String getSelected() {
			return this.sSelected;
		}

		/**
		 * observers
		 * @param listener
		 */
		public void addChangeListener(ChangeListener listener) {
			if (changeListeners == null)
				changeListeners = new ChangeListenerCollection();
			changeListeners.add(listener);
		}

		public void removeChangeListener(ChangeListener listener) {
			if (changeListeners != null)
				changeListeners.remove(listener);
		}
	}
