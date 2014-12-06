package de.hdm.reservierung.client;


import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.reservierung.shared.RoomBooking;
import de.hdm.reservierung.shared.Room;
import de.hdm.reservierung.shared.TimeSlot;
import de.hdm.reservierung.shared.User;

/**
 * The async counterpart of <code>Service</code>.
 */
public interface ServiceAsync {
	void checkLogin(String username, String password,
			AsyncCallback<User> callback);

	void getAllRaumbuchungenFromUser(User user,
			AsyncCallback<ArrayList<RoomBooking>> callback);

	void deleteRaumbuchung(ArrayList<Integer> deleteIdList,
			AsyncCallback<Void> callback);

	void getAllRaumbuchungen(AsyncCallback<ArrayList<RoomBooking>> callback);

	void getAvailableRaumbuchungenByDateForStudent(String date, String room,
			User user, AsyncCallback<ArrayList<TimeSlot>> callback);

	void getUser(String kuerzel, AsyncCallback<User> callback);

	void cancelMail(RoomBooking roomBooking,
			AsyncCallback<Void> callback);

	void getAvailableRaumbuchungenByDateForDocent(String date, String room,
			User user, AsyncCallback<ArrayList<TimeSlot>> callback);

	void getAllRooms(AsyncCallback<ArrayList<Room>> callback);

	void getTimeSlots(AsyncCallback<ArrayList<TimeSlot>> callback);

	//void insertRaumbuchung(ArrayList<RoomBooking> roomBookingList,
		//	AsyncCallback<Void> callback);

	void bookingMail(String room, User user,
			ArrayList<RoomBooking> roomBookingList, AsyncCallback<Void> callback);

	void deleteAndInformUser(ArrayList<TimeSlot> timeSlotList,
			AsyncCallback<Void> callback);

	void getRoomBooking(Integer bookingId, AsyncCallback<RoomBooking> callback);

	void insertRoomBooking(ArrayList<TimeSlot> overBooking,
			ArrayList<RoomBooking> roomBooking, String room, User user,
			AsyncCallback<ArrayList<RoomBooking>> callback);

}
