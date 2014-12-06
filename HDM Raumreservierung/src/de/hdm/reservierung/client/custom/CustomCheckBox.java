package de.hdm.reservierung.client.custom;

import org.gwtbootstrap3.client.ui.CheckBox;

public class CustomCheckBox extends CheckBox {
	
private Integer timeId;
private Integer bookingId;
	
	public CustomCheckBox(String value, Integer timeId, Integer bookingId){
		super();
		this.setText(value);
		this.setVisible(true);
		this.timeId= timeId;
		this.bookingId = bookingId;
	}

	/**
	 * @return the cId
	 */
	public Integer getTimeId() {
		return timeId;
	}

	/**
	 * @param cId the cId to set
	 */
	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
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
