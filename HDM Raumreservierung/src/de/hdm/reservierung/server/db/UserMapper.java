package de.hdm.reservierung.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.reservierung.server.DBConnection;
import de.hdm.reservierung.shared.User;

public class UserMapper {

	private static UserMapper userMapper = null;

	protected UserMapper() {
	}

	public static UserMapper raumbuchungsMapper() {
		if (userMapper == null) {
			userMapper = new UserMapper();
		}
		return userMapper;
	}

	public User checkLogin(String username, String password) {
		Connection con = DBConnection.getInstance();
		User user = new User();
		try {

			String sql = "SELECT * FROM user where kuerzel = '" + username
					+ "' and password = '" + password + "'";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			if (rs.next()) {
				user.setVorname(rs.getString("vorname"));
				user.setNachname(rs.getString("nachname"));
				user.setKuerzel(rs.getString("kuerzel"));
				user.setMail(rs.getString("mail"));
				user.setRolle(rs.getString("rolle"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;

	}

	public User getUser(String value) {
		User user = new User();
		Connection con = DBConnection.getInstance();
		try {

			String sql = "SELECT * FROM user where kuerzel = '" + value + "'";
			Statement state = con.createStatement();
			ResultSet rs = state.executeQuery(sql);
			if (rs.next()) {
				user.setVorname(rs.getString("vorname"));
				user.setNachname(rs.getString("nachname"));
				user.setKuerzel(rs.getString("kuerzel"));
				user.setMail(rs.getString("mail"));
				user.setRolle(rs.getString("rolle"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}
}
