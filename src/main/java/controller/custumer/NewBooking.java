package controller.custumer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import controller.PublicClassController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.UserLogged;
import service.PathFXMLService;

public class NewBooking extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private VBox vbox;

	@FXML
	private JFXDatePicker datePicker;

	@FXML
	private ComboBox<String> timePicker;

	@FXML
	private JFXButton buttonBooking;

	@FXML
	private Label labelNoTime1;

	@FXML
	private Label labelNoTime2;

	/*
	 * ObservableList for combobox options
	 */
	private ObservableList<String> options = FXCollections.observableArrayList();

	@FXML
	protected void initialize() throws SQLException {
		/*
		 * Clear options
		 */
		options.clear();

		/*
		 * Set time as a null
		 */
		timePicker.valueProperty().set(null);

		/*
		 * Get all providers from sql
		 */
		dateTimeProviderDAO.selectAllProviders(1, UserLogged.idProvider);

		/*
		 * Message there is no providers
		 */
		if (UserLogged.dates.size() < 1) {

			labelNoTime1.setVisible(true);
			labelNoTime2.setVisible(true);
			return;
		}

		timePicker.setItems(options);

		datePicker.setVisible(true);

		/*
		 * Add dates according to all data got from sql and change color
		 */
		datePicker.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				setDisable(true);
				setStyle("-fx-text-fill: WHITE; -fx-background-color: #1C1C1C; -fx-background-radius: 30; -fx-border-radius: 30;");
				setBackground(new Background(new BackgroundFill(Color.web("#1C1C1C"), CornerRadii.EMPTY, null)));
				super.updateItem(item, empty);
				for (int i = 0; i < UserLogged.dates.size(); i++) {
					if (item.isBefore(LocalDate.now()) || item.isEqual(LocalDate.now())) {
						setDisable(true);
					}
					else if (item.isEqual(LocalDate.of(UserLogged.dates.get(i).getYear(), UserLogged.dates.get(i).getMonth(),
							UserLogged.dates.get(i).getDay()))) {
						setStyle("-fx-text-fill: BLACK; -fx-background-color: KHAKI; -fx-background-radius: 30; -fx-border-radius: 30;");
						setDisable(false);
					}
				}
			}
		});
		datePicker.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				k.consume();
			}
		});
		/*
		 * update date and convert into string
		 */
		datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {

				options.clear();

				if (dateFormat.dateFormatString(datePicker.getValue().toString()) == true) {
					timePicker.setVisible(true);
					for (int i = 0; i < UserLogged.dates.size(); i++) {
						if (dateFormat.getDay() == UserLogged.dates.get(i).getDay()
								&& dateFormat.getMonth() == UserLogged.dates.get(i).getMonth()
								&& dateFormat.getYear() == UserLogged.dates.get(i).getYear()) {
							int hour = UserLogged.dates.get(i).getHour();
							int minute = UserLogged.dates.get(i).getMinute();

							String compose = hour < 10 ? "0" + hour + ":" : "" + hour + ":";
							compose += minute < 10 ? "0" + minute : "" + minute;

							options.add(compose);
						}
					}
				}
			}
		});
		/*
		 * If the user edit manually, the timepick is set as invisible
		 */
		datePicker.getEditor().setOnKeyReleased(event -> {

			timePicker.setVisible(false);
			event.consume();
		});
		/*
		 * Check value
		 */
		timePicker.valueProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (timePicker.getValue() != null) {
					buttonBooking.setVisible(true);

					if (dateFormat.timeFormatString(timePicker.getValue().toString()) == true) {

					}

				} else {
					buttonBooking.setVisible(false);
				}

			}
		});
		/*
		 * Fill with the dates
		 */
		timePicker.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> p) {
				return new ListCell<String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						setBackground(
								new Background(new BackgroundFill(Color.web("#1C1C1C"), CornerRadii.EMPTY, null)));
						setStyle("-fx-text-fill: WHITE;");
						setText(item);
					}
				};
			}

		});

	}

	/*
	 * Logout button
	 */
	@FXML
	protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.LOGIN_SCREEN);
	}

	/*
	 * Back button
	 */
	@FXML
	protected void handleBackButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.VIEW_PROVIDER_SCREEN);
	}

	/*
	 * Cancel bookings
	 */
	@FXML
	protected void handleViewBookingButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.CANCEL_BOOKING_SCREEN);

	}

	/*
	 * Booking button
	 */
	@FXML
	protected void handleBookingButtonAction(ActionEvent event) throws SQLException, IOException {

		/*
		 * Add booking into sql
		 */
		if (datePicker.getValue() != null && timePicker.getValue() != null) {
			for (int i = 0; i < UserLogged.dates.size(); i++) {
				if (dateFormat.getDay() == UserLogged.dates.get(i).getDay()
						&& dateFormat.getMonth() == UserLogged.dates.get(i).getMonth()
						&& dateFormat.getYear() == UserLogged.dates.get(i).getYear()
						&& dateFormat.getHour() == UserLogged.dates.get(i).getHour()
						&& dateFormat.getMinute() == UserLogged.dates.get(i).getMinute()) {
					if (dateTimeProviderDAO.addBookingByCustumer(UserLogged.userId,
							UserLogged.dates.get(i).getId()) == true) {
						LoadFXMLWithSpinner(vbox,PathFXMLService.BOOKING_CONFIRMED_SCREEN);
					}
				}
			}
		}
	}

}