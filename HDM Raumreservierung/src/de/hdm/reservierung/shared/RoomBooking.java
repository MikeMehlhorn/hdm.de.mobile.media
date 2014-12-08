package de.hdm.reservierung.shared;

import java.sql.Timestamp;
import java.util.ArrayList;

import de.hdm.reservierung.client.custom.DateFormat;

public class RoomBooking extends BusinessObjects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Timestamp startzeit, endzeit;
	private String date;
	private User user;
	private TimeSlot timeSlot;
	private Room room;

	

	/**
	 * @return the startzeit
	 */
	public Timestamp getStartzeit() {
		return startzeit;
	}

	/**
	 * @param startzeit
	 *            the startzeit to set
	 */
	public void setStartzeit(Timestamp startzeit) {
		this.startzeit = startzeit;
	}

	/**
	 * @return the endzeit
	 */
	public Timestamp getEndzeit() {
		return endzeit;
	}

	/**
	 * @param endzeit
	 *            the endzeit to set
	 */
	public void setEndzeit(Timestamp endzeit) {
		this.endzeit = endzeit;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}


	
	@Override
	public String toString(){
		
		String a = DateFormat.FormatDateDDMMYYYY2(this.startzeit.toString());

		ArrayList<String> x = DateFormat
				.SeperateDateTime2String(this.startzeit.toString());
		ArrayList<String> y = DateFormat
				.SeperateDateTime2String(this.endzeit.toString());
		
		return a + " ab " + x.get(1) + " - " + y.get(1) + " in Raum " + room.getId();
		
	}
	
	public String forCalendar(){
		ArrayList<String> y = DateFormat
				.SeperateDateTime2String(this.endzeit.toString());
		
		return y.get(1) + " | " + this.room.getId();
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the timeSlot
	 */
	public TimeSlot getTimeSlot() {
		return timeSlot;
	}

	/**
	 * @param timeSlot the timeSlot to set
	 */
	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

}
