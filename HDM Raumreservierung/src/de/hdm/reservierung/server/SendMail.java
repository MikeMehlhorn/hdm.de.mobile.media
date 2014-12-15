package de.hdm.reservierung.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.hdm.reservierung.client.custom.DateFormat;
import de.hdm.reservierung.shared.RoomBooking;
import de.hdm.reservierung.shared.TimeSlot;
import de.hdm.reservierung.shared.User;

public class SendMail {

	// private static RoombookingMapper raumbuchungsMapper = null;
	private static Properties props;
	private static final String username = "XXXX@gmail.com";
	private static final String password = "XXXXXX";
	private static String mailtext = "";
	private static String betreff = "";

	public static void setProperties() {
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

	}

	public static void overBooking(RoomBooking roomBooking) {
		setProperties();
		
		String a = DateFormat.FormatDateDDMMYYYY2(roomBooking.getStartzeit()
				.toString());

		ArrayList<String> x = DateFormat.SeperateDateTime2String(roomBooking
				.getStartzeit().toString());
		ArrayList<String> y = DateFormat.SeperateDateTime2String(roomBooking
				.getEndzeit().toString());
		
		mailtext = "Sehr geehrte(r) " + roomBooking.getUser().getVorname() + " "
		+ roomBooking.getUser().getNachname() + "," + "\n\n Ihre Raumreservierung wurde Storniert, da ein Professor den Raum reserviert hat."
	
				+ "Es handelt sich um folgende Raumreservierung: \n\n "
				+ "Raumnummer: " + roomBooking.getRoom().getId() + "\n Datum:" + a
				+ "\n Zeitraum: " + x.get(1) + " - " + y.get(1) + "\n\n"
				+ "Mit freundlichen Grüßen \n"
				+ "HdM-Stuttgart - Raumreserierungssystem";
		betreff = "Stornierung Ihrer Buchung";

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("hdmraumreservierung@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("hdmraumreservierung@gmail.com"));

			message.setSubject(betreff);
			message.setText(mailtext);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void cancelBooking(RoomBooking roomBooking) {
		setProperties();
		String a = DateFormat.FormatDateDDMMYYYY2(roomBooking.getStartzeit()
				.toString());

		ArrayList<String> x = DateFormat.SeperateDateTime2String(roomBooking
				.getStartzeit().toString());
		ArrayList<String> y = DateFormat.SeperateDateTime2String(roomBooking
				.getEndzeit().toString());

		mailtext = "Sehr geehrte(r) " + roomBooking.getUser().getVorname() + " "
				+ roomBooking.getUser().getNachname() + "," + "\n\n Sie haben am "
				+ new Date().toLocaleString()
				+ " folgende Raumreservierung storniert: \n\n "
				+ "Raumnummer: " + roomBooking.getRoom().getId() + "\n Datum:" + a
				+ "\n Zeitraum: " + x.get(1) + " - " + y.get(1) + "\n\n"
				+ "Mit freundlichen Grüßen \n"
				+ "HdM-Stuttgart - Raumreserierungssystem";
		betreff = "Stornierungsbestätigung";

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("hdmraumreservierung@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("hdmraumreservierung@gmail.com"));

			message.setSubject(betreff);
			message.setText(mailtext);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public static void bookingMail(String room, User user,
			ArrayList<RoomBooking> roomBookingList) {
		setProperties();
		StringBuffer sB = new StringBuffer();
		String a = "";
		for (int i = 0; i < roomBookingList.size(); i++) {
			ArrayList<String> x = DateFormat
					.SeperateDateTime2String(roomBookingList.get(i)
							.getStartzeit().toString());
			ArrayList<String> y = DateFormat
					.SeperateDateTime2String(roomBookingList.get(i)
							.getEndzeit().toString());
			a = DateFormat.FormatDateDDMMYYYY2(roomBookingList.get(i).getDate()
					.toString());
			sB.append(x.get(1) + " - " + y.get(1) + "\n");
		}
		String buchungstext = sB.toString();

		betreff = "Reservierungsbestätigung für " + room + " am " + a;
		mailtext = "Sehr geehrte(r) " + user.getVorname() + " "
				+ user.getNachname() + "," + "\n\nSie haben am "
				+ new Date().toLocaleString()
				+ " folgende Zeit(en) gebucht: \n\n" + buchungstext + "\n\n"
				+ "Mit freundlichen Grüßen \n"
				+ "HdM-Stuttgart - Raumreserierungssystem";
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("hdmraumreservierung@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("hdmraumreservierung@gmail.com"));

			message.setSubject(betreff);
			message.setText(mailtext);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
