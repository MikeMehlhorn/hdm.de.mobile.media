package de.hdm.reservierung.server.db;

import de.hdm.reservierung.server.DBConnection;
import de.hdm.reservierung.shared.Room;
import de.hdm.reservierung.shared.RoomBooking;
import de.hdm.reservierung.shared.TimeSlot;
import de.hdm.reservierung.shared.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RoombookingMapper {

	private static RoombookingMapper raumbuchungsMapper = null;

	protected RoombookingMapper() {
	}

	public static RoombookingMapper raumbuchungsMapper() {
		if (raumbuchungsMapper == null) {
			raumbuchungsMapper = new RoombookingMapper();
		}
		return raumbuchungsMapper;
	}

	public ArrayList<RoomBooking> getAllRaumbuchungenByUser(User user) {

		Connection con = DBConnection.getInstance();
		ArrayList<RoomBooking> raumbuchungList = new ArrayList<RoomBooking>();
		try {
			String sql = "SELECT * FROM raumbuchung, raum where raumbuchung.user_kuerzel = '"
					+ user.getKuerzel()
					+ "' and raumbuchung.startzeit > CURDATE()  AND raum.id = raumbuchung.raum_id ORDER BY raumbuchung.startzeit DESC";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				RoomBooking roomBooking = new RoomBooking();
				Room room = new Room();

				room.setCapacity(rs.getInt("anzahl"));
				room.setId(rs.getString("raum_id"));
				roomBooking.setRoom(room);

				roomBooking.setUser(user);

				roomBooking.setId(rs.getInt("id"));
				roomBooking.setStartzeit(rs.getTimestamp("startzeit"));
				roomBooking.setEndzeit(rs.getTimestamp("endzeit"));
				roomBooking.setDate(rs.getString("tagesdatum"));
				raumbuchungList.add(roomBooking);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return raumbuchungList;
	}

	public RoomBooking getRaumbuchungById(Integer bookingId) {
		Connection con = DBConnection.getInstance();
		RoomBooking roomBooking = new RoomBooking();
		try {

			String sql = "SELECT * FROM raumbuchung, raum, zeitslot, user where raumbuchung.id = '"
					+ bookingId
					+ "' AND raum.id = raumbuchung.raum_id AND user.kuerzel = raumbuchung.user_kuerzel AND zeitslot.id = raumbuchung.zeit_id";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {

				Room room = new Room();
				User user = new User();
				TimeSlot timeSlot = new TimeSlot();

				room.setCapacity(rs.getInt("anzahl"));
				room.setId(rs.getString("raum_id"));
				roomBooking.setRoom(room);

				user.setVorname(rs.getString("vorname"));
				user.setNachname(rs.getString("nachname"));
				user.setKuerzel(rs.getString("kuerzel"));
				user.setMail(rs.getString("mail"));
				user.setRolle(rs.getString("rolle"));
				roomBooking.setUser(user);

				roomBooking.setId(rs.getInt("id"));
				roomBooking.setStartzeit(rs.getTimestamp("startzeit"));
				roomBooking.setEndzeit(rs.getTimestamp("endzeit"));
				roomBooking.setDate(rs.getString("tagesdatum"));

				timeSlot.setTimeId(rs.getInt("id"));
				timeSlot.setValue(rs.getString("value"));
				roomBooking.setTimeSlot(timeSlot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roomBooking;
	}

	public ArrayList<RoomBooking> getAllRaumbuchungen() {
		Connection con = DBConnection.getInstance();
		ArrayList<RoomBooking> raumbuchungList = new ArrayList<RoomBooking>();
		try {

			String sql = "SELECT * FROM raumbuchung, raum, zeitslot, user where raum.id = raumbuchung.raum_id AND user.kuerzel = raumbuchung.user_kuerzel AND zeitslot.id = raumbuchung.zeit_id";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				RoomBooking roomBooking = new RoomBooking();
				Room room = new Room();
				User user = new User();
				TimeSlot timeSlot = new TimeSlot();

				room.setCapacity(rs.getInt("anzahl"));
				room.setId(rs.getString("raum_id"));
				roomBooking.setRoom(room);

				user.setVorname(rs.getString("vorname"));
				user.setNachname(rs.getString("nachname"));
				user.setKuerzel(rs.getString("kuerzel"));
				user.setMail(rs.getString("mail"));
				user.setRolle(rs.getString("rolle"));
				roomBooking.setUser(user);

				roomBooking.setId(rs.getInt("id"));
				roomBooking.setStartzeit(rs.getTimestamp("startzeit"));
				roomBooking.setEndzeit(rs.getTimestamp("endzeit"));
				roomBooking.setDate(rs.getString("tagesdatum"));

				timeSlot.setTimeId(rs.getInt("id"));
				timeSlot.setValue(rs.getString("value"));
				roomBooking.setTimeSlot(timeSlot);
				raumbuchungList.add(roomBooking);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return raumbuchungList;
	}

	public void deleteRaumbuchung(Integer bookingId) {
		Connection con = DBConnection.getInstance();
		try {

			String sql = "DELETE FROM raumbuchung where id ='" + bookingId
					+ "'";
			Statement state = con.createStatement();
			state.executeUpdate(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<RoomBooking> insertRaumbuchungForStudent(
			ArrayList<RoomBooking> roomBookingListPar, User user) {
		// Kopie der Originalliste
		ArrayList<RoomBooking> allRoomBookings = new ArrayList<RoomBooking>(
				roomBookingListPar);

		// Vor dem buchen wird geprüft, ob die Reservierung tatsächlich
		// verfügbar ist oder ob in der ZWischenzeit jemand
		// anderst gebuch hat

		// Prüfen, ob Reservierung noch verfügbar für Dozent oder Student
		ArrayList<RoomBooking> roomBookingList = checkBookingForStudent(
				roomBookingListPar, user);

		for (int i = 0; i < roomBookingList.size(); i++) {
			Connection con = DBConnection.getInstance();
			try {
				String sql = "INSERT INTO raumbuchung (id, raum_id, user_kuerzel, startzeit, endzeit, zeit_id, tagesdatum) VALUES (NULL,'"
						+ roomBookingList.get(i).getRoom().getId()
						+ "','"
						+ roomBookingList.get(i).getUser().getKuerzel()
						+ "','"
						+ roomBookingList.get(i).getStartzeit()
						+ "','"
						+ roomBookingList.get(i).getEndzeit()
						+ "','"
						+ roomBookingList.get(i).getTimeSlot().getTimeId()
						+ "','" + roomBookingList.get(i).getDate() + "');";
				Statement state = con.createStatement();
				state.executeUpdate(sql);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return setReturn(allRoomBookings, roomBookingList);

	}

	public ArrayList<RoomBooking> insertRaumbuchungForDocent(
			ArrayList<RoomBooking> roomBookingListPar, User user) {
		// Kopie der Originalliste
		ArrayList<RoomBooking> allRoomBookings = new ArrayList<RoomBooking>(
				roomBookingListPar);

		// Vor dem buchen wird geprüft, ob die Reservierung tatsächlich
		// verfügbar ist oder ob in der ZWischenzeit jemand
		// anderst gebuch hat

		// Prüfen, ob Reservierung noch verfügbar für Dozent oder Student
		ArrayList<RoomBooking> roomBookingList = checkBookingForDocent(
				roomBookingListPar, user);

		for (int i = 0; i < roomBookingList.size(); i++) {
			Connection con = DBConnection.getInstance();
			try {
				String sql = "INSERT INTO raumbuchung (id, raum_id, user_kuerzel, startzeit, endzeit, zeit_id, tagesdatum) VALUES (NULL,'"
						+ roomBookingList.get(i).getRoom().getId()
						+ "','"
						+ roomBookingList.get(i).getUser().getKuerzel()
						+ "','"
						+ roomBookingList.get(i).getStartzeit()
						+ "','"
						+ roomBookingList.get(i).getEndzeit()
						+ "','"
						+ roomBookingList.get(i).getTimeSlot().getTimeId()
						+ "','" + roomBookingList.get(i).getDate() + "');";
				Statement state = con.createStatement();
				state.executeUpdate(sql);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return setReturn(allRoomBookings, roomBookingList);

	}

	public ArrayList<TimeSlot> getAvailableRaumbuchungenForStudent(
			String datePar, String roomPar, User userPar,
			ArrayList<TimeSlot> timeSlotList) {
		// Anhand von einem Datum und Raum (String Value, String raum), werden
		// die freien Zeitslots ermittelt
		// Rolle == Stundent --> Keine Überbuchung möglich
		Connection con = DBConnection.getInstance();
		ArrayList<Integer> IntegerList = new ArrayList<Integer>();
		try {

			String sql = "SELECT raumbuchung.zeit_id FROM raumbuchung where tagesdatum = '"
					+ datePar + "' AND raum_id = '" + roomPar + "'";

			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				Integer time_id = rs.getInt("zeit_id");
				IntegerList.add(time_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (IntegerList.isEmpty()) {
			return timeSlotList;
		} else {
			for (int i = 0; i < IntegerList.size(); i++) {
				for (int j = 0; j < timeSlotList.size(); j++) {
					if (timeSlotList.get(j).getTimeId()
							.compareTo(IntegerList.get(i)) == 0) {
						timeSlotList.remove(j);

					}
				}
			}
		}

		return timeSlotList;

	}

	public ArrayList<TimeSlot> getAvailableRaumbuchungenForDocent(String date,
			String room, User userPar, ArrayList<TimeSlot> timeSlotListPar) {
		// Anhand von einem Datum und Raum (String Value, String raum), werden
		// die freien Zeitslots ermittelt
		// Rolle == Dozent --> Überbuchung möglich bei Studentenbuchungen
		Connection con = DBConnection.getInstance();
		ArrayList<TimeSlot> timeSLotList = new ArrayList<TimeSlot>();
		try {

			String sql = "SELECT * FROM raumbuchung, user, zeitslot where tagesdatum = '"
					+ date
					+ "' AND raumbuchung.raum_id = '"
					+ room
					+ "' AND zeitslot.id = raumbuchung.zeit_id AND user.kuerzel = raumbuchung.user_kuerzel";

			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				TimeSlot timeSlot = new TimeSlot();
				User user = new User();

				user.setVorname(rs.getString("vorname"));
				user.setNachname(rs.getString("nachname"));
				user.setKuerzel(rs.getString("kuerzel"));
				user.setMail(rs.getString("mail"));
				user.setRolle(rs.getString("rolle"));
				timeSlot.setUser(user);

				timeSlot.setTimeId(rs.getInt("zeit_id"));
				timeSlot.setValue(rs.getString("value"));

				timeSLotList.add(timeSlot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (timeSLotList.isEmpty()) {
			return timeSlotListPar;
		} else {
			for (int i = 0; i < timeSLotList.size(); i++) {
				for (int j = 0; j < timeSlotListPar.size(); j++) {
					if (timeSlotListPar.get(j).getTimeId()
							.compareTo(timeSLotList.get(i).getTimeId()) == 0
							& timeSLotList.get(i).getUser().getRolle()
									.compareTo("Dozent") == 0) {
						timeSlotListPar.remove(j);
					}
				}
			}
		}

		for (int i = 0; i < timeSLotList.size(); i++) {
			for (int j = 0; j < timeSlotListPar.size(); j++) {
				if (timeSlotListPar.get(j).getTimeId()
						.compareTo(timeSLotList.get(i).getTimeId()) == 0
						& timeSLotList.get(i).getUser().getRolle()
								.compareTo("Student") == 0) {
					timeSlotListPar.get(j).setUser(
							timeSLotList.get(i).getUser());
				}
			}
		}

		return timeSlotListPar;

	}

	public ArrayList<RoomBooking> checkBookingForStudent(
			ArrayList<RoomBooking> roomBookingListPar, User user) {

		Connection con = DBConnection.getInstance();
		ArrayList<RoomBooking> roomBookingList = new ArrayList<RoomBooking>(
				roomBookingListPar);
		ArrayList<Integer> deleteList = new ArrayList<Integer>();
		try {
			for (int j = 0; j < roomBookingListPar.size(); j++) {
				String sql = "SELECT * FROM raumbuchung where raum_id = '"
						+ roomBookingListPar.get(j).getRoom().getId()
						+ "' AND zeit_id = '"
						+ roomBookingListPar.get(j).getTimeSlot().getTimeId()
						+ "' AND tagesdatum ='"
						+ roomBookingListPar.get(j).getDate() + "'";
				Statement state = con.createStatement();
				ResultSet rs = state.executeQuery(sql);

				while (rs.next()) {
					deleteList.add(roomBookingListPar.get(j).getTimeSlot()
							.getTimeId());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < deleteList.size(); i++) {
			for (int j = 0; j < roomBookingList.size(); j++) {
				if (roomBookingList.get(j).getTimeSlot().getTimeId()
						.compareTo(deleteList.get(i)) == 0) {
					roomBookingList.remove(j);
				}
			}
		}
		return roomBookingList;
	}

	public ArrayList<RoomBooking> checkBookingForDocent(
			ArrayList<RoomBooking> roomBookingListPar, User user) {

		Connection con = DBConnection.getInstance();
		ArrayList<RoomBooking> roomBookingList = new ArrayList<RoomBooking>(
				roomBookingListPar);
		ArrayList<Integer> deleteList = new ArrayList<Integer>();
		try {
			for (int j = 0; j < roomBookingListPar.size(); j++) {
				String sql = "SELECT * FROM raumbuchung, user where raum_id = '"
						+ roomBookingListPar.get(j).getRoom().getId()
						+ "' AND zeit_id = '"
						+ roomBookingListPar.get(j).getTimeSlot().getTimeId()
						+ "' AND tagesdatum ='"
						+ roomBookingListPar.get(j).getDate()
						+ "' AND user.kuerzel = raumbuchung.user_kuerzel AND user.rolle = 'Dozent'";
				Statement state = con.createStatement();
				ResultSet rs = state.executeQuery(sql);

				while (rs.next()) {
					deleteList.add(roomBookingListPar.get(j).getTimeSlot()
							.getTimeId());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < deleteList.size(); i++) {
			for (int j = 0; j < roomBookingList.size(); j++) {
				if (roomBookingList.get(j).getTimeSlot().getTimeId()
						.compareTo(deleteList.get(i)) == 0) {
					roomBookingList.remove(j);
				}
			}
		}
		return roomBookingList;
	}

	public ArrayList<RoomBooking> getCancelBookings(
			ArrayList<RoomBooking> roomBookingListPar) {

		Connection con = DBConnection.getInstance();

		ArrayList<RoomBooking> cancelBookings = new ArrayList<RoomBooking>();
		try {
			for (int j = 0; j < roomBookingListPar.size(); j++) {
				String sql = "SELECT * FROM raumbuchung, raum, zeitslot, user where raum_id = '"
						+ roomBookingListPar.get(j).getRoom().getId()
						+ "' AND zeit_id = '"
						+ roomBookingListPar.get(j).getTimeSlot().getTimeId()
						+ "' AND tagesdatum ='"
						+ roomBookingListPar.get(j).getDate()
						+ "' AND user.kuerzel = raumbuchung.user_kuerzel AND user.rolle = 'Student' AND raumbuchung.raum_id = raum.id AND raumbuchung.zeit_id = zeitslot.id ";
				Statement state = con.createStatement();
				ResultSet rs = state.executeQuery(sql);

				while (rs.next()) {
					RoomBooking roomBooking = new RoomBooking();
					Room room = new Room();
					User user = new User();
					TimeSlot timeSlot = new TimeSlot();

					room.setCapacity(rs.getInt("anzahl"));
					room.setId(rs.getString("raum_id"));
					roomBooking.setRoom(room);

					user.setVorname(rs.getString("vorname"));
					user.setNachname(rs.getString("nachname"));
					user.setKuerzel(rs.getString("kuerzel"));
					user.setMail(rs.getString("mail"));
					user.setRolle(rs.getString("rolle"));
					roomBooking.setUser(user);

					roomBooking.setId(rs.getInt("id"));
					roomBooking.setStartzeit(rs.getTimestamp("startzeit"));
					roomBooking.setEndzeit(rs.getTimestamp("endzeit"));
					roomBooking.setDate(rs.getString("tagesdatum"));

					timeSlot.setTimeId(rs.getInt("id"));
					timeSlot.setValue(rs.getString("value"));
					roomBooking.setTimeSlot(timeSlot);

					cancelBookings.add(roomBooking);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cancelBookings;
	}

	private ArrayList<RoomBooking> setReturn(
			ArrayList<RoomBooking> allRoomBookings,
			ArrayList<RoomBooking> isBookedList) {
		
		//Alle Buchungen auf Status Belegt setzen 
		for (int i = 0; i < allRoomBookings.size(); i++) {
			allRoomBookings.get(i).setId(999);
		}
		
		//Alle Buchungen bereits verbucht, Status auf Belegt setzen
		if (isBookedList.isEmpty()) {
			for (int i = 0; i < allRoomBookings.size(); i++) {
				allRoomBookings.get(i).setId(999);
			}
			return allRoomBookings;
		}

		if (allRoomBookings.size() != isBookedList.size()) {

			for (RoomBooking rb1 : allRoomBookings) {
				for (RoomBooking rb2 : isBookedList) {
					if (rb1.getTimeSlot().getTimeId()
							.equals(rb2.getTimeSlot().getTimeId())      ) {
						rb1.setId(777);
					}

				}
			}

			// Ausgabe für den Anwender!
			return allRoomBookings;

		} else {
			for (int i = 0; i < allRoomBookings.size(); i++) {
				allRoomBookings.get(i).setId(777);
			}
			return allRoomBookings;
		}
	}

}
