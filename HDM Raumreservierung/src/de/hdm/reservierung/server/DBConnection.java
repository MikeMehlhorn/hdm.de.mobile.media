package de.hdm.reservierung.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	
	private static Connection conn = null;

	// Hostname
	private final String dbHost = "127.0.0.1";

	// Port -- Standard: 3306
	private final String dbPort = "3306";

	// Datenbankname
	private final String database = "hdm_raumreservierung";

	// Datenbankuser
	private final String dbUser = "root";

	// Datenbankpasswort
	private final String dbPassword = "";
	
	public DBConnection(){
		try {

			// Datenbanktreiber f�r ODBC Schnittstellen laden.
			// F�r verschiedene ODBC-Datenbanken muss dieser Treiber
			// nur einmal geladen werden.
			Class.forName("com.mysql.jdbc.Driver");

			// Verbindung zur ODBC-Datenbank 'sakila' herstellen.
			// Es wird die JDBC-ODBC-Br�cke verwendet.
			conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
					+ dbPort + "/" + database + "?" + "user=" + dbUser + "&"
					+ "password=" + dbPassword);
		} catch (ClassNotFoundException e) {
			System.out.println("Treiber nicht gefunden");
		} catch (SQLException e) {
			System.out.println("Connect nicht moeglich");
		}
	}
	
	public static Connection getInstance() {
		if (conn == null)
			new DBConnection();
		return conn;
	}
	
	/**
	 * Schreibt die Namensliste in die Konsole
	 */
	public String printNameList() {
		conn = getInstance();
		String Titel = "";
		if (conn != null) {
			// Anfrage-Statement erzeugen.
			Statement query;

			try {
				query = conn.createStatement();

				// Ergebnistabelle erzeugen und abholen.
				String sql = "SELECT * FROM raumbuchung";
				ResultSet result = query.executeQuery(sql);

				// Ergebniss�tze durchfahren.
				while (result.next()) {
					Titel = result.getString("raum_id"); // Alternativ: // result.getString(1)
					Titel = result.getString("user_id"); // Alternativ:
					Titel = result.getString("buchungsdatum"); // Alternativ:
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Titel;
	}
	

	


}
