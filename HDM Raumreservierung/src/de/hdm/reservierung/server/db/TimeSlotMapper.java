package de.hdm.reservierung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.reservierung.server.DBConnection;
import de.hdm.reservierung.shared.TimeSlot;

public class TimeSlotMapper {

	
	private static TimeSlotMapper timeSlotMapper = null;
	
	protected TimeSlotMapper() {
	}
	
	public static TimeSlotMapper timeSlotMapper() {
		if (timeSlotMapper == null) {
			timeSlotMapper = new TimeSlotMapper();
		}
		return timeSlotMapper;
	}
	
	public ArrayList<TimeSlot> getTimeSlots(){
		Connection con = DBConnection.getInstance();
		ArrayList<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
		try {

			String sql = "SELECT * FROM zeitslot";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while (rs.next()) {
				TimeSlot timeSlot = new TimeSlot();
				timeSlot.setTimeId(rs.getInt("id"));
				timeSlot.setValue(rs.getString("value"));
				timeSlotList.add(timeSlot);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return timeSlotList;
	}
}
