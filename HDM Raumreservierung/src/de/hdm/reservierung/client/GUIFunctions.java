package de.hdm.reservierung.client;

import com.google.gwt.user.client.ui.RootPanel;

public final class GUIFunctions {
	public static void clearRootPanel(String div) {
		RootPanel.get(div).clear();
	}
	public static void clearRootPanel(){
		RootPanel.get().clear();
	}
}
