package de.hdm.reservierung.client;

import java.util.ArrayList;

import org.gwtbootstrap3.client.ui.constants.DeviceSize;

import com.google.gwt.dom.client.AreaElement;
import com.google.gwt.dom.client.MapElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.reservierung.client.custom.CustomImage;
import de.hdm.reservierung.client.custom.CustomRoomImage;
import de.hdm.reservierung.client.custom.Template;
import de.hdm.reservierung.shared.Room;
import de.hdm.reservierung.shared.User;

public class RoomOverview extends Template implements ResizeHandler {

	private FlowPanel flowPanel = new FlowPanel();
	private ServiceAsync service = ClientsideSettings.getService();
	private CustomImage customImage = new CustomImage("img/Grundriss.PNG");
	
	private MapElement mapElement;
	private AreaElement areaElement;

	public RoomOverview(final User user) {
		this.setHeaderText("Raumübersicht");
		
	
		
		
		customImage.setVisibleOn(DeviceSize.MD_LG);

		service.getAllRooms(new AsyncCallback<ArrayList<Room>>() {

			@Override
			public void onSuccess(ArrayList<Room> result) {
				for (int i = 0; i < result.size(); i++) {
					final CustomRoomImage customRoomImage = new CustomRoomImage(
							"img/" + result.get(i).getId() + ".jpg", result
									.get(i).getCapacity(), result.get(i)
									.getId());
					customRoomImage.setVisibleOn(DeviceSize.XS_SM);
					customRoomImage.setId(result.get(i).getId());
					customRoomImage.setAltText(result.get(i).getId());

					flowPanel.add(customRoomImage);
					customRoomImage.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							GUIFunctions.clearRootPanel("content1");
							RootPanel.get("content1").add(
									new RoomBookingForm(
											customRoomImage.getId(), user));
						}
					});

				}

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

		this.add(flowPanel);
		this.add(customImage);
	}

	@Override
	public void onResize(ResizeEvent event) {

	}
}
