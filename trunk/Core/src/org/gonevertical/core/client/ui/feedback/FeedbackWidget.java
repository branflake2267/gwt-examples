package org.gonevertical.core.client.ui.feedback;

import org.gonevertical.core.client.global.LoadingWidget;
import org.gonevertical.core.client.rpc.RpcCore;
import org.gonevertical.core.client.rpc.RpcCoreServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
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
public class FeedbackWidget extends DialogBox implements ClickHandler {

	// rpc init Var 
	private RpcCoreServiceAsync rpc;

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

		bSend.addClickHandler(this);
		bClose.addClickHandler(this);
		cbComment.addClickHandler(this);
		cbSuggestion.addClickHandler(this);

		html.setStyleName("core-feedback-field");
		html1.setStyleName("core-feedback-field");
		html2.setStyleName("core-feedback-field");
		htmlA.setStyleName("core-feedback-field");
		htmlN.setStyleName("core-feedback-field");
		pWidget.addStyleName("core-feedback-padlr");
		pWidget.setWidth("400px");
		tbFromEmail.setWidth("300px");
		tbFromName.setWidth("300px");
		tbSubject.setWidth("300px");
		taMessage.setSize("300px", "300px");


		rpc = RpcCore.initRpc();
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
		fbd.suggestion = cbSuggestion.getValue();

		//get comment
		fbd.comment = cbComment.getValue();

		//get problem
		fbd.problem = cbProblem.getValue();

		//get other
		fbd.other = cbOther.getValue();

		//get post
		fbd.post = cbPost.getValue();

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
		loading.show();
		pNotfication.add(loading);	

		pNotfication.add(ss);

		Timer t = new Timer() {
			public void run() {
				pNotfication.clear();
				loading.hide();

				if (close == true) {
					setHide();		
					//reset vars
					cbComment.setValue(false);
					cbSuggestion.setValue(false);
					cbProblem.setValue(false);
					cbOther.setValue(false);
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


	public void onClick(ClickEvent event) {
		Widget sender = (Widget) event.getSource();

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

		if (cbSuggestion.getValue() == true || cbComment.getValue() == true) {
			cbPost.setVisible(true);
		} 

		if (cbSuggestion.getValue() == false && cbComment.getValue() == false) {
			cbPost.setVisible(false);
		}

	}

	/**
	 * save feedback data via rpc to server
	 * 
	 * @param feedbackData
	 */
	public void saveFeedbackData(FeedbackData feedbackData) {

		drawNotificaton("Sending");

		rpc.saveFeedBack(feedbackData, new AsyncCallback<Boolean>() {
			public void onSuccess(Boolean result) {
				close = true;
				drawNotificaton("Thanks for sending feedback.");
			}
			
			public void onFailure(Throwable caught) {
				Window.alert("There was an error saving to database");
			}
		});
	}


}
