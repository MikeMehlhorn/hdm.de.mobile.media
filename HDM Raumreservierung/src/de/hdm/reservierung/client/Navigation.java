package de.hdm.reservierung.client;

import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Input;
import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarBrand;
import org.gwtbootstrap3.client.ui.NavbarCollapse;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.AlertType;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.NavbarPosition;
import org.gwtbootstrap3.client.ui.constants.NavbarType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import de.hdm.reservierung.shared.User;

public class Navigation extends Composite {
	private User gv_user;

	private static NavigationUiBinder uiBinder = GWT
			.create(NavigationUiBinder.class);

	private ServiceAsync service = ClientsideSettings.getService();

	private final Alert alert = new Alert(
			"Anmeldung fehlgeschlagen! Ungültiger Benutzername oder Passwort",
			AlertType.DANGER);

	interface NavigationUiBinder extends UiBinder<Widget, Navigation> {
	}

	@UiField
	NavbarCollapse navbarcoll;
	@UiField
	Heading info;
	@UiField
	Navbar navbar;
	@UiField
	NavbarBrand navbarbrand;
	@UiField
	Button login;
	@UiField
	Button logout;
	@UiField
	TextBox username;
	@UiField
	Input password;
	@UiField
	static AnchorListItem RoomOverview;
	@UiField
	static AnchorListItem Raumbuchungen;
	@UiField
	static AnchorListItem Uebersicht;

	public Navigation() {

	}

	@Override
	protected void onLoad() {
		super.onLoad();
	}

	public Navigation(Boolean loggedIn) {
		initWidget(uiBinder.createAndBindUi(this));
		// Je nach Status (Eingeloggt Ja|Nein) wird der entsprechend
		// weitergeleitet
		init();
		if (loggedIn) {
			// Window.alert(Cookies.getCookie("hdm_raumreservierung"));
			service.getUser(Cookies.getCookie("hdm_raumreservierung"),
					new getUser());
			loggedIn();
		} else {
			loggedOut();
		}

	}

	private void init() {

		info.setVisible(false);

		navbar.setType(NavbarType.DEFAULT);
		navbar.setPosition(NavbarPosition.FIXED_TOP);
		// navbarbrand.setText("HdM");
		Image logo = new Image();
		logo.setResponsive(true);
		logo.setAltText("HdM");
		logo.setUrl("img/Logo.png");
		navbarbrand.setPaddingTop(6);
		navbarbrand.add(logo);
		RoomOverview.setVisible(false);
		Raumbuchungen.setVisible(false);

		Uebersicht.setVisible(false);
	}

	private void loggedOut() {
		logout.setVisible(false);
		login.setText("Anmelden");

	}

	private void loggedIn() {
		login.setVisible(false);
		logout.setType(ButtonType.DANGER);
		logout.setText("Abmelden");
		logout.setVisible(true);
		username.setVisible(false);
		password.setVisible(false);
		Raumbuchungen.setIconBordered(true);
		RoomOverview.setIconBordered(true);
		Uebersicht.setVisible(true);
		Uebersicht.setIconBordered(true);
		info.setVisible(true);
		RoomOverview.setVisible(true);
		Raumbuchungen.setVisible(true);
	}

	@UiHandler("logout")
	public void logout(final ClickEvent event) {
		GUIFunctions.clearRootPanel("navigation");
		GUIFunctions.clearRootPanel("content1");
		Cookies.removeCookie("hdm_raumreservierung");
		// Cookies.removeCookie(gv_user.getKuerzel());
		RootPanel.get("navigation").add(new Navigation(false));

	}

	@UiHandler("login")
	public void login(final ClickEvent event) {
		login.state().loading();

		new Timer() {

			@Override
			public void run() {
				login.state().reset();

			}
		}.schedule(2000);

		if (username.getValue().isEmpty() || password.getText().isEmpty()) {
			GUIFunctions.clearRootPanel("content1");

			alert.setDismissable(true);
			RootPanel.get("content1").add(alert);
		} else {

			service.checkLogin(username.getValue(), password.getText(),
					new AsyncCallback<User>() {
						@Override
						public void onSuccess(User user) {
							if (user.getKuerzel() == null) {
								alert.setDismissable(true);
								RootPanel.get("content1").add(alert);
							} else {
								setUserInformation(user);
								gv_user = user;
								Cookies.setCookie("hdm_raumreservierung",
										user.getKuerzel());
								loggedIn();
								// Zeigt alle Raumbuchgen an
								GUIFunctions.clearRootPanel("content1");
								Raumbuchungen.setActive(setActive(2));
								RootPanel.get("content1").add(
										new ShowRoombookingFromUser(user));

							}

						}

						@Override
						public void onFailure(Throwable caught) {
							// Window.alert(caught.getMessage());

						}
					});
		}

	}

	@UiHandler("RoomOverview")
	public void Raumreservierung(final ClickEvent event) {
		hideNavbarCollapse();
		GUIFunctions.clearRootPanel("content1");
		RoomOverview.setActive(setActive(1));
		RootPanel.get("content1").add(new RoomOverview(gv_user));
	}

	@UiHandler("Raumbuchungen")
	public void Raumbuchungen(final ClickEvent event) {
		hideNavbarCollapse();
		GUIFunctions.clearRootPanel("content1");
		Raumbuchungen.setActive(setActive(2));
		RootPanel.get("content1").add(new ShowRoombookingFromUser(gv_user));

	}

	@UiHandler("Uebersicht")
	public void Uebersicht(final ClickEvent event) {
		hideNavbarCollapse();
		GUIFunctions.clearRootPanel("content1");
		Uebersicht.setActive(setActive(3));
		RootPanel.get("content1").add(new Calendar());

	}

	@UiHandler("navbarbrand")
	public void navbarbrand(final ClickEvent event) {
		GUIFunctions.clearRootPanel("content1");
		Raumbuchungen.setActive(setActive(2));
		RootPanel.get("content1").add(new ShowRoombookingFromUser(gv_user));
		// btn.setVisible(false);

	}

	private void hideNavbarCollapse() {
		if (navbarcoll.getElement().getClassName()
				.compareTo("navbar-collapse collapse in") == 0) {
			navbarcoll.toggle();

		}
	}

	public Boolean setActive(Integer integer) {
		Raumbuchungen.setActive(false);
		RoomOverview.setActive(false);
		Uebersicht.setActive(false);

		switch (integer) {
		case 1:
			return true;

		case 2:
			return true;

		case 3:
			return true;

		default:
			return false;
		}

	}

	private void setUserInformation(User user) {
		info.setText(user.getVorname() + " " + user.getNachname() + " | "
				+ user.getKuerzel() + " | " + user.getRolle());
	}

	class getUser implements AsyncCallback<User> {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSuccess(User result) {
			if (result != null) {
				gv_user = result;
				setUserInformation(result);
				GUIFunctions.clearRootPanel("content1");
				Raumbuchungen.setActive(setActive(2));
				RootPanel.get("content1").add(
						new ShowRoombookingFromUser(result));
			}

		}

	}

}
