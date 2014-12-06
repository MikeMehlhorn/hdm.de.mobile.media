package de.hdm.reservierung.client.custom;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;

public class CustomButton extends Button {
	
	private Integer pK;

	public CustomButton(Integer pK){
		this.setType(ButtonType.DANGER);
		this.setIcon(IconType.TIMES);
		this.setSize(ButtonSize.SMALL);
		this.pK = pK;
	}

	/**
	 * @return the pK
	 */
	public Integer getpK() {
		return pK;
	}

	/**
	 * @param pK the pK to set
	 */
	public void setpK(Integer pK) {
		this.pK = pK;
	}

	
}
