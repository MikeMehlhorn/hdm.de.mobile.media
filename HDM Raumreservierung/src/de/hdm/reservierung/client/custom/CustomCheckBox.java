package de.hdm.reservierung.client.custom;

import org.gwtbootstrap3.client.ui.CheckBox;

public class CustomCheckBox extends CheckBox {
	
private Integer timeId;
	
	public CustomCheckBox(String value, Integer timeId){
		super();
		this.setText(value);
		this.setVisible(true);
		this.timeId= timeId;
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


	
	


	
	
	

}
