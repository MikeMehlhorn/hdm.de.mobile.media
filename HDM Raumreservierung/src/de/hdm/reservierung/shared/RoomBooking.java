package de.hdm.reservierung.shared;

import java.sql.Timestamp;
import java.util.ArrayList;

import de.hdm.reservierung.client.custom.DateFormat;

public class RoomBooking extends BusinessObjects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id, time_Id;
	private String user_kuerzel, raum_id;
	private Timestamp startzeit, endzeit;
	private String date;

	/**
	 * @return the person_id
	 */
	public String getUser_kuerzel() {
		return user_kuerzel;
	}

	/**
	 * @param person_id
	 *            the person_id to set
	 */
	public void setUser_kuerzel(String user_id) {
		this.user_kuerzel = user_id;
	}

	/**
	 * @return the raum_id
	 */
	public String getRaum_id() {
		return raum_id;
	}

	/**
	 * @param raum_id
	 *            the raum_id to set
	 */
	public void setRaum_id(String raum_id) {
		this.raum_id = raum_id;
	}

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

	/**
	 * @return the time_Id
	 */
	public Integer getTime_Id() {
		return time_Id;
	}

	/**
	 * @param time_Id
	 *            the time_Id to set
	 */
	public void setTime_Id(Integer time_Id) {
		this.time_Id = time_Id;
	}
	
	@Override
	public String toString(){
		
		String a = DateFormat.FormatDateDDMMYYYY2(this.startzeit.toString());

		ArrayList<String> x = DateFormat
				.SeperateDateTime2String(this.startzeit.toString());
		ArrayList<String> y = DateFormat
				.SeperateDateTime2String(this.endzeit.toString());
		
		return "Am " + a + " ab " + x.get(1) + " - " + y.get(1) + " in Raum " + raum_id ;
		
	}

}
