package de.hdm.reservierung.client.custom;

import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.constants.ImageType;

public class CustomImage extends Image {
	
public CustomImage(String urlValue) {
		
		super.onLoad();
		this.setType(ImageType.ROUNDED);
		this.setUrl(urlValue);

	}

}
