package de.hdm.reservierung.client;

import java.util.ArrayList;
import java.util.Date;

import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.InputGroup;
import org.gwtbootstrap3.client.ui.InputGroupAddon;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.constants.AlertType;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.IconPosition;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.LabelType;
import org.gwtbootstrap3.client.ui.constants.PanelType;
import org.gwtbootstrap3.client.ui.constants.Styles;
import org.gwtbootstrap3.client.ui.html.Div;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.events.ChangeDateEvent;
import org.gwtbootstrap3.extras.datetimepicker.client.ui.base.events.ChangeDateHandler;
import org.gwtbootstrap3.extras.growl.client.ui.Growl;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlHelper;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlOptions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.reservierung.client.custom.CustomCheckBox;
import de.hdm.reservierung.client.custom.CustomDateTimePicker;
import de.hdm.reservierung.client.custom.CustomTable;
import de.hdm.reservierung.client.custom.DateFormat;
import de.hdm.reservierung.client.custom.Template;
import de.hdm.reservierung.shared.RoomBooking;
import de.hdm.reservierung.shared.TimeSlot;
import de.hdm.reservierung.shared.User;

public class RoomBookingForm extends Template {

	private Button buttonSave = new Button("Reservieren");
	private Heading heading = new Heading(HeadingSize.H2);
	private final CustomDateTimePicker datum = new CustomDateTimePicker();
	private final ServiceAsync greetingService = GWT.create(Service.class);
	Alert AlertNoBooking = new Alert();

	private FlexTable fT = new FlexTable();

	private InputGroup inputGroup = new InputGroup();
	private InputGroupAddon inputGroupAddon = new InputGroupAddon();

	private Panel panel = new Panel();
	private PanelHeader panelHeader = new PanelHeader();
	private PanelBody panelBody = new PanelBody();

	private String printDate = "";

	private Div flexTable = new Div();

	/**
	 * @param value
	 */
	public RoomBookingForm(final String room, final User user) {
		final ServiceAsync service = ClientsideSettings.getService();
		this.setHeaderText("Buchungsformular für Raum: " + room);

		fT.setStyleName("table table-striped table-condensed");

		datum.setValue(new Date());

		printDate = DateFormat.FormatDateDDMMYYYY(datum.getBaseValue()
				.toString());

		inputGroupAddon.setIcon(IconType.CALENDAR);
		inputGroupAddon.setIconPosition(IconPosition.LEFT);
		inputGroupAddon.setText("Datum");
		inputGroupAddon.setIconLight(true);

		inputGroup.add(inputGroupAddon);
		inputGroup.add(datum);

		this.add(inputGroup);
		this.add(new HTML("</br>"));

		if (user.getRolle().compareTo("Student") == 0) {
			service.getAvailableRaumbuchungenByDateForStudent(DateFormat
					.FormatDate4SQLFilter(datum.getBaseValue()).toString(),
					room, user, new loadDates());

			datum.addChangeDateHandler(new ChangeDateHandler() {
				@Override
				public void onChangeDate(ChangeDateEvent evt) {
					Window.scrollTo(0, 0);
					printDate = DateFormat.FormatDateDDMMYYYY(datum
							.getBaseValue().toString());
					greetingService.getAvailableRaumbuchungenByDateForStudent(
							DateFormat.FormatDate4SQLFilter(datum
									.getBaseValue().toString()), room, user,
							new loadDates());
				}
			});

		} else {

			greetingService.getAvailableRaumbuchungenByDateForDocent(DateFormat
					.FormatDate4SQLFilter(datum.getBaseValue().toString()),
					room, user, new loadDates());

			datum.addChangeDateHandler(new ChangeDateHandler() {
				@Override
				public void onChangeDate(ChangeDateEvent evt) {
					Window.scrollTo(0, 0);
					printDate = DateFormat.FormatDateDDMMYYYY(datum
							.getBaseValue().toString());
					greetingService.getAvailableRaumbuchungenByDateForDocent(
							DateFormat.FormatDate4SQLFilter(datum
									.getBaseValue().toString()), room, user,
							new loadDates());
				}
			});
		}

		buttonSave.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				buttonSave.setEnabled(false);
				Boolean isBooked = false;
				final ArrayList<RoomBooking> roomBookingList = new ArrayList<RoomBooking>();
				final ArrayList<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
				// final ArrayList<RoomBooking> overBookingList = new
				// ArrayList<RoomBooking>();
				for (int i = 0; i < fT.getRowCount(); i++) {
					CustomCheckBox c = (CustomCheckBox) fT.getWidget(i, 0);
					String txt = "";
					try {
						txt = fT.getText(i, 1);
					} catch (Exception e) {
						// TODO: handle exception
					}
					Boolean x = c != null && c.getValue() && !txt.isEmpty();

					if (c != null && c.getValue()) {

						isBooked = true;
						RoomBooking roomBooking = new RoomBooking();
						roomBooking.setTime_Id(c.getTimeId());
						roomBooking.setUser_kuerzel(user.getKuerzel());
						roomBooking.setRaum_id(room);
						roomBooking.setDate(DateFormat.FormatDate4SQLFilter(
								datum.getBaseValue()).toString());
						roomBooking.setStartzeit(DateFormat.Convert2Timestamp(
								datum.getBaseValue(), c.getText(), 1));
						roomBooking.setEndzeit(DateFormat.Convert2Timestamp(
								datum.getBaseValue(), c.getText(), 2));
						roomBookingList.add(roomBooking);
					}
					if (x) {
						isBooked = true;

						// Überbuchungen vom Dozent
						TimeSlot timeSlot = new TimeSlot();
						timeSlot.setBookingId(c.getBookingId());
						timeSlot.setUser_kuerzel(user.getKuerzel());
						timeSlotList.add(timeSlot);
					}

				}

				if (isBooked) {
					buttonSave.setIcon(IconType.REFRESH);
					buttonSave.setIconSpin(true);
					buttonSave.setType(ButtonType.WARNING);

					service.insertRoomBooking(timeSlotList, roomBookingList,
							room, user,
							new AsyncCallback<ArrayList<RoomBooking>>() {

								@Override
								public void onSuccess(
										ArrayList<RoomBooking> result) {
									Integer count = 1;
									RootPanel.get("content1").clear();
									RootPanel.get("content1").add(
											new HTML("</br>"));
									RootPanel.get("content1").add(
											new HTML("</br>"));
									RootPanel.get("content1").add(new Heading(HeadingSize.H4,"Buchungsstatus"));
									for (int j = 0; j < result.size(); j++) {
										
										switch (result.get(j).getId()) {
										case 777:
											RootPanel.get("content1").add(new Alert("Buchung "  + count+ ": " + result.get(j).toString(),AlertType.SUCCESS));
											break;

										case 999:
												RootPanel.get("content1").add(new Alert("Buchung "+ count+ ": "+ result.get(j).toString() + " bereits gebucht!",AlertType.DANGER));
												
											
											break;
											
										default:
											break;
											
										}
										count++;
									}

								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});

				} else {

					buttonSave.state().loading();

					new Timer() {

						@Override
						public void run() {
							buttonSave.state().reset();

						}
					}.schedule(5000);

					buttonSave.setEnabled(true);

					GrowlOptions go = GrowlHelper.getNewOptions();
					go.setCustomType("danger");
					go.setAllowDismiss(false);
					Growl.growl("Fehler",
							"Wählen Sie mindestens einen Zeitraum!",
							Styles.FONT_AWESOME_BASE + " "
									+ IconType.THUMBS_DOWN.getCssName(), go);

				}
			}
		});

	}

	private void init(ArrayList<TimeSlot> result) {

		if (result.isEmpty()) {
			AlertNoBooking.setType(AlertType.INFO);
			AlertNoBooking.setText("Am " + printDate
					+ " sind keine Reservierungen mehr möglich!");
			buttonSave.setVisible(false);
			panel.setVisible(false);
			AlertNoBooking.setVisible(true);

		} else {
			flexTable.clear();
			panel.clear();
			panelHeader.clear();
			panelBody.clear();
			fT.removeAllRows();
			AlertNoBooking.setVisible(false);
			panel.setVisible(true);
			heading.setText("Am " + printDate
					+ " können Sie folgende Zeiten reservieren:");
			panelHeader.add(heading);
			panel.setType(PanelType.SUCCESS);

			for (int i = 0; i < result.size(); i++) {
				fT.getRowFormatter().setStyleName(i, "default");
				fT.setWidget(i, 0,
						new CustomCheckBox(result.get(i).getValue(), result
								.get(i).getTimeId(), result.get(i)
								.getBookingId()));
				fT.setText(i, 1, "");
				if (result.get(i).getUser_kuerzel() != null) {
					fT.setText(i, 1, "Bereits gebucht von Student: "
							+ result.get(i).getUser_kuerzel());
					fT.getRowFormatter().setStyleName(i, "danger");
				}

			}
			flexTable.add(fT);
			panel.add(panelHeader);
			panelBody.add(flexTable);
			panel.add(panelBody);
			buttonSave.setType(ButtonType.SUCCESS);
			buttonSave.setBlock(true);
			buttonSave.setEnabled(true);
			buttonSave.setIcon(IconType.SAVE);
			buttonSave.setVisible(true);
		}

		this.add(AlertNoBooking);
		this.add(panel);
		this.add(buttonSave);

	}

	class loadDates implements AsyncCallback<ArrayList<TimeSlot>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(ArrayList<TimeSlot> result) {

			init(result);
		}

	}

}
