package de.hdm.reservierung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.reservierung.server.DBConnection;
import de.hdm.reservierung.shared.Room;

public class RoomMapper {
	
	private static RoomMapper roomMapper = null;

	protected RoomMapper() {
	}
	
	public static RoomMapper roomMapper() {
		if (roomMapper == null) {
			roomMapper = new RoomMapper();
		}
		return roomMapper;
	}
	
	public ArrayList<Room> getAllRooms(){
		Connection con = DBConnection.getInstance();
		ArrayList<Room> roomList = new ArrayList<Room>();
		try {

			String sql = "SELECT * FROM raum";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);

			while (rs.next()) {
				Room room = new Room();
				room.setId(rs.getString("id"));
				room.setCapacity(rs.getInt("anzahl"));
				roomList.add(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roomList;
	}

}
