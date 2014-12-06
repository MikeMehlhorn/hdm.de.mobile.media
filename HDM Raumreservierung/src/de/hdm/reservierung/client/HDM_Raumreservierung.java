package de.hdm.reservierung.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class HDM_Raumreservierung implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		if (Cookies.getCookie("hdm_raumreservierung") != null) {
			RootPanel.get("navigation").add(new Navigation(true));
		} else {
			RootPanel.get("navigation").add(new Navigation(false));
		}
	}
}
