package de.hdm.reservierung.shared;



public class TimeSlot extends BusinessObjects{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  Integer timeId;
	private String value;
	private User user;
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

}
