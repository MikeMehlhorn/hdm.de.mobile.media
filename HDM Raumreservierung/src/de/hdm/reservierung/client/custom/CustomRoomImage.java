package de.hdm.reservierung.client.custom;

import org.gwtbootstrap3.client.ui.constants.ImageType;

public class CustomRoomImage extends org.gwtbootstrap3.client.ui.Image {

	private String id;
	private Integer capacity;

	public CustomRoomImage(String urlValue, Integer capacity, String id) {

		super.onLoad();
		this.setResponsive(true);
		this.setType(ImageType.THUMBNAIL);
		this.setUrl(urlValue);
		this.capacity = capacity;
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the capacity
	 */
	public Integer getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

}
