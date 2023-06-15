package controller.provider;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;

import controller.PublicClassController;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.UserLogged;
import model.Dates;
import service.PathFXMLService;
import service.GeneralAnimationService;

public class DateTimeProviderAdd extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private VBox vbox;

	@FXML
	private JFXDatePicker datePicker;

	@FXML
	private JFXButton button12;
	@FXML
	private JFXButton button1;
	@FXML
	private JFXButton button2;
	@FXML
	private JFXButton button3;
	@FXML
	private JFXButton button4;
	@FXML
	private JFXButton button5;
	@FXML
	private JFXButton button6;
	@FXML
	private JFXButton button7;
	@FXML
	private JFXButton button8;
	@FXML
	private JFXButton button9;
	@FXML
	private JFXButton button10;
	@FXML
	private JFXButton button11;
	@FXML
	private JFXButton buttonAM;
	@FXML
	private JFXButton buttonOK;
	@FXML
	private Circle circle;

	private boolean button1Clicked = false;
	private boolean button2Clicked = false;
	private boolean button3Clicked = false;
	private boolean button4Clicked = false;
	private boolean button5Clicked = false;
	private boolean button6Clicked = false;
	private boolean button7Clicked = false;
	private boolean button8Clicked = false;
	private boolean button9Clicked = false;
	private boolean button10Clicked = false;
	private boolean button11Clicked = false;
	private boolean button12Clicked = false;
	private boolean buttonAMClicked = false;

	/*
	 * New dates
	 */
	public static List<Dates> newDates = new ArrayList<>();

	/*
	 * Node to get parameter
	 */
	Node node;

	@FXML
	protected void initialize() throws SQLException {
		/*
		 * Select all provider's dates
		 */
		dateTimeProviderDAO.selectAllProvidersDates(UserLogged.userId);

		/*
		 * Change style according to the date
		 */
		datePicker.setDayCellFactory(d -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				setStyle(
						"-fx-text-fill: GREEN; -fx-background-color: #1C1C1C; -fx-background-radius: 30; -fx-border-radius: 30;");
				setBackground(new Background(new BackgroundFill(Color.web("#1C1C1C"), CornerRadii.EMPTY, null)));
				super.updateItem(item, empty);
				if (item.isBefore(LocalDate.now()) || item.isEqual(LocalDate.now())) {
					setDisable(true);
				}
				for (int i = 0; i < UserLogged.dates.size(); i++) {
					if (item.isEqual(LocalDate.of(UserLogged.dates.get(i).getYear(),
							UserLogged.dates.get(i).getMonth(),
							UserLogged.dates.get(i).getDay()))) {
						setStyle(
								"-fx-text-fill: BLACK; -fx-background-color: KHAKI; -fx-background-radius: 30; -fx-border-radius: 30;");
						setDisable(true);
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
		 * Add multiple values
		 */
		datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
			public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
					LocalDate newValue) {

				if (dateFormat.dateFormatString(datePicker.getValue().toString()) == true) {
					Dates provider = new Dates();
					provider.setYear(dateFormat.getYear());
					provider.setMonth(dateFormat.getMonth());
					provider.setDay(dateFormat.getDay());

					if (newDates.size() < 1) {
						if (newValue != LocalDate.now()) {
							newDates.add(provider);
						}
					} else {
						for (int i = 0; i < newDates.size(); i++) {
							newDates.get(i).setYear(dateFormat.getYear());
							newDates.get(i).setMonth(dateFormat.getMonth());
							newDates.get(i).setDay(dateFormat.getDay());
						}
					}
				}

				if ((newValue != LocalDate.now()) && (button1Clicked || button2Clicked || button3Clicked
						|| button4Clicked || button5Clicked || button6Clicked || button7Clicked || button8Clicked
						|| button9Clicked || button10Clicked || button11Clicked || button12Clicked)) {
					buttonOK.setVisible(true);
				}

			}
		});
		datePicker.getEditor().setOnKeyReleased(event -> {
			buttonOK.setVisible(false);
			event.consume();
		});

	}

	/*
	 * Hour button
	 */
	@FXML
	protected void handleHourButtonAction(ActionEvent event) {
		
		/*
		 * Init node to get the index (indetified in the FXML)
		 */
		node = (Node) event.getSource();
		String data = (String) node.getUserData();
		int value = Integer.parseInt(data);

		/*
		 * Original style and green style if clicked
		 */
		String styleClick = "-fx-border-radius: 30; -fx-background-color: #1C1C1C; -fx-border-color: GREEN; -fx-background-radius: 30; -fx-min-width: 35px; -fx-min-height: 35px; -fx-max-width: 35px; -fx-max-height: 35px;";
		String styleNoClick = "-fx-border-radius: 30; -fx-background-color: #1C1C1C; -fx-border-color: KHAKI; -fx-background-radius: 30; -fx-min-width: 35px; -fx-min-height: 35px; -fx-max-width: 35px; -fx-max-height: 35px;";

		if (datePicker.getValue() == null && newDates.size() < 1) {
			return;
		}
		
		/*
		 * New dates to add on a list
		 */
		Dates provider = new Dates();

		/*
		 * Controls all the clicks, if the user select or not the hours
		 */
		switch (value) {
		case 12:
			button12.setStyle(button12Clicked ? styleNoClick : styleClick);
			button12Clicked = button12Clicked ? false : true;
			if (!button12Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 11:
			button11.setStyle(button11Clicked ? styleNoClick : styleClick);
			button11Clicked = button11Clicked ? false : true;
			if (!button11Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 10:
			button10.setStyle(button10Clicked ? styleNoClick : styleClick);
			button10Clicked = button10Clicked ? false : true;
			if (!button10Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 9:
			button9.setStyle(button9Clicked ? styleNoClick : styleClick);
			button9Clicked = button9Clicked ? false : true;
			if (!button9Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 8:
			button8.setStyle(button8Clicked ? styleNoClick : styleClick);
			button8Clicked = button8Clicked ? false : true;
			if (!button8Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 7:
			button7.setStyle(button7Clicked ? styleNoClick : styleClick);
			button7Clicked = button7Clicked ? false : true;
			if (!button7Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 6:
			button6.setStyle(button6Clicked ? styleNoClick : styleClick);
			button6Clicked = button6Clicked ? false : true;
			if (!button6Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 5:
			button5.setStyle(button5Clicked ? styleNoClick : styleClick);
			button5Clicked = button5Clicked ? false : true;
			if (!button5Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 4:
			button4.setStyle(button4Clicked ? styleNoClick : styleClick);
			button4Clicked = button4Clicked ? false : true;
			if (!button4Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 3:
			button3.setStyle(button3Clicked ? styleNoClick : styleClick);
			button3Clicked = button3Clicked ? false : true;
			if (!button3Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 2:
			button2.setStyle(button2Clicked ? styleNoClick : styleClick);
			button2Clicked = button2Clicked ? false : true;
			if (!button2Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 1:
			button1.setStyle(button1Clicked ? styleNoClick : styleClick);
			button1Clicked = button1Clicked ? false : true;
			if (!button1Clicked) {
				for (int i = 0; i < newDates.size(); i++) {
					if (newDates.get(i).getHour() == value) {
						newDates.remove(i);
					}
				}
				return;
			}
			break;
		case 13:
			buttonAM.setText(buttonAMClicked ? "AM" : "PM");
			buttonAMClicked = buttonAMClicked ? false : true;
			break;
		}

		/*
		 * If the user click on PM (it changes automatically)
		 */
		if (value < 13 && datePicker.getValue() != null) {
			if (buttonAMClicked) {
				value += value == 12 ? 0 : 12;
			}
			buttonOK.setVisible(true);
			provider.setYear(newDates.get(0).getYear());
			provider.setMonth(newDates.get(0).getMonth());
			provider.setDay(newDates.get(0).getDay());
			provider.setHour(value);
			provider.setMinute(0);
			newDates.add(provider);
		} else {
			if ((datePicker.getValue() == null) && !button1Clicked && !button2Clicked && !button3Clicked
					&& !button4Clicked && !button5Clicked && !button6Clicked && !button7Clicked && !button8Clicked
					&& !button9Clicked && !button10Clicked && !button11Clicked && !button12Clicked) {
				buttonOK.setVisible(false);
			} else if ((!button1Clicked && !button2Clicked && !button3Clicked && !button4Clicked && !button5Clicked
					&& !button6Clicked && !button7Clicked && !button8Clicked && !button9Clicked && !button10Clicked
					&& !button11Clicked && !button12Clicked)) {
				buttonOK.setVisible(false);
			}
		}

	}

	/*
	 * Execute all the queries in a thread (bug effect fixed)
	 */
	public class SQLThread extends Thread {

		public void run() {
			for (int i = 0; i < newDates.size(); i++) {
				if (newDates.get(i).getHour() != 0) {
					try {
						dateTimeProviderDAO.addProviderHours(UserLogged.userId, newDates.get(i));
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(1L * 200L);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			newDates.clear();
			this.interrupt();
		}
	}

	/*
	 * OK button
	 */
	@FXML
	protected void handleOKButtonAction(ActionEvent event) throws IOException, SQLException {

		/*
		 * Start the thread
		 */
		SQLThread sQLThread = new SQLThread();

		sQLThread.start();

		/*
		 * Set elements as invisible
		 */
		datePicker.setVisible(false);
		buttonOK.setVisible(false);
		circle.setVisible(false);

		/*
		 * Translation effect
		 */
		GeneralAnimationService.TranslationHourButton(button12, 7000);
		GeneralAnimationService.TranslationHourButton(button11, 6000);
		GeneralAnimationService.TranslationHourButton(button10, 5000);
		GeneralAnimationService.TranslationHourButton(button9, 4000);
		GeneralAnimationService.TranslationHourButton(button8, 3000);
		GeneralAnimationService.TranslationHourButton(button7, 2000);
		GeneralAnimationService.TranslationHourButton(button6, 1000);
		GeneralAnimationService.TranslationHourButton(button5, 7000);
		GeneralAnimationService.TranslationHourButton(button4, 8000);
		GeneralAnimationService.TranslationHourButton(button3, 9000);
		GeneralAnimationService.TranslationHourButton(button2, 8000);
		GeneralAnimationService.TranslationHourButton(button1, 7000);
		GeneralAnimationService.TranslationHourButton(buttonAM, 5000);

		/*
		 * Pause transition for 4 second while effect is working
		 */
		PauseTransition pause = new PauseTransition(Duration.seconds(4));
		pause.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				
				/*
				 * TranslationBack effect
				 */
				GeneralAnimationService.TranslationBack(button12, 7000);
				GeneralAnimationService.TranslationBack(button11, 6000);
				GeneralAnimationService.TranslationBack(button10, 5000);
				GeneralAnimationService.TranslationBack(button9, 4000);
				GeneralAnimationService.TranslationBack(button8, 3000);
				GeneralAnimationService.TranslationBack(button7, 6000);
				GeneralAnimationService.TranslationBack(button6, 4000);
				GeneralAnimationService.TranslationBack(button5, 7000);
				GeneralAnimationService.TranslationBack(button4, 8000);
				GeneralAnimationService.TranslationBack(button3, 9000);
				GeneralAnimationService.TranslationBack(button2, 8000);
				GeneralAnimationService.TranslationBack(button1, 7000);
				GeneralAnimationService.TranslationBack(buttonAM, 5000);

				String styleNoClick = "-fx-border-radius: 30; -fx-background-color: #1C1C1C; -fx-border-color: KHAKI; -fx-background-radius: 30; -fx-min-width: 35px; -fx-min-height: 35px; -fx-max-width: 35px; -fx-max-height: 35px;";

				button1Clicked = false;
				button2Clicked = false;
				button3Clicked = false;
				button4Clicked = false;
				button5Clicked = false;
				button6Clicked = false;
				button7Clicked = false;
				button8Clicked = false;
				button9Clicked = false;
				button10Clicked = false;
				button11Clicked = false;
				button12Clicked = false;
				buttonAMClicked = false;
				button1.setStyle(styleNoClick);
				button2.setStyle(styleNoClick);
				button3.setStyle(styleNoClick);
				button4.setStyle(styleNoClick);
				button5.setStyle(styleNoClick);
				button6.setStyle(styleNoClick);
				button7.setStyle(styleNoClick);
				button8.setStyle(styleNoClick);
				button9.setStyle(styleNoClick);
				button10.setStyle(styleNoClick);
				button11.setStyle(styleNoClick);
				button12.setStyle(styleNoClick);

				/*
				 * New Select (I do not know if I need that, maybe add in on the list only)
				 */
				try {
					dateTimeProviderDAO.selectAllProvidersDates(UserLogged.userId);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				/*
				 * Get all the buttons back and visible
				 */
				datePicker.getEditor().clear();
				datePicker.setVisible(true);
				buttonOK.setVisible(false);
				circle.setVisible(true);
			}
		});
		/*
		 * Keep going
		 */
		pause.play();

	}

	/*
	 * View booking button
	 */
	@FXML
	protected void handleViewBookingButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.PROVIDER_VIEW_CUSTUMER_BOOKING_SCREEN);
	}
	
	/*
	 * Logout booking button
	 */
	@FXML
	protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.LOGIN_SCREEN);
	}

	/*
	 * Rremove booking button
	 */
	@FXML
	protected void handleRemoveBookingButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.PROVIDER_REMOVE_DATE_SCREEN);
	}

}