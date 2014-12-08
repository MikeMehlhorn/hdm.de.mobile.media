package de.hdm.reservierung.client.custom;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.AlertType;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.extras.bootbox.client.Bootbox;
import org.gwtbootstrap3.extras.bootbox.client.callback.ConfirmCallback;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.reservierung.client.ShowRoombookingFromUser;
import de.hdm.reservierung.client.ClientsideSettings;
import de.hdm.reservierung.client.DataSource;
import de.hdm.reservierung.client.GUIFunctions;
import de.hdm.reservierung.client.ServiceAsync;
import de.hdm.reservierung.shared.RoomBooking;
import de.hdm.reservierung.shared.User;

public class CustomTable extends FlexTable {

	private ServiceAsync service = ClientsideSettings.getService();

	DataSource input;
	Button cancel;

	public CustomTable(DataSource input) {
		super();
		this.setInput(input);

	}

	public void setInput(DataSource input) {
		for (int i = this.getRowCount(); i > 0; i--) {
			this.removeRow(0);
		}
		if (input == null) {
			return;
		}

		int row = 0;
		List<String> headers = input.getTableHeader();
		if (headers != null) {
			int i = 0;
			for (String string : headers) {
				this.setText(row, i, string);
				i++;
			}
			row++;
		}
		// make the table header look nicer
		this.getRowFormatter().addStyleName(0, "tableHeader");
		this.setStyleName("table");

		List<RoomBooking> rows = input.getRaumbuchung();
		int i = 1;

		for (final RoomBooking roomBooking : rows) {

			final CustomButton cancel = new CustomButton(roomBooking.getId());

			this.setText(i, 0, String.valueOf(roomBooking.getRoom().getId()));

			String a = DateFormat.FormatDateDDMMYYYY2(roomBooking
					.getStartzeit().toString());

			ArrayList<String> x = DateFormat
					.SeperateDateTime2String(roomBooking.getStartzeit()
							.toString());
			ArrayList<String> y = DateFormat
					.SeperateDateTime2String(roomBooking.getEndzeit()
							.toString());

			this.setText(i, 1, a + " ab " + x.get(1) + " - " + y.get(1));
			this.setWidget(i, 2, cancel);
			i++;

			// Add a button to remove this stock from the table.
			cancel.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Bootbox.confirm(
							"Wollen Sie diese Reservierung wirklich löschen?",
							new ConfirmCallback() {
								@Override
								public void callback(boolean result) {
									if (result) {
										cancel.setIcon(IconType.REFRESH);
										cancel.setIconSpin(true);
										cancel.setType(ButtonType.WARNING);
										service.deleteRaumbuchung(cancel.getpK(), new AsyncCallback<RoomBooking>() {
											
											@Override
											public void onSuccess(RoomBooking result) {
												GUIFunctions.clearRootPanel("content1");
												RootPanel.get("content1").add(
														new HTML("</br>"));
												RootPanel.get("content1").add(
														new HTML("</br>"));
												RootPanel.get("content1").add(new Alert("Ihre Reservierung am " + result.toString() + " wurde erfolgreich gelöscht.", AlertType.INFO));
												User user = new User();
												user.setKuerzel(roomBooking.getUser().getKuerzel());
												RootPanel.get("content1").add(new ShowRoombookingFromUser(user));
												
											}
											
											@Override
											public void onFailure(Throwable caught) {
												// TODO Auto-generated method stub
												
											}
										});

									}

								}
							});

				}
			});

		}
		this.input = input;
	}
}
