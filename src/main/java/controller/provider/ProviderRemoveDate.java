package controller.provider;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;

import controller.PublicClassController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.UserLogged;
import service.PathFXMLService;

public class ProviderRemoveDate extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private VBox vbox;

	@FXML
	private JFXButton cancelButton;

	@FXML
	private ComboBox<String> comboBox;
	
	@FXML
	private Label labelNoBooking;

	/*
	 * ObservableList for combobox
	 */
	private ObservableList<String> options = FXCollections.observableArrayList();

	@FXML
	protected void initialize() throws SQLException {
		/*
		 * Select all dates
		 */
		dateTimeProviderDAO.selectAllDatesFromProvider(UserLogged.userId);

		/*
		 * Clear options
		 */
		options.clear();
		
		/*
		 * Message there is no bookings
		 */
		if(UserLogged.dates.size() < 1)
		{
			comboBox.setVisible(false);
			labelNoBooking.setVisible(true);
			return;
		}

		/*
		 * Set combobox as a null
		 */
		comboBox.valueProperty().set(null);

		/*
		 * Build a string with name and date/time
		 */
		for (int i = 0; i < UserLogged.dates.size(); i++) {
			int hour = UserLogged.dates.get(i).getHour();
			int minute = UserLogged.dates.get(i).getMinute();
			int year = UserLogged.dates.get(i).getYear();
			int month = UserLogged.dates.get(i).getMonth();
			int day = UserLogged.dates.get(i).getDay();

			String compose = "";

			compose = year + "/" + (month < 10 ? "0" + month : month) + "/" + (day < 10 ? "0" + day : day) + " - ";

			compose += hour < 10 ? "0" + hour + ":" : "" + hour + ":";
			compose += minute < 10 ? "0" + minute : "" + minute;

			compose += " - " + UserLogged.dates.get(i).getProviderName();

			options.add(i, compose);
		}

		/*
		 * Set items
		 */
		comboBox.setItems(options);

		/*
		 * Set button visible if value is != null
		 */
		comboBox.valueProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (comboBox.getValue() != null) {
					cancelButton.setVisible(true);

				} else {
					cancelButton.setVisible(false);
				}

			}
		});

		/*
		 * Fix white background
		 */
		comboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> p) {
				return new ListCell<String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setBackground(new Background(new BackgroundFill(Color.web("#1C1C1C"), CornerRadii.EMPTY, null)));
						setStyle("-fx-text-fill: WHITE;");
						setText(item);
					}
				};
			}

		});

	}

	/*
	 * Cancel button
	 */
	@FXML
	protected void handleCancelButtonAction(ActionEvent event) throws IOException, SQLException {

		if (comboBox.getValue() != null) {

			for (int i = 0; i < UserLogged.dates.size(); i++) {
				if(options.get(i).equalsIgnoreCase(comboBox.getValue())) 
				{
					dateTimeProviderDAO.deleteDate(UserLogged.dates.get(i).getYear(),UserLogged.dates.get(i).getMonth(),UserLogged.dates.get(i).getDay(),UserLogged.dates.get(i).getHour());
					UserLogged.dates.remove(i);
					options.remove(i);
				}
			}
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
	 * Add date button
	 */
	@FXML
	protected void handleAddDateButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.PROVIDER_ADD_DATE_SCREEN);
	}
	
	/*
	 * Remove date button
	 */
	@FXML
	protected void handleBookingButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.PROVIDER_VIEW_CUSTUMER_BOOKING_SCREEN);
	}

}
