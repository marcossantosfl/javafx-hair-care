package controller.custumer;

import java.io.IOException;
import java.sql.SQLException;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;

import controller.PublicClassController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.UserLogged;
import service.PathFXMLService;

public class WhichAreaController extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private VBox vbox;
	
	@FXML
	private JFXButton buttonSouth;

	@FXML
	private JFXButton buttonNorth;

	@FXML
	protected void initialize() throws SQLException {
		/*
		 * Set notification if any provider cancel the user's bookings
		 */
		if (notificationService.getNotification(UserLogged.userId) > 0) {
			Notifications.create().title("Bookings").text(
					"Booking cancelled by the Provider! please check your bookings...(click to delete this message)")
					.hideAfter(Duration.seconds(8)).position(Pos.CENTER).onAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							try {
								notificationService.deleteNotification(UserLogged.userId);
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}).darkStyle().showError();
		}

	}

	/*
	 * LOAD fxml
	 */
	void Load() throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.VIEW_PROVIDER_SCREEN);
	}

	/*
	 * South button
	 */
	@FXML
	protected void handleSouthButtonAction(ActionEvent event) throws IOException {
		UserLogged.userLocation = 0;

		Load();

	}

	/*
	 * North button
	 */
	@FXML
	protected void handleNorthButtonAction(ActionEvent event) throws IOException {
		UserLogged.userLocation = 1;

		Load();
	}

	/*
	 * Logout button
	 */
	@FXML
	protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.LOGIN_SCREEN);

	}

	/*
	 * Cancel booking button
	 */
	@FXML
	protected void handleBookingButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.CANCEL_BOOKING_SCREEN);
	}
}
