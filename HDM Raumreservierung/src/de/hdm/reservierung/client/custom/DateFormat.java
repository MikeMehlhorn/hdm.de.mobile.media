package de.hdm.reservierung.client.custom;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateFormat {

	public static ArrayList<String> SeperateDateTime2String(String value) {
		ArrayList<String> date = new ArrayList<String>();
		date.clear();
		if (value.toString() != "") {

			// DateTimeFormat d = new DateTimeFormat();

			// DateTimeFormat dateFormat = new DateTimeFormat();

			// String text = dateFormat.format(value);

			String onlyDate = value.substring(0, 10);
			onlyDate.replace('-', ':');
			String onylStartTime = value.substring(11, 16);
			date.add(onlyDate);
			date.add(onylStartTime);
			return date;
		}
		return date;
	}

	public static String FormatDate4SQLFilter(String value) {
		if (!value.isEmpty()) {
			String day, month, year = "";
			day = value.substring(0, 2);
			month = value.substring(3, 5);
			year = value.substring(6, 10);

			return year + "-" + month + "-" + day;
		}

		return "";

	}

	public static String FormatDateDDMMYYYY(String value) {
		if (!value.isEmpty()) {
			String day, month, year = "";
			day = value.substring(0, 2);
			month = value.substring(3, 5);
			year = value.substring(6, 10);

			return day + "." + month + "." + year;

		}

		return "";
	}

	public static String FormatDateDDMMYYYY2(String value) {
		// FORMAT INPUT YYYY-MM-DD
		if (!value.isEmpty()) {
			String day, month, year = "";
			month = value.substring(5, 7);
			day = value.substring(8, 10);
			year = value.substring(0, 4);

			return day + "." + month + "." + year;

		}

		return "";
	}


	public static Timestamp Convert2Timestamp(String value, String value2,
			Integer i) {
		String day = "", month = "", year = "", hour = "", minute = "";
		String parseTime = "";
		Timestamp timestamp = null;

		DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");

		// SimpleDateFormat dateFormat = new SimpleDateFormat(
		// "yyyy-MM-dd hh:mm:ss:SSS");

		if (!value.isEmpty()) {
			// 27-11-2014
			month = value.substring(3, 5);
			day = value.substring(0, 2);
			year = value.substring(6, 10);
			value2 = value2.replaceAll(" ", "");
			value2 = value2.replaceAll(":", "");
			value2 = value2.replaceAll("-", "");

			switch (i) {
			case 1:
				// [08:00] - 08:30

				hour = value2.substring(0, 2);
				minute = value2.substring(2, 4);
				break;

			case 2:
				// 08:00 - [08:30]
				hour = value2.substring(4, 6);
				minute = value2.substring(6, 8);
				break;

			default:
				break;
			}

		}

		try {

			parseTime = year + "-" + month + "-" + day + " " + hour + ":"
					+ minute + ":00";
			// Date parsedTimeStamp = dateFormat.parse(parseTime);
			Date parsedTimeStamp = fmt.parse(parseTime);

			timestamp = new Timestamp(parsedTimeStamp.getTime());
		} catch (Exception e) {
			// TODO: handle exception
		}

		return timestamp;
	}
}
