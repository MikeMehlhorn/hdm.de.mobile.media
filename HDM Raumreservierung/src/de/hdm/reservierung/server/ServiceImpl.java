package de.hdm.reservierung.server;

import java.util.ArrayList;

import de.hdm.reservierung.client.Service;
import de.hdm.reservierung.server.db.RoomMapper;
import de.hdm.reservierung.server.db.RoombookingMapper;
import de.hdm.reservierung.server.db.TimeSlotMapper;
import de.hdm.reservierung.server.db.UserMapper;
import de.hdm.reservierung.shared.RoomBooking;
import de.hdm.reservierung.shared.Room;
import de.hdm.reservierung.shared.TimeSlot;
import de.hdm.reservierung.shared.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
public class ServiceImpl extends RemoteServiceServlet implements Service {

	private static final long serialVersionUID = 1L;
	private UserMapper userMapper = null;
	private RoombookingMapper roombookingMapper = null;
	private RoomMapper roomMapper = null;
	private TimeSlotMapper timeSlotMapper = null;

	@Override
	public void init() throws IllegalArgumentException {
		/*
		 * Ganz wesentlich ist, dass die BankAdministration einen vollst�ndigen
		 * Satz von Mappern besitzt, mit deren Hilfe sie dann mit der Datenbank
		 * kommunizieren kann.
		 */
		this.roomMapper = RoomMapper.roomMapper();
		this.roombookingMapper = RoombookingMapper.raumbuchungsMapper();
		this.userMapper = UserMapper.raumbuchungsMapper();
		this.timeSlotMapper = TimeSlotMapper.timeSlotMapper();
	}

	@Override
	public ArrayList<RoomBooking> getAllRaumbuchungenFromUser(User user) {
		return roombookingMapper.getAllRaumbuchungenByUser(user);
	}

	@Override
	public void deleteRaumbuchung(ArrayList<Integer> deleteIdList) {
		roombookingMapper.deleteRaumbuchung(deleteIdList);
	}

	@Override
	public User checkLogin(String username, String password) {
		return userMapper.checkLogin(username, password);
	}

	@Override
	public ArrayList<RoomBooking> getAllRaumbuchungen() {
		return roombookingMapper.getAllRaumbuchungen();
	}

	@Override
	public ArrayList<TimeSlot> getAvailableRaumbuchungenByDateForStudent(
			String date, String room, User user) {
		ArrayList<TimeSlot> timeSlot = getTimeSlots();
		return roombookingMapper.getAvailableRaumbuchungenByDateForStudent(
				date, room, user, timeSlot);
	}

	@Override
	public User getUser(String kuerzel) {
		return userMapper.getUser(kuerzel);
	}

	@Override
	public void cancelMail(RoomBooking roomBooking) {
		User user = getUser(roomBooking.getUser_kuerzel());
		SendMail.cancelBooking(roomBooking, user);
	}

	@Override
	public ArrayList<TimeSlot> getAvailableRaumbuchungenByDateForDocent(
			String date, String room, User user) {
		ArrayList<TimeSlot> timeSlot = getTimeSlots();
		return roombookingMapper.getAvailableRaumbuchungenByDateForDocent(date,
				room, user, timeSlot);
	}

	@Override
	public ArrayList<Room> getAllRooms() {
		return roomMapper.getAllRooms();
	}

	@Override
	public ArrayList<TimeSlot> getTimeSlots() {
		return timeSlotMapper.getTimeSlots();
	}

	@Override
	public void bookingMail(String room, User user,
			ArrayList<RoomBooking> roomBookingList) {
		SendMail.bookingMail(room, user, roomBookingList);

	}

	@Override
	public void deleteAndInformUser(ArrayList<TimeSlot> timeSlotList) {
		for (int i = 0; i < timeSlotList.size(); i++) {
			RoomBooking roomBooking = getRoomBooking(timeSlotList.get(i)
					.getBookingId());
			User user = getUser(roomBooking.getUser_kuerzel());
			ArrayList<Integer> deleteList = new ArrayList<Integer>();
			deleteList.add(timeSlotList.get(i).getBookingId());
			roombookingMapper.deleteRaumbuchung(deleteList);
			SendMail.overBooking(timeSlotList, roomBooking, user);
		}

	}

	@Override
	public RoomBooking getRoomBooking(Integer bookingId) {
		return roombookingMapper.getRoomBooking(bookingId);
	}

	@Override
	public ArrayList<RoomBooking> insertRoomBooking(
			ArrayList<TimeSlot> overBooking,
			ArrayList<RoomBooking> roomBookingPar, String room, User user) {
		
		for (int i = 0; i < overBooking.size(); i++) {
			RoomBooking roomBooking = getRoomBooking(overBooking.get(i)
					.getBookingId());
			user = getUser(roomBooking.getUser_kuerzel());
			ArrayList<Integer> deleteList = new ArrayList<Integer>();
			deleteList.add(overBooking.get(i).getBookingId());
			roombookingMapper.deleteRaumbuchung(deleteList);
			SendMail.overBooking(overBooking, roomBooking, user);
		}
		
		
		ArrayList<RoomBooking> roomBooking = roombookingMapper.insertRaumbuchung(roomBookingPar);
		bookingMail(room, user, roomBooking);
		return roomBooking;
	}

}
