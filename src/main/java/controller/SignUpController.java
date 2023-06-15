package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import model.Account;
import model.AccountLogged;
import service.AccountDAOService;
import service.FXMLService;
import service.TransitionService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SignUpController extends GeneralController {

	/*
	 * FXML annotation to be able to indetify the id's on fxml
	 */
	@FXML
	private BorderPane borderSignUp;

	@FXML
	private JFXButton signUpButton;

	@FXML
	private TextField nameField;

	@FXML
	private TextField emailField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField phoneField;

	@FXML
	private JFXToggleButton toggleField;

	@FXML
	private ComboBox<String> comboField;

	@FXML
	private Label nameLabel;

	@FXML
	private Label emailLabel;

	private boolean invalidEmail;

	@FXML
	private Label numberLabel;

	@FXML
	private Label passwordLabel;

	@FXML
	private Label locationLabel;

	@FXML
	private Label AccountExistsLabel;

	/*
	 * Initialize added to check some changes on fields and set warning alert
	 */
	@FXML
	private void initialize() {
		nameField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (nameField.getLength() >= MIN_CHARACTER && nameField.getLength() <= MAX_CHARACTER) {
					nameLabel.setVisible(false);
				} else {
					nameLabel.setVisible(true);
				}

			}
		});
		nameField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (nameField.getLength() >= MAX_CHARACTER) {
					k.consume();
				}
			}
		});
		emailField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				pattern = Pattern.compile("^(.+)@(.+)$");
				matcher = pattern.matcher(emailField.getText());

				if ((emailField.getLength() <= MIN_CHARACTER && emailField.getLength() <= MAX_CHARACTER)
						|| !matcher.matches()) {
					invalidEmail = true;
					emailLabel.setVisible(true);
				} else {
					invalidEmail = false;
					emailLabel.setVisible(false);
				}

			}
		});
		emailField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (emailField.getLength() >= MAX_CHARACTER) {
					k.consume();
				}
			}
		});
		phoneField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				char ar[] = k.getCharacter().toCharArray();
				char ch = ar[k.getCharacter().toCharArray().length - 1];
				if ((!(ch >= '0' && ch <= '9')) || phoneField.getLength() >= MIN_CHARACTER) {
					k.consume();
				}
			}
		});
		phoneField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (phoneField.getLength() >= MIN_CHARACTER && phoneField.getLength() <= MAX_CHARACTER) {
					numberLabel.setVisible(false);
				} else {
					numberLabel.setVisible(true);
				}

			}
		});
		passwordField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				if (passwordField.getLength() >= MIN_CHARACTER && passwordField.getLength() <= MAX_CHARACTER) {
					passwordLabel.setVisible(false);
				} else {
					passwordLabel.setVisible(true);
				}

			}
		});
		passwordField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (passwordField.getLength() >= MAX_CHARACTER) {
					k.consume();
				}
			}
		});
		comboField.valueProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (comboField.getValue().equalsIgnoreCase("Location")) {
					locationLabel.setVisible(true);
				} else {
					locationLabel.setVisible(false);
				}

			}
		});
	}

	/*
	 * Set warning to the user select the location
	 */
	@FXML
	protected void handleToggleButtonAction(ActionEvent event) throws IOException {

		if (toggleField.isSelected()) {

			ObservableList<String> options = FXCollections.observableArrayList("DUBLIN SOUTH", "DUBLIN NORTH");

			comboField.setValue("Location");
			comboField.setItems(options);

			comboField.setVisible(true);
			signUpButton.relocate(81, 357);

		} else {
			this.locationLabel.setVisible(false);
			comboField.setVisible(false);
			signUpButton.relocate(83, 322);
		}

	}

	/*
	 * Set warning to the user if the fields are empty
	 */
	@FXML
	protected void handleSignUpButtonAction(ActionEvent event) throws IOException, SQLException {

		boolean error = false;

		int location = -1;

		if (nameField.getLength() < MIN_CHARACTER) {
			this.nameLabel.setVisible(true);
			error = true;
		}
		if (emailField.getLength() < MIN_CHARACTER || invalidEmail == true) {
			this.emailLabel.setVisible(true);
			error = true;
		}
		if (phoneField.getLength() <= 9) {
			this.numberLabel.setVisible(true);
			error = true;
		}
		if (passwordField.getLength() < MIN_CHARACTER) {
			this.passwordLabel.setVisible(true);
			error = true;
		}
		if (toggleField.isSelected()) {
			if (comboField.getValue() == null) {
				this.locationLabel.setVisible(true);
				error = true;
			} else {
				if (comboField.getValue().equalsIgnoreCase("Location")) {

					this.locationLabel.setVisible(true);
					error = true;
				} else {
					if (comboField.getValue().equalsIgnoreCase("DUBLIN SOUTH")) {
						location = 0;
					} else {
						location = 1;
					}
				}
			}
		}

		if (error == false) {
			Account account = new Account();
			account.setName(nameField.getText());
			account.setEmail(emailField.getText());
			account.setNumber(phoneField.getText());
			account.setPassword(passwordField.getText());

			int key = accountDAOService.saveAccount(account, toggleField.isSelected(), location);

			this.AccountExistsLabel.setVisible(key == 0 ? true : false);

			signUpButton.setDisable(true);

			if (key > 0) {
				
				AccountLogged.accountId = key;
				
				account.setId(key);
				accountDAOService.saveAccountRole(account, toggleField.isSelected() ? 1 : 0);

				BorderPane border = FXMLLoaderInit(borderSignUp, FXMLService.TRANSITION_SCREEN, true);

				/*
				 * Check later
				 */
				if (toggleField.isSelected()) {
					final BorderPane borderWaiting = FXMLLoaderInit(border, FXMLService.WAITING_SCREEN, false);
					/*
					 * Pause transition while change all the components FXML
					 */
					TransitionService.PauseTransitionAndSetElement(border, borderWaiting, 1);
				} else {
					final BorderPane borderLocation = FXMLLoaderInit(border, FXMLService.LOCATION_SCREEN, false);

					/*
					 * Pause transition while change all the components FXML
					 */
					TransitionService.PauseTransitionAndSetElement(border, borderLocation, 1);
				}

			} else {
				signUpButton.setDisable(false);
			}

		}

	}

}
