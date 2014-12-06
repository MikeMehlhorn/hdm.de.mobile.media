package de.hdm.reservierung.client.custom;

import org.gwtbootstrap3.extras.datetimepicker.client.ui.DateTimePicker;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.DateTimePickerLanguage;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.constants.DateTimePickerView;

public class CustomDateTimePicker extends DateTimePicker {

	public CustomDateTimePicker() {
		this.setValue(new java.util.Date());
		this.setMinView(DateTimePickerView.MONTH);
		this.setLanguage(DateTimePickerLanguage.DE);
		this.setAutoClose(true);
		this.setFormat("dd-mm-yyyy");
		//this.setDaysOfWeekDisabled(DateTimePickerDayOfWeek.SUNDAY,
			//	DateTimePickerDayOfWeek.SATURDAY);
		this.setHighlightToday(true);
		//this.setStartDate(new java.util.Date());
	}

}
