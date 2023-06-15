package controller.custumer;

import java.io.IOException;

import animatefx.animation.Bounce;
import controller.PublicClassController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import service.PathFXMLService;

public class ViewConfirmedOrDeniedController extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private VBox vbox;
	
	@FXML
	private Circle circle1;
	
	@FXML
	private Circle circle2;
	
	@FXML
	private Circle circle3;

	@FXML
	protected void initialize() {
		
		if(circle1 != null && circle2 != null && circle3 != null)
		{
			new Bounce(circle1).setCycleCount(9999999).setDelay(Duration.millis(500)).play();
			new Bounce(circle2).setCycleCount(9999999).setDelay(Duration.millis(1000)).play();
			new Bounce(circle3).setCycleCount(9999999).setDelay(Duration.millis(1100)).play();
		}

	}

	/*
	 * Logout button
	 */
	@FXML
	protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.LOGIN_SCREEN);
	}

	/*
	 * New booking button
	 */
	@FXML
	protected void handleNewBookingButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.WHICHAREA_SCREEN);
	}

	/*
	 * Cancel booking button
	 */
	@FXML
	protected void handleBookingButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.CANCEL_BOOKING_SCREEN);
	}

}
