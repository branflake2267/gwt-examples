package com.tribling.gwt.test.feedback.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * feed back widget dialog popup - get the data and save it to the server via rpc
 * 
 * @author branflake2267
 *
 */
public class FeedbackWidget extends DialogBox implements ClickListener {
	
	// rpc init Var 
	private FeedbackServiceAsync callProvider;
	
	// persistent for user's session 
	private String SessionID = null;
	
	//main widget panel
	private VerticalPanel pWidget = new VerticalPanel();
		
	//notification panel
	private HorizontalPanel pNotfication = new HorizontalPanel();
	
	private TextBox tbFromEmail = new TextBox();
	private TextBox tbFromName = new TextBox();
	private TextBox tbSubject = new TextBox();
	private TextArea taMessage = new TextArea();
	
	private CheckBox cbSuggestion = new CheckBox("Suggestion");
	private CheckBox cbComment = new CheckBox("Comment");
	private CheckBox cbProblem = new CheckBox("Report a problem");
	private CheckBox cbOther = new CheckBox("Other");
	private CheckBox cbPost = new CheckBox("May we post your comment online?");

	private PushButton bSend = new PushButton("Send");
	private PushButton bClose = new PushButton("Cancel");
	
	//use for closing after rpc
	private boolean close = false;
	
	/**
	 * constructor - init widget
	 */
	public FeedbackWidget() {
		
		// window title/caption
		setText("Send Feedback"); // set style for this title/caption here in sytle.css-> .gwt-DialogBox .Caption {};
		
		String s = "Sends us feedback.";
		HTML htmlT = new HTML(s);
		
		HTML html = new HTML("Your Email");
		HorizontalPanel hpEmail = new HorizontalPanel();
		hpEmail.setSpacing(4);
		hpEmail.add(html);
		hpEmail.add(tbFromEmail);
		
		HTML htmlN = new HTML("Your Name");
		HorizontalPanel hpN = new HorizontalPanel();
		hpN.setSpacing(4);
		hpN.add(htmlN);
		hpN.add(tbFromName);
		
		
		VerticalPanel pAbout = new VerticalPanel();
		pAbout.add(cbSuggestion);
		pAbout.add(cbComment);
		pAbout.add(cbProblem);
		pAbout.add(cbOther);
		
		HTML htmlA = new HTML("");
		HorizontalPanel hpA = new HorizontalPanel();
		hpA.setSpacing(4);
		hpA.add(htmlA);
		hpA.add(pAbout);

		HTML html1 = new HTML("Subject");
		HorizontalPanel hp1 = new HorizontalPanel();
		hp1.setSpacing(4);
		hp1.add(html1);
		hp1.add(tbSubject);
		
		HTML html2 = new HTML("Message");
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.setSpacing(4);
		hp2.add(html2);
		hp2.add(taMessage);
		
		HorizontalPanel hp3 = new HorizontalPanel();
		hp3.setSpacing(4);
		hp3.add(bClose);
		hp3.add(bSend);
		hp3.add(pNotfication);
		
		pWidget.add(htmlT);
		pWidget.add(hpEmail);
		pWidget.add(hpN);
		pWidget.add(hpA);
		pWidget.add(hp1);
		pWidget.add(hp2);
		pWidget.add(cbPost);
		pWidget.add(hp3);
		
		
		// init widget
		setWidget(pWidget);
		
		cbPost.setVisible(false);
				
		bSend.addClickListener(this);
		bClose.addClickListener(this);
		cbComment.addClickListener(this);
		cbSuggestion.addClickListener(this);
		
		html.setStyleName("field");
		html1.setStyleName("field");
		html2.setStyleName("field");
		htmlA.setStyleName("field");
		htmlN.setStyleName("field");
		pWidget.addStyleName("padLR");
		pWidget.setWidth("400px");
			tbFromEmail.setWidth("300px");
			tbFromName.setWidth("300px");
			tbSubject.setWidth("300px");
			taMessage.setSize("300px", "300px");
			
	
		
		// init rpc
		initRpc();
	}
	
