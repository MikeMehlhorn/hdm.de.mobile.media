package de.hdm.reservierung.client;



import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.reservierung.shared.Room;
import de.hdm.reservierung.shared.RoomBooking;
import de.hdm.reservierung.shared.TimeSlot;
import de.hdm.reservierung.shared.User;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface Service extends RemoteService {
	
	
	
	// <-- Roombooking -->
	ArrayList<RoomBooking> getAllRaumbuchungenFromUser(User user);
	ArrayList<RoomBooking> getAllRaumbuchungen();
	ArrayList<TimeSlot> getAvailableRaumbuchungenForStudent(String date,String room,User user);
	ArrayList<TimeSlot> getAvailableRaumbuchungenForDocent(String date,String room, User user);
	RoomBooking deleteRaumbuchung(Integer bookingId);
	ArrayList<RoomBooking> insertRoomBooking(ArrayList<RoomBooking> roomBooking, String room, User user);
	
	
	
	//<-- User -->
	User checkLogin(String username, String password);
	User getUser(String kuerzel);
	
	
	//<-- Room -->
	ArrayList<Room> getAllRooms();
	
	// <-- Mailing -->
	void cancelMail(RoomBooking raumbuchung);
	void bookingMail(String room, User user, ArrayList<RoomBooking> roomBookingList);
	
	// <--TimeSlot -->
	ArrayList<TimeSlot> getTimeSlots();
	
	
	
	
	
}
