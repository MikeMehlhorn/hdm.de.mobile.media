package de.hdm.reservierung.client;

import java.util.ArrayList;

import de.hdm.reservierung.client.custom.CustomTable;
import de.hdm.reservierung.client.custom.Template;
import de.hdm.reservierung.shared.RoomBooking;
import de.hdm.reservierung.shared.User;

import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Well;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.WellSize;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ShowRoombookingFromUser extends Template {

	private CustomTable customTable;
	private ServiceAsync service = ClientsideSettings.getService();

	private Well well = new Well();

	public ShowRoombookingFromUser(User user) {
		onLoad();
		this.setHeaderText("");
		Navigation.Raumbuchungen.setActive(true);
		Navigation.RoomOverview.setActive(false);
		service.getAllRaumbuchungenFromUser(user, new loadDates());

	}

	private void setFormNoData() {
		well.setSize(WellSize.LARGE);
		Heading h2 = new Heading(HeadingSize.H2);
		h2.setText("Keine Raumbuchungen für Sie vorhanden!");
		well.add(h2);
		this.add(well);

	}

	private void setFormWithData(ArrayList<RoomBooking> result) {
		customTable = new CustomTable(null);
		DataSource datasource = new DataSource(result);
		customTable.setInput(datasource);
		this.add(customTable);
	}

	class loadDates implements AsyncCallback<ArrayList<RoomBooking>> {

		@Override
		public void onFailure(Throwable caught) {
			caught.getMessage();

		}

		@Override
		public void onSuccess(ArrayList<RoomBooking> result) {
			if (result.size() == 0) {
				setFormNoData();
			} else {
				setFormWithData(result);
			}
		}

	}

}
