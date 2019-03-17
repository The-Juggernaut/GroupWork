package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import application.ScreenManager;

/**
 * Event class object used for creating, updating and deleting event objects
 * both locally and within the database.
 * @author Kane
 * @version 1.0
 */
public class Event {
	
	private int ID;
	private String title;
	private String details;
	private String date;
	private int maxAttending;
	
	private static int totalEventNo = 0; //total no. of events (tracks eventID).

	//upcoming events if user, or all events if librarian.
	private static ArrayList<Event> allEvents = new ArrayList<Event>();
	//all events user has joined to.
	private static ArrayList<Event> usersEvents = new ArrayList<Event>();

	public Event(String title, String details, String date, int maxAttending) {
		this.title = title;
		this.details = details;
		this.date = date;
		this.maxAttending = maxAttending;
	}
	
	public static int getTotalEventNo() {
		return totalEventNo;
	}

	public static void setTotalEventNo(int totalEventNo) {
		Event.totalEventNo = totalEventNo;
	}
	
	/**
	 * Checks if date string is in the future or present/past.
	 * @param dateString date string to be checked.
	 * @return returns true if future date, false if otherwise.
	 */
	public static boolean checkFutureDate(String dateString) {
		try {
			LocalDateTime localDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime eventDate = LocalDateTime.parse(dateString, formatter);
			return eventDate.isAfter(localDate);
		} catch (DateTimeParseException e ) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Loads appropriate events from database. Users will see upcoming events they're
	 * currently not joined to in one table, and events they're joined to in another.
	 * Librarians will see all events.
	 * @throws SQLException
	 */
	public static void loadEventsFromDB() throws SQLException {
		
		Connection connectionToDB = DBHelper.getConnection();
        Statement stmt = connectionToDB.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM events");
        
        allEvents.clear();
        usersEvents.clear();
        
        //if normal user, get all events IDs they're joined to.
        ArrayList<Integer> usersEventIDs = null;
        if(ScreenManager.getCurrentUser() instanceof User) {
        	usersEventIDs = ((User) ScreenManager.getCurrentUser()).loadUserEvents();
        }

		while(rs.next()) {
			
			totalEventNo += 1;
		
			if(ScreenManager.getCurrentUser() instanceof User) {
				//if event is upcoming and user isn't already joined to it
				if(checkFutureDate(rs.getString(4)) && !(usersEventIDs.contains(rs.getInt(1)))) {
					allEvents.add(new Event(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
					allEvents.get(allEvents.size()-1).setID(rs.getInt(1));
				//if user is already joined to event
				} else if (usersEventIDs.contains(rs.getInt(1))) {
					usersEvents.add(new Event(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
					usersEvents.get(usersEvents.size()-1).setID(rs.getInt(1));
				}
			} else {
				//add all events if librarian
				allEvents.add(new Event(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));
				allEvents.get(allEvents.size()-1).setID(rs.getInt(1));
			}
		}
		
		
		connectionToDB.close();
		
	}
	
	/**
	 * Update Event object in database.
	 * @param event
	 */
	public static void updateEventInDB(Event event) {
		try {
			Connection connectionToDB = DBHelper.getConnection();
	        Statement stmt = connectionToDB.createStatement();
	        stmt.executeUpdate("UPDATE events SET title = '" + event.title +
	        "', details = '" + event.details + "', date = '" + event.date +
	        "', maxAllowed = " + event.maxAttending + " WHERE eID = " + event.ID);
	        connectionToDB.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds user/event relationship to database when a user joins to an event/
	 * @param username The user who has joined to an event.
	 * @param eventID The event ID.
	 */
	public static void addUserEventInDB(String username, int eventID) {
		try {
			Connection connectionToDB = DBHelper.getConnection();
	        PreparedStatement sqlStatement = connectionToDB.prepareStatement("INSERT INTO userEvents VALUES (?,?)");
	        sqlStatement.setInt(1, eventID);
	        sqlStatement.setString(2, username);
	        sqlStatement.execute();
	        connectionToDB.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addEvent(String title, String details, String date, int maxAllowed) {
		allEvents.add(new Event(title, details, date, maxAllowed));
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public int getMaxAttending() {
		return maxAttending;
	}
	
	public void setMaxAttending(int maxAttending) {
		this.maxAttending = maxAttending;
	}
	
	public static ArrayList<Event> getAllEvents() {
		return allEvents;
	}

	public static void setAllEvents(ArrayList<Event> newEvents) {
		allEvents = newEvents;
	}
	
	public static ArrayList<Event> getUserEvents() throws SQLException {
		return usersEvents;
	}

}
