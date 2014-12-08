package de.hdm.reservierung.client.custom;

import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.constants.Alignment;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;

import com.google.gwt.user.client.ui.HTML;

public class Template extends Container {

	@Override
	protected void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		this.setFluid(false);
	}



	public void setHeaderText(String value) {
		this.add(new HTML("</br>"));
		this.add(new HTML("</br>"));
		Heading heading = new Heading(HeadingSize.H3, value);
		heading.setAlignment(Alignment.CENTER);
		this.add(heading);
	}
}