	/**
	 * prepare data for rpc transport
	 */
	private void getFeedbackDataThenSave() {
		
		//prepare for rpc transport
		FeedbackData fbd = new FeedbackData();
		
		//get From Email
		fbd.fromEmail = tbFromEmail.getText();
		
		//get From Name
		fbd.fromName = tbFromName.getText();
		
		//get Subject
		fbd.subject = "gawkat.com: " + tbSubject.getText();
		
		//get message
		fbd.message = "gawkat.com: " + taMessage.getText();
		
		//get suggestion
		fbd.suggestion = cbSuggestion.isChecked();
		
		//get comment
		fbd.comment = cbComment.isChecked();
		
		//get problem
		fbd.problem = cbProblem.isChecked();
		
		//get other
		fbd.other = cbOther.isChecked();
		
		//get post
		fbd.post = cbPost.isChecked();
		
		//error check??
		if (taMessage.equals("")) {
			drawNotificaton("Please add a message.");
			return;
		}
		
		saveFeedbackData(fbd);
	}
	
	/**
	 * draw notification
	 * @param s
	 */
	private void drawNotificaton(String s) {
		pNotfication.clear();
		
		final LoadingWidget loading = new LoadingWidget();
		
		//add spacing
		s = "&nbsp;" + s;
		HTML ss = new HTML(s);
		
		//adding loading
		loading.showLoading();
		pNotfication.add(loading);	
		
		pNotfication.add(ss);

		Timer t = new Timer() {
			public void run() {
				pNotfication.clear();
				loading.hideLoading();
				
				if (close == true) {
					setHide();		
					//reset vars
					cbComment.setChecked(false);
					cbSuggestion.setChecked(false);
					cbProblem.setChecked(false);
					cbOther.setChecked(false);
					tbSubject.setText("");
					taMessage.setText("");
				}
			}
		};
		t.schedule(2000);
		
	}
	
	/**
	 * hide feedback 
	 */
	private void setHide() {
		this.hide();
		this.clear();
	}
	
	/**
	 * observe widget
	 */
	public void onClick(Widget sender) {
		
		if (sender == bSend) {
			getFeedbackDataThenSave();
		}
		
		if (sender == bClose) {
			this.hide();
			this.clear();
		}
		
		if (sender == cbSuggestion) {
			setPost();
		}
		
		if (sender == cbComment) {
			setPost();
		}
	
	}
	
	/**
	 * turn on cbPost checkbox if Suggestion or Comment are selected
	 */
	private void setPost() {
		
		if (cbSuggestion.isChecked() == true || cbComment.isChecked() == true) {
			cbPost.setVisible(true);
		} 
		
		if (cbSuggestion.isChecked() == false && cbComment.isChecked() == false) {
			cbPost.setVisible(false);
		}
		
	}
	
	/**
	 * set SessionID
	 * 
	 * @param SessionID
	 */
	public void setSessionID(String SessionID) {
		this.SessionID = SessionID;
	}
	
	/**
	 * Init the RPC provider
	 */
    private void initRpc() {
    	callProvider = (FeedbackServiceAsync) GWT.create(FeedbackService.class);
        ServiceDefTarget target = (ServiceDefTarget) callProvider;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "FeedbackService";
        target.setServiceEntryPoint(moduleRelativeURL);
    }
    
	/**
	 * save feedback data via rpc to server
	 * 
	 * @param feedbackData
	 */
	public void saveFeedbackData(FeedbackData feedbackData) {
		
		drawNotificaton("Sending");
		
		AsyncCallback callback = new AsyncCallback() {	
			
			public void onFailure(Throwable ex) {
				RootPanel.get().add(new HTML(ex.toString()));
			}
			//SUCESS
			public void onSuccess(Object result) {
				
				drawNotificaton("Thank you for your feedback.");
				close = true;
				
			}
		};
		
		// execute rpc and wait for its response
		callProvider.saveFeedBack(SessionID, feedbackData, callback);
	}
}
