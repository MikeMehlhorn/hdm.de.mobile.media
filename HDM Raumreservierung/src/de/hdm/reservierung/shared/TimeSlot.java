package de.hdm.reservierung.shared;



public class TimeSlot extends BusinessObjects{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  Integer timeId;
	private String value;
	private String user_kuerzel;
	private String user_role;
	private Integer bookingId;
	/**
	 * @return the id
	 */
	public Integer getTimeId() {
		return timeId;
	}
	/**
	 * @param id the id to set
	 */
	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the user_kuerzel
	 */
	public String getUser_kuerzel() {
		return user_kuerzel;
	}
	/**
	 * @param user_kuerzel the user_kuerzel to set
	 */
	public void setUser_kuerzel(String user_kuerzel) {
		this.user_kuerzel = user_kuerzel;
	}
	/**
	 * @return the user_role
	 */
	public String getUser_role() {
		return user_role;
	}
	/**
	 * @param user_role the user_role to set
	 */
	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	/**
	 * @return the bookingId
	 */
	public Integer getBookingId() {
		return bookingId;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

}
