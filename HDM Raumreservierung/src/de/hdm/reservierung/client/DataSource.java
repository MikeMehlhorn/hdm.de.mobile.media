package de.hdm.reservierung.client;

import java.util.ArrayList;
import java.util.List;

import de.hdm.reservierung.shared.RoomBooking;

public class DataSource {
	
	  private final List<RoomBooking> roomBooking;
	  private List<String> header;

	  public DataSource(List<RoomBooking> roomBooking) {
	    header = new ArrayList<String>();
	    header.add("Raum");
	    header.add("Zeitraum");
	    header.add("Storno");
	    this.roomBooking = roomBooking;
	  }

	  public List<RoomBooking> getRaumbuchung() {
	    return roomBooking;
	  }

	  public List<String> getTableHeader() {
	    return header;
	  }

}
