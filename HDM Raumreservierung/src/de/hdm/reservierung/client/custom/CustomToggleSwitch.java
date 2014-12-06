package de.hdm.reservierung.client.custom;


import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.extras.toggleswitch.client.ui.ToggleSwitch;
import org.gwtbootstrap3.extras.toggleswitch.client.ui.base.constants.ColorType;
import org.gwtbootstrap3.extras.toggleswitch.client.ui.base.constants.SizeType;

public class CustomToggleSwitch extends ToggleSwitch {

	private Integer timeId;
	
	public CustomToggleSwitch(Integer timeId) {
		super();
		this.setAnimated(true);
		this.setOffIcon(IconType.THUMBS_DOWN);
		this.setOnIcon(IconType.THUMBS_UP);
		this.setOffColor(ColorType.DANGER);
		this.setOnColor(ColorType.SUCCESS);
		this.setValue(false);
		this.setSize(SizeType.REGULAR);
		this.setTimeId(timeId);
	}

	/**
	 * @return the timeId
	 */
	public Integer getTimeId() {
		return timeId;
	}

	/**
	 * @param timeId the timeId to set
	 */
	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}

	
}
