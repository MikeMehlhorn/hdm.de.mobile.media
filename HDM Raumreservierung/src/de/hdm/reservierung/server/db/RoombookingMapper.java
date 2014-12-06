package de.hdm.reservierung.server.db;

import de.hdm.reservierung.server.DBConnection;
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

			String sql = "SELECT * FROM raumbuchung where raumbuchung.user_kuerzel = '"
					+ user.getKuerzel()
					+ "' and raumbuchung.startzeit > CURDATE() ORDER BY raumbuchung.startzeit DESC";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				RoomBooking roomBooking = new RoomBooking();
				roomBooking.setId(rs.getInt("id"));
				roomBooking.setRaum_id(rs.getString("raum_id"));
				roomBooking.setUser_kuerzel(rs.getString("user_kuerzel"));
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

	public ArrayList<RoomBooking> getAllRaumbuchungen() {
		Connection con = DBConnection.getInstance();
		ArrayList<RoomBooking> raumbuchungList = new ArrayList<RoomBooking>();
		try {

			String sql = "SELECT * FROM raumbuchung";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				RoomBooking roomBooking = new RoomBooking();
				roomBooking.setId(rs.getInt("id"));
				roomBooking.setRaum_id(rs.getString("raum_id"));
				roomBooking.setUser_kuerzel(rs.getString("user_kuerzel"));
				roomBooking.setStartzeit(rs.getTimestamp("startzeit"));
				roomBooking.setEndzeit(rs.getTimestamp("endzeit"));
				roomBooking.setTime_Id(rs.getInt("zeit_id"));
				raumbuchungList.add(roomBooking);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return raumbuchungList;
	}

	public void deleteRaumbuchung(ArrayList<Integer> deleteIdList) {
		Connection con = DBConnection.getInstance();
		try {
			for (int i = 0; i < deleteIdList.size(); i++) {
				String sql = "DELETE FROM raumbuchung where id ="
						+ deleteIdList.get(i);
				Statement state = con.createStatement();
				state.executeUpdate(sql);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<RoomBooking> insertRaumbuchung(
			ArrayList<RoomBooking> roomBookingListPar) {
		// Originalliste
		ArrayList<RoomBooking> allRoomBookings = new ArrayList<RoomBooking>(
				roomBookingListPar);

		// Vor dem buchen wird geprüft, ob die Reservierung tatsächlich
		// verfügbar ist oder ob in der ZWischenzeit jemand
		// anderst gebuch hat

		// Bereits verbucht!
		ArrayList<RoomBooking> roomBookingList = checkBooking(roomBookingListPar);

		for (int i = 0; i < roomBookingList.size(); i++) {
			Connection con = DBConnection.getInstance();
			try {
				String sql = "INSERT INTO raumbuchung (id, raum_id, user_kuerzel, startzeit, endzeit, zeit_id, tagesdatum) VALUES (NULL,'"
						+ roomBookingList.get(i).getRaum_id()
						+ "','"
						+ roomBookingList.get(i).getUser_kuerzel()
						+ "','"
						+ roomBookingList.get(i).getStartzeit()
						+ "','"
						+ roomBookingList.get(i).getEndzeit()
						+ "','"
						+ roomBookingList.get(i).getTime_Id()
						+ "','"
						+ roomBookingList.get(i).getDate() + "');";
				Statement state = con.createStatement();
				state.executeUpdate(sql);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		// Alle Buchungen bereits belegt!
		if (roomBookingList.isEmpty()) {
			for (int i = 0; i < allRoomBookings.size(); i++) {
				allRoomBookings.get(i).setId(999);
			}
			return allRoomBookings;
		}

		if (allRoomBookings.size() != roomBookingList.size()) {
			ArrayList<Integer> deleteList = new ArrayList<Integer>();
			deleteList.clear();
			for (int i = 0; i < allRoomBookings.size(); i++) {
				for (int j = 0; j < roomBookingList.size(); j++) {
					if (allRoomBookings.get(i).getTime_Id()
							.compareTo(roomBookingList.get(j).getTime_Id()) == 0) {
						allRoomBookings.get(i).setId(999);
					} else {
						allRoomBookings.get(i).setId(777);
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

	public ArrayList<TimeSlot> getAvailableRaumbuchungenByDateForStudent(
			String date, String room, User user,
			ArrayList<TimeSlot> timeSlotList) {
		// Anhand von einem Datum und Raum (String Value, String raum), werden
		// die freien Zeitslots ermittelt
		// Rolle == Stundent --> Keine Überbuchung möglich
		Connection con = DBConnection.getInstance();
		ArrayList<Integer> IntegerList = new ArrayList<Integer>();
		try {

			String sql = "SELECT raumbuchung.zeit_id FROM raumbuchung where tagesdatum = '"
					+ date + "' AND raum_id = '" + room + "'";

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

	public ArrayList<TimeSlot> getAvailableRaumbuchungenByDateForDocent(
			String date, String room, User user,
			ArrayList<TimeSlot> timeSlotList) {
		// Anhand von einem Datum und Raum (String Value, String raum), werden
		// die freien Zeitslots ermittelt
		// Rolle == Dozent --> Überbuchung möglich bei Studentenbuchungen
		Connection con = DBConnection.getInstance();
		ArrayList<TimeSlot> timeSLotList = new ArrayList<TimeSlot>();
		try {

			String sql = "SELECT raumbuchung.id, raumbuchung.user_kuerzel, raumbuchung.zeit_id, zeitslot.value, user.rolle FROM raumbuchung, user, zeitslot where tagesdatum = '"
					+ date
					+ "' AND raumbuchung.raum_id = '"
					+ room
					+ "' AND zeitslot.id = raumbuchung.zeit_id AND user.kuerzel = raumbuchung.user_kuerzel";

			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setTimeId(rs.getInt("zeit_id"));
				timeSlot.setUser_kuerzel(rs.getString("user_kuerzel"));
				timeSlot.setValue(rs.getString("value"));
				timeSlot.setUser_role(rs.getString("rolle"));
				timeSlot.setBookingId(rs.getInt("id"));
				timeSLotList.add(timeSlot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (timeSLotList.isEmpty()) {
			return timeSlotList;
		} else {
			for (int i = 0; i < timeSLotList.size(); i++) {
				for (int j = 0; j < timeSlotList.size(); j++) {
					if (timeSlotList.get(j).getTimeId()
							.compareTo(timeSLotList.get(i).getTimeId()) == 0
							& timeSLotList.get(i).getUser_role()
									.compareTo("Dozent") == 0) {
						timeSlotList.remove(j);
					}
					// if (timeSlotList.get(j).getTimeId()
					// .compareTo(timeSLotList.get(i).getTimeId()) == 0
					// & timeSLotList.get(i).getUser_role()
					// .compareTo("Student") == 0) {
					// timeSlotList.get(j).setUser_kuerzel(timeSLotList.get(j).getUser_kuerzel());
					// timeSlotList.get(j).setBookingId(timeSLotList.get(j).getBookingId());
				}
			}
		}

		for (int i = 0; i < timeSLotList.size(); i++) {
			for (int j = 0; j < timeSlotList.size(); j++) {
				if (timeSlotList.get(j).getTimeId()
						.compareTo(timeSLotList.get(i).getTimeId()) == 0
						& timeSLotList.get(i).getUser_role()
								.compareTo("Student") == 0) {
					timeSlotList.get(j).setUser_kuerzel(
							timeSLotList.get(i).getUser_kuerzel());
					timeSlotList.get(j).setBookingId(
							timeSLotList.get(i).getBookingId());
				}
			}
		}

		return timeSlotList;

	}

	public RoomBooking getRoomBooking(Integer bookingId) {
		Connection con = DBConnection.getInstance();
		RoomBooking roomBooking = new RoomBooking();
		try {

			String sql = "SELECT * FROM raumbuchung where id =" + bookingId;
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				roomBooking.setId(rs.getInt("id"));
				roomBooking.setRaum_id(rs.getString("raum_id"));
				roomBooking.setUser_kuerzel(rs.getString("user_kuerzel"));
				roomBooking.setStartzeit(rs.getTimestamp("startzeit"));
				roomBooking.setEndzeit(rs.getTimestamp("endzeit"));
				roomBooking.setTime_Id(rs.getInt("zeit_id"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomBooking;

	}

	public ArrayList<RoomBooking> checkBooking(
			ArrayList<RoomBooking> roomBookingListPar) {
		Connection con = DBConnection.getInstance();
		ArrayList<RoomBooking> roomBookingList = new ArrayList<RoomBooking>(
				roomBookingListPar);
		ArrayList<Integer> deleteList = new ArrayList<Integer>();
		try {
			for (int j = 0; j < roomBookingListPar.size(); j++) {
				String sql = "SELECT * FROM raumbuchung where raum_id = '"
						+ roomBookingListPar.get(j).getRaum_id()
						+ "' AND zeit_id = '"
						+ roomBookingListPar.get(j).getTime_Id()
						+ "' AND tagesdatum ='"
						+ roomBookingListPar.get(j).getDate() + "'";
				Statement state = con.createStatement();
				ResultSet rs = state.executeQuery(sql);

				while (rs.next()) {
					deleteList.add(roomBookingListPar.get(j).getTime_Id());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < deleteList.size(); i++) {
			for (int j = 0; j < roomBookingList.size(); j++) {
				if (roomBookingList.get(j).getTime_Id()
						.compareTo(deleteList.get(i)) == 0) {
					roomBookingList.remove(j);
				}
			}
		}
		return roomBookingList;
	}
}
