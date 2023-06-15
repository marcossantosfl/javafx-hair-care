package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import model.AccountLogged;
import service.FXMLService;
import service.TransitionService;

public class DateTimeController extends GeneralController {

	@FXML
	private BorderPane borderDatetime;

	@FXML
	private JFXDatePicker datePicker;

	@FXML
	private ComboBox<String> timePicker;

	@FXML
	private JFXButton buttonBook;

	private boolean blockDatePicker = false;

	private ObservableList<String> options = FXCollections.observableArrayList();

	class DateFormat {
		int year;
		int month;
		int day;
		int hour;
		int minute;
	}

	DateFormat dateFormat = new DateFormat();

	private int idSelected = -1;

	private boolean dateFormatString(String value) {

		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == '-') {
				dateFormat.year = Integer.parseInt(value.substring(0, 4));
				dateFormat.month = Integer.parseInt(value.substring(5, 7));
				dateFormat.day = Integer.parseInt(value.substring(8, 10));

				return true;
			}
		}

		return false;
	}

	private boolean timeFormatString(String value) {

		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == ':') {
				dateFormat.hour = Integer.parseInt(value.substring(0, 2));
				dateFormat.minute = Integer.parseInt(value.substring(3, 5));
				return true;
			}
		}

		return false;
	}

	@FXML
	protected void initialize() throws SQLException {

		dateTimeProviderDAO.selectAllProviders(1, idProvider);

		if (AccountLogged.datetimeproviders.size() < 1) {
			// set error message (does not have any day)
			return;
		}

		timePicker.setItems(options);

		datePicker.setVisible(true);

		datePicker.setDayCellFactory(d -> new DateCell() {

			@Override
			public void updateItem(LocalDate item, boolean empty) {
				setDisable(true);
				setStyle("-fx-background-color: #ffc0cb;");
				super.updateItem(item, empty);
				for (int i = 0; i < AccountLogged.datetimeproviders.size(); i++) {
					if (item.isEqual(LocalDate.of(AccountLogged.datetimeproviders.get(i).getYear(),
							AccountLogged.datetimeproviders.get(i).getMonth(),
							AccountLogged.datetimeproviders.get(i).getDay()))) {
						setStyle("-fx-background-color: #1E90FF;");
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
		datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {

				options.clear();

				if (dateFormatString(datePicker.getValue().toString()) == true) {
					timePicker.setVisible(true);
					blockDatePicker = false;
					for (int i = 0; i < AccountLogged.datetimeproviders.size(); i++) {
						if (dateFormat.day == AccountLogged.datetimeproviders.get(i).getDay()
								&& dateFormat.month == AccountLogged.datetimeproviders.get(i).getMonth()
								&& dateFormat.year == AccountLogged.datetimeproviders.get(i).getYear()) {
							int hour = AccountLogged.datetimeproviders.get(i).getHour();
							int minute = AccountLogged.datetimeproviders.get(i).getMinute();

							String compose = hour < 10 ? "0" + hour + ":" : "" + hour + ":";
							compose += minute < 10 ? "0" + minute : "" + minute;

							options.add(compose);
						}
					}
				}
			}
		});
		datePicker.getEditor().setOnKeyReleased(event -> {

			blockDatePicker = true;
			timePicker.setVisible(false);
			event.consume();
		});
		timePicker.valueProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (timePicker.getValue() != null) {
					buttonBook.setVisible(true);

					if (timeFormatString(timePicker.getValue().toString()) == true) {

					}

				} else {
					buttonBook.setVisible(false);
				}

			}
		});

	}

	@FXML
	protected void handleBookButtonAction(ActionEvent event) throws SQLException, IOException {
		if (datePicker.getValue() != null && timePicker.getValue() != null) {

			for (int i = 0; i < AccountLogged.datetimeproviders.size(); i++) {
				if (dateFormat.day == AccountLogged.datetimeproviders.get(i).getDay()
						&& dateFormat.month == AccountLogged.datetimeproviders.get(i).getMonth()
						&& dateFormat.year == AccountLogged.datetimeproviders.get(i).getYear()
						&& dateFormat.hour == AccountLogged.datetimeproviders.get(i).getHour()
						&& dateFormat.minute == AccountLogged.datetimeproviders.get(i).getMinute()) {
					if (dateTimeProviderDAO.saveProvider(AccountLogged.accountId,
							AccountLogged.datetimeproviders.get(i).getId()) == true) {

						BorderPane border = FXMLLoaderInit(borderDatetime, FXMLService.TRANSITION_SCREEN, true);

						/*
						 * Check later
						 */
						final BorderPane borderSignup = FXMLLoaderInit(border, FXMLService.CONFIRMED, false);

						TransitionService.PauseTransitionAndSetElement(border, borderSignup, 1);
					}
				}
			}
		}
	}

}