package de.hdm.reservierung.client;

import java.util.ArrayList;
import java.util.Date;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.InputGroup;
import org.gwtbootstrap3.client.ui.InputGroupAddon;
import org.gwtbootstrap3.client.ui.constants.IconPosition;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.events.ChangeDateEvent;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.events.ChangeDateHandler;
import org.gwtbootstrap3.extras.fullcalendar.client.ui.AgendaOptions;
import org.gwtbootstrap3.extras.fullcalendar.client.ui.CalendarConfig;
import org.gwtbootstrap3.extras.fullcalendar.client.ui.Event;
import org.gwtbootstrap3.extras.fullcalendar.client.ui.FullCalendar;
import org.gwtbootstrap3.extras.fullcalendar.client.ui.Language;
import org.gwtbootstrap3.extras.fullcalendar.client.ui.ViewOption;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

import de.hdm.reservierung.client.custom.CustomDateTimePicker;
import de.hdm.reservierung.client.custom.Template;
import de.hdm.reservierung.shared.RoomBooking;

public class Calendar extends Template {

	private ServiceAsync service = ClientsideSettings.getService();

	private FlowPanel flowPanel = new FlowPanel();

	private FullCalendar fc;
	private final CustomDateTimePicker datum = new CustomDateTimePicker();

	private InputGroup inputGroup = new InputGroup();
	private InputGroupAddon inputGroupAddon = new InputGroupAddon();
	
	private static final String red = "#FF0000";
	private static final String green = "#00FF00";
	private static final String blue = "#0000FF";
	private static final String yellow = "#FFFF00";
	private static final String lightblue = "#00FFFF";
	private static final String pink = "#FF00FF";
	

	public Calendar() {

		this.setHeaderText("");

		inputGroupAddon.setIcon(IconType.CALENDAR);
		inputGroupAddon.setIconPosition(IconPosition.LEFT);
		inputGroupAddon.setText("Datum");
		inputGroupAddon.setIconLight(true);

		inputGroup.add(inputGroupAddon);
		inputGroup.add(datum);

		Button month = new Button("Monatsansicht");
		//Button week = new Button("Wochenansicht");

		// inputGroup.add(month);
		this.add(new HTML("</br>"));
		flowPanel.add(month);

		//flowPanel.add(week);
		// month.getElement().getStyle().setMarginBottom(10, Unit.PX);

		month.getElement().getStyle().setMarginRight(10, Unit.PX);
		this.add(flowPanel);
		this.add(new HTML("</br>"));
		this.add(inputGroup);

		month.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fc.setView(ViewOption.month);

			}
		});

		/*week.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fc.setView(ViewOption.agendaWeek);

			}
		});*/

		CalendarConfig config = new CalendarConfig();
		AgendaOptions agendaOptions = new AgendaOptions();
		agendaOptions.setSlotDuration("00:30:00");
		agendaOptions.setMinTime("08:00:00");
		agendaOptions.setMaxTime("17:00:00");

		agendaOptions.setAllDayText("Uhrzeit");
		agendaOptions.setSlotEventOverlap(true);

		config.setTimezone("local");
		config.setLangauge(Language.German);
		config.setSelectable(false);

		// config.setAgendaOptions(agendaOptions);

		datum.addChangeDateHandler(new ChangeDateHandler() {

			@Override
			public void onChangeDate(ChangeDateEvent evt) {
				fc.goToDate(datum.getValue());
				fc.setView(ViewOption.agendaDay);

			}
		});

		fc = new FullCalendar("some_unique_id", ViewOption.agendaDay, config,
				false);

		fc.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				service
						.getAllRaumbuchungen(new AsyncCallback<ArrayList<RoomBooking>>() {
							@Override
							public void onSuccess(ArrayList<RoomBooking> result) {
								addEvents(result);
							}

							@Override
							public void onFailure(Throwable caught) {

							}
						});
			}

		});

		this.add(new HTML("</br>"));
		this.add(fc);

	}

	protected void addEvents(ArrayList<RoomBooking> result) {
		for (int i = 0; i < result.size(); i++) {
			Event calEvent = new Event("1" + i, result.get(i).forCalendar());

			calEvent.setStartEditable(false);
			calEvent.setDurationEditable(false);
			calEvent.setEditable(false);

			Date s = new Date();
			s = result.get(i).getStartzeit();

			calEvent.setStart(s);

			// Date d = new Date(s.getTime());
			// d.setHours(d.getHours() + 1);
			
			switch (result.get(i).getRoom().getId()) {
			case "I210":
				calEvent.setColor(red);
				break;
				
			case "I213":
				calEvent.setColor(green);
				break;
				
			case "I215":
				calEvent.setColor(blue);
				break;
				
			case "I216":
				calEvent.setColor(yellow);
				break;
				
			case "I217":
				calEvent.setColor(lightblue);
				break;
				
			case "I218":
				calEvent.setColor(pink);
				break;

			default:
				break;
			}
			

			calEvent.setEnd(result.get(i).getEndzeit());

			fc.addEvent(calEvent);

		}

	}

}
