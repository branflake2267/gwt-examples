package com.tribling.gwt.test.calendar.client;

import java.util.Arrays;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class CalendarWidget extends Composite implements ClickListener, TableListener {

	// Observer to notify the originator object
	private ChangeListenerCollection changeListeners;

	// First day falls on what weekday s,m,t,w,t,f,s
	private int dayOfMonthOffset = 0;

	// Days of Week
	private String[] daysOfWeek = new String[] { "Sunday", "Monday", "Tuesday",
			"Wednesday", "Thursday", "Friday", "Saturday" };

	// Days of Week Abrev
	private String[] daysOfWeekAbrev = new String[] { "Sun", "Mon", "Tue",
			"Wed", "Thu", "Fri", "Sat" };

	// Num the first day of month falls on
	private int firstDayNumOfMonth = 0;

	// Days in month
	private int lastDayInMonth = 0;

	// Months
	private String[] monthNames = new String[] { "January", "February",
			"March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	
	// Calendar panels
	private VerticalPanel pCalendarWidget = new VerticalPanel();
	private HorizontalPanel pCalendarMenu = new HorizontalPanel();
	private HorizontalPanel pCalendarContent = new HorizontalPanel();
	
	// menu
	private HorizontalPanel pMenuMonth = new HorizontalPanel();
	private HorizontalPanel pMenuYear = new HorizontalPanel();
	private HorizontalPanel pMenuToday = new HorizontalPanel();
	private HorizontalPanel pMonthName = new HorizontalPanel();
	private HorizontalPanel pYear = new HorizontalPanel();
	
	// buttons
	private PushButton bNextMonth = new PushButton(">");
	private PushButton bNextYear = new PushButton(">");
	private PushButton bPrevMonth = new PushButton("<");
	private PushButton bPrevYear = new PushButton("<");
	private PushButton bToday = new PushButton("Today");

	// Selected Day
	private int selectedDay = 0; //TODO - replace this with selDay
	
	// Menu Selected
	private int selMonth = 0;
	private int selYear = 0;
	private int selDay = 0;

	// Today - keeps today in mem
	private int todayDay = 0;
	private int todayMonth = 0;
	private int todayYear = 0;
		
	// calendar grid - used for referencing with dayGrid storage
	private Grid calGrid = null;
	
	// store the DayNum grid location
	private String[] dayGrid = null;
	
	// last grid that was selected by click
	private String dayGridLastSelected = null;
	
	private boolean clearDay;
	
	/**
	 * constructor - make widget
	 */
	public CalendarWidget() {

		//Title
		HorizontalPanel hpTitle = new HorizontalPanel();
		hpTitle.add(new HTML("&nbsp;<b>Habit Progress</b>&nbsp;"));
		
		VerticalPanel pTitle = new VerticalPanel();
		pTitle.add(hpTitle);
		
		pCalendarMenu.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		
		// menu
		pCalendarMenu.add(pMenuMonth);
		pCalendarMenu.add(bToday);
		pCalendarMenu.add(pMenuYear);
		

		VerticalPanel pSpacer1 = new VerticalPanel();
		VerticalPanel pSpacer2 = new VerticalPanel();
		
		// Combine Calendar Panels
		pCalendarWidget.add(pTitle);
		pCalendarWidget.add(pSpacer1);
		pCalendarWidget.add(pCalendarMenu);
		pCalendarWidget.add(pCalendarContent);
		pCalendarWidget.add(pSpacer2);
		
		//init the widget
		initWidget(pCalendarWidget);
		
		//Style
		pSpacer1.setStyleName("spacer");
		pSpacer2.setStyleName("spacer");
		pCalendarMenu.setStyleName("padLR");
		pCalendarContent.addStyleName("padLR");
		pTitle.addStyleName("title");
		pCalendarMenu.addStyleName("cal-pCalendarMenu");
		pCalendarContent.addStyleName("cal-pCalendarContent");
		pCalendarWidget.setStyleName("cal-pCalendarWidget");
		
		//Hard Style
		pTitle.setWidth("100%");
		pTitle.setCellHorizontalAlignment(hpTitle, HorizontalPanel.ALIGN_CENTER);
		
		//Observers
		bPrevMonth.addClickListener(this);
		bNextMonth.addClickListener(this);
		bPrevYear.addClickListener(this);
		bNextYear.addClickListener(this);
		bToday.addClickListener(this);
		
		
	}
	
	/**
	 * process sign in
	 * 
	 * set today
	 * draws menu
	 */
	public void drawCalendarItems() {
		//get todays date
		getToday();

		//set the first month to this month
		setTodayDateSelected();

		//draw menu
		drawMenu();
		
		drawCalendar();
	}
	
	/**
	 * calculate common calendar stuff
	 */
	private void calculateCalendar() {
		// How many days in month
		getLastDayInMonth();
		
		// first day offset
		getFirstDayNumOfMonth();
	}

	/**
	 * goto prev month
	 */
	private void changePrevMonth() {
		this.resetGridSelection();
		// if jan to dec
		if (selMonth == 0) {
			selYear = selYear - 1;
			selMonth = 11;
			drawYear();
		} else {
			selMonth = selMonth - 1;
		}

		drawMonth();//change month name
		drawCalendar();//redraw calendar
	}
	
	/**
	 * goto next month
	 */
	private void changeNextMonth() {
		this.resetGridSelection();
		// if dec to jan
		if (selMonth == 11) {
			selYear = selYear + 1;
			selMonth = 0;
			drawYear();
		} else {
			selMonth = selMonth + 1;
		}
		drawMonth();//change month name
		drawCalendar(); //redraw calendar
	}

	/**
	 * goto prev year
	 */
	private void changePrevYear() {
		this.resetGridSelection();
		selYear = selYear - 1;
		drawYear(); //change year displayed
		drawCalendar();//redraw calendar
	}

	/**
	 * goto next year
	 */
	private void changeNextYear() {
		this.resetGridSelection();
		selYear = selYear + 1;
		drawYear(); //change year displayed
		drawCalendar(); //redraw calendar
	}

	/**
	 * Change Day Selected 
	 * 
	 * only reload calendar if the  month has changed 
	 */
	private void changeToToday() {
				
		if (this.selMonth != this.todayMonth || this.selYear != this.todayYear) {
			setTodayDateSelected();
			drawCalendar();
		} else {
			setSelectedDay(todayDay);
			setDaySelectedInCalGrid(todayDay);
		}
	
		drawMonth();
		drawYear();
	}

	/**
	 * draw calendar with data
	 */
	public void drawCalendar() {
		
		// draw calendar
		drawCalendarGrid();
		
		// load calendar data
		//getCalendarDataForMonth(selYear, selMonth);
	}
	
	/**
	 * draw calendar data for specific day, saved or deleted
	 */
	public void drawCalendarDataForDay() {
		
		// get calendar labels for 
		//getCalendarDataForDay(this.selYear, this.selMonth, this.selDay);
	}
	
	/**
	 * draw calendar grid
	 */
	private void drawCalendarGrid() {
		pCalendarContent.clear(); //start over
		
		//calculate calendar days
		calculateCalendar();

		// consider first 7 cells names and 0 in day count
		int CountCells = 0 - 6; 
		int CountWeekDays = 0;
		int countDay = 0;
		int LastDay = lastDayInMonth + firstDayNumOfMonth + 1;
		dayGrid = new String[LastDay]; //store day grid location
		
		// grid size
		int rowsTotal = 6;
		int columnsTotal = 7; 
		if (firstDayNumOfMonth > 4 && lastDayInMonth > 30) {
			rowsTotal = 7; 
		}
		calGrid = new Grid(rowsTotal, columnsTotal);

		//rows
		for (int row = 0; row < rowsTotal; ++row) {

			//columns
			for (int column = 0; column < columnsTotal; ++column) {

				// Weekdays
				if (row == 0) { //FIRST ROW
					String WeekDayAbrev = getWeekDayName(CountWeekDays);
					calGrid.setText(row, column, WeekDayAbrev);
					calGrid.getCellFormatter().addStyleName(row, column,"cal-WeekDays");
					CountWeekDays++;
				}

				// Days
				if (row > 0 && CountCells > firstDayNumOfMonth && CountCells < LastDay) { // AFTER FIRST ROW
					
					//store grid location 0,1 - for access later
					dayGrid[countDay] = Integer.toString(row) + "," + Integer.toString(column);
					
					//make widget for panel
					Day dayWidget = new Day();
					
					//add a widget that observers for clicks
					dayWidget.setDayNumber(countDay + 1);
					
					//add day widget to Day in Grid
					calGrid.setWidget(row, column, dayWidget);
					
					// Can't edit future
					boolean canEdit = getCanEditDay(countDay+1);

					//Day Style
					String style = "";
					if ((countDay + 1 == todayDay && selMonth == todayMonth && selYear == todayYear)) { // Today
						style = "cal-Today";
					} else if (canEdit == false) { // can edit style
						style = "cal-Day-Cantedit";
					} else { // Can't edit style
						style = "cal-Day";
					}
					 
					//cell style
					calGrid.getCellFormatter().setWidth(row, column, "45px");
					calGrid.getCellFormatter().setHeight(row, column, "45px");
					calGrid.getCellFormatter().setStyleName(row, column, style);
					
					
					countDay++;
				}
				//count what cell we are on
				CountCells++;
			}
		}

		// Display
		pCalendarContent.add(calGrid);
		
		// Observe Grid for clicks
		calGrid.addTableListener(this);
		
		// Style
		calGrid.addStyleName("cal-Grid");
	}

	/**
	 * can we edit this day?
	 * 
	 * @param day
	 * @return
	 */
	private boolean getCanEditDay(int day) {
		
		boolean canEdit = false;
		
		if ( (day <= todayDay) &&  (selMonth <= todayMonth) && (selYear <= todayYear) || 
				(selMonth < todayMonth) && (selYear <= todayYear) ) {  
			canEdit = true;
		}
		
		return canEdit;
	}
	
	/**
	 * draw calendar menu
	 */
	private void drawMenu() {
		
		//Month Panel
		pMenuMonth.clear();
		pMenuMonth.add(bPrevMonth);
		pMenuMonth.add(new HTML("&nbsp;"));
		pMenuMonth.add(pMonthName);
		pMenuMonth.add(new HTML("&nbsp;"));
		pMenuMonth.add(bNextMonth);

		//Year Panel
		pMenuYear.clear();
		pMenuYear.add(bPrevYear);
		pMenuYear.add(new HTML("&nbsp;"));
		pMenuYear.add(pYear);
		pMenuYear.add(new HTML("&nbsp;")); 	
		pMenuYear.add(bNextYear);

		drawMonth();
		drawYear();
		
		pMenuMonth.setCellVerticalAlignment(pMonthName, VerticalPanel.ALIGN_MIDDLE);
		pMenuYear.setCellVerticalAlignment(pYear, VerticalPanel.ALIGN_MIDDLE);
	}

	/**
	 * draw month in menu
	 */
	private void drawMonth() {
		String MonthName = getMonthName();
		pMonthName.clear();
		pMonthName.add(new Label(MonthName));
	}

	/**
	 * draw year in menu
	 */
	private void drawYear() {
		String Year = Integer.toString(selYear);
		pYear.clear();
		pYear.add(new Label(Year));
	}

	/**
	 * get where the first day of the month starts
	 */
	private void getFirstDayNumOfMonth() {
		Date date = new Date(selYear - 1900, selMonth, 1);
		firstDayNumOfMonth = date.getDay();
	}

	/**
	 * get how many days in month
	 */
	private void getLastDayInMonth() {

		switch (selMonth) {
		case 1:
			if ((selYear % 4 == 0 && selYear % 100 != 0) || selYear % 400 == 0) {
				lastDayInMonth = 29;
			} else {
				lastDayInMonth = 28;
			}
			break;
		case 3:
			lastDayInMonth = 30;
			break;
		case 5:
			lastDayInMonth = 30;
			break;
		case 8:
			lastDayInMonth = 30;
			break;
		case 10:
			lastDayInMonth = 30;
			break;
		default:
			lastDayInMonth = 31;
			break;
		}
	}

	private String getMonthName() {
		return monthNames[selMonth];
	}

	public int getSelectedDay() {
		return this.selectedDay;
	}

	/**
	 * get today 
	 * 
	 * sets todayYear
	 * sets todayMonth
	 * sets tdoayDate
	 */
	private void getToday() {
		Date date = new Date();
		todayYear = date.getYear() + 1900;
		todayMonth = date.getMonth();
		todayDay = date.getDate();
	}

	/**
	 * get day of week name for grid/calendar
	 * 
	 * @param WeekDay
	 * @return
	 */
	private String getWeekDayName(int WeekDay) {
		return daysOfWeekAbrev[WeekDay];
	}

	/**
	 * set the default month to load
	 */
	private void setTodayDateSelected() {
		selMonth = todayMonth;
		selYear = todayYear;
		selDay = todayDay;
	}
	
	/**
	 * set day selected in calendar grid
	 * 
	 * @param Day
	 */
	private void setDaySelectedInCalGrid(int Day) {
		
		this.selDay = Day;
		
		String location = dayGrid[Day-1];
		String[] grid = getGrid(location);
		
		if (dayGridLastSelected != null) {
			String[] lastgrid = getGrid(dayGridLastSelected);
			
			//Is the lastgrid location today
			String lStyle;
			String slastgrid = Integer.parseInt(lastgrid[0]) + "," + Integer.parseInt(lastgrid[1]); //5,4
			
			if (selMonth == todayMonth && selYear == todayYear) {
				if (dayGrid[todayDay-1].equals(slastgrid)) {
					lStyle = "cal-Today";
				} else {
					lStyle = "cal-Day";
				}
			} else {
				lStyle = "cal-Day";
			}
  
			setGridStyle(lastgrid[0], lastgrid[1], lStyle);
		}
		
		setGridStyle(grid[0], grid[1], "cal-DaySelected");
		
		//save the last selected
		dayGridLastSelected = dayGrid[Day-1];
	}
	
	/**
	 * set grid style to cell in grid
	 * 
	 * @param row
	 * @param col
	 * @param Style
	 */
	private void setGridStyle(String row, String col, String Style) {
		int irow = Integer.parseInt(row);
		int icol = Integer.parseInt(col);
		calGrid.getCellFormatter().setStyleName(irow, icol, Style);
		//RootPanel.get().add(new Label("grid: " + row + " " + col + " s: " + Style));
	}
	
	/**
	 * find grid location from string 0,1
	 * 
	 * @param location
	 * @return grid[]
	 */
	private String[] getGrid(String location) {
		String[] grid = location.split(",");
		return grid;
	}
	
	/**
	 * reset the grid calendar selection system
	 */
	private void resetGridSelection() {
		dayGrid = null;
		dayGridLastSelected = null;
	}

	/**
	 * draw the labels into entire month
	 * 
	 * @param cal
	 */
	private void drawCalendarDataForMonth(CalendarData[] calData) {

		//loop through data and insert into the grid
		for (int i=0; i < calData.length; i++) {
			drawLabelToGrid(calData[i]);
		}
	}
	
	/**
	 *  draw the lables into a Day
	 *  
	 * @param calData
	 */
	private void drawCalendarDataForDay(CalendarData[] calData) {
		
		if (calData == null) {
			return;
		}
		
		clearDay = true;
		
		//loop through data and insert into the grid
		for (int i=0; i < calData.length; i++) {
			
			if (i==1) {
				clearDay = false;
			}
			
			drawLabelToGrid(calData[i]);
		}
	
		//clear the day if return is 0
		if (calData.length == 0) {
			CalendarData calData2 = new CalendarData();
			calData2.day = selDay;
			calData2.month = selMonth;
			calData2.year = selYear;
			drawLabelToGrid(calData2);
		}
	}
	
	/**
	 * draw labels on calendar grid
	 * 
	 * @param day
	 * @param labels
	 */
	private void drawLabelToGrid(CalendarData calData) {
		
		int day = calData.day;
		String sLabel = calData.label;

		String location = dayGrid[day-1];
		String[] grid = getGrid(location);
		int row = Integer.parseInt(grid[0]);
		int column = Integer.parseInt(grid[1]);
		Day dw = (Day) calGrid.getWidget(row, column);
		
		if (clearDay == true) {
			dw.clearLabels();
		}
		dw.drawLabel(sLabel);
	}
	
	/**
	 * get Unix Time stamp for day (at 12:00:00 24hr)
	 * 
	 * @return int unixTimeStamp 
	 */
	public int getSelectedTimeStampForDay() {
		Date date = new Date();
		date.setYear(this.selYear-1900);
		date.setMonth(this.selMonth);
		date.setDate(this.selectedDay);
		date.setHours(12);
		date.setMinutes(0);
		date.setSeconds(0);
		long lTimeStamp = date.getTime();
		int iTimeStamp = (int) (lTimeStamp * .001);
		return iTimeStamp;
	}
	
	/**
	 * set selected day and let the parent know of change
	 * 
	 * @param selectedDay
	 */
	private void setSelectedDay(int selectedDay) {
		this.selectedDay = selectedDay;

		if (changeListeners != null) {
			changeListeners.fireChange(this);
		}
	}

	/**
	 * Observing
	 */
	public void onClick(Widget sender) {

		if (sender == bPrevMonth) {
			changePrevMonth();
		}

		if (sender == bNextMonth) {
			changeNextMonth();
		}

		if (sender == bPrevYear) {
			changePrevYear();
		}

		if (sender == bNextYear) {
			changeNextYear();
		}

		if (sender == bToday) {
			changeToToday();
		}
	}

	/**
	 * Observe Calendar Widget
	 * 
	 * @param listener
	 */
	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null)
			changeListeners = new ChangeListenerCollection();
		changeListeners.add(listener);
	}
	
	protected void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null)
			changeListeners.remove(listener);
	}

	/**
	 * observe day
	 */
	public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
		
		String location = Integer.toString(row) + "," + Integer.toString(cell);
		int index = Arrays.binarySearch(dayGrid, location);
		
		// no matching day found for this cell
		if (index == -1) { 
			return;
		}
		
		int dayClicked = index + 1;
		
		boolean canEdit = getCanEditDay(dayClicked);
		
		if (canEdit == true) {
			setSelectedDay(dayClicked);
			setDaySelectedInCalGrid(dayClicked);
		}
	
		// debug
		//System.out.println("click: Row:" + row + " Col:" + cell + " index:" + dayClicked);
	}
	

	
}
