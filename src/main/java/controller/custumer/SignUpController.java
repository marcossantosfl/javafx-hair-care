package controller.custumer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;

import controller.PublicClassController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.User;
import model.UserLogged;
import service.PathFXMLService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SignUpController extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private VBox vbox;

	@FXML
	private JFXButton signUp;

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

	@FXML
	private void initialize() {
		/*
		 * All the valitions (name, email, password, combobox, toggle)
		 */
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
		comboField.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
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
	 * Set warning to the user select the location
	 */
	@FXML
	protected void handleToggleButtonAction(ActionEvent event) throws IOException {

		/*
		 * Relocate the button if select the toogle as a provider
		 */
		if (toggleField.isSelected()) {

			ObservableList<String> options = FXCollections.observableArrayList("DUBLIN SOUTH", "DUBLIN NORTH");

			comboField.setValue("Location");
			comboField.setItems(options);

			comboField.setVisible(true);
			signUp.relocate(81, 357);

		} else {
			this.locationLabel.setVisible(false);
			comboField.setVisible(false);
			signUp.relocate(83, 322);
		}

	}

	/*
	 * Sign in button
	 */
	@FXML
	protected void handleSignInButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox,PathFXMLService.LOGIN_SCREEN);
	}

	/*
	 * Sign up
	 */
	@FXML
	protected void handleSignUpButtonAction(ActionEvent event) throws IOException, SQLException {

		boolean error = false;

		int location = -1;

		/*
		 * All validations
		 */
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
			User account = new User();
			account.setName(nameField.getText());
			account.setEmail(emailField.getText());
			account.setNumber(phoneField.getText());
			/*
			 * Password hashed/encrypted/salted
			 */
			account.setPassword(new BCryptPasswordEncoder().encode(passwordField.getText()));

			/*
			 * Insert into sql and get key generated
			 */
			int key = accountDAOService.newAccount(account, toggleField.isSelected(), location);

			this.AccountExistsLabel.setVisible(key == 0 ? true : false);

			signUp.setDisable(true);

			if (key > 0) {

				UserLogged.userId = key;

				account.setId(key);
				int i = accountDAOService.saveAccountRole(account, toggleField.isSelected() ? 1 : 0);

				if (toggleField.isSelected()) {
					LoadFXMLWithSpinner(vbox,PathFXMLService.AWAITING_SCREEN);
				} else {
					LoadFXMLWithSpinner(vbox,PathFXMLService.WHICHAREA_SCREEN);
				}
			}
		} else {
			signUp.setDisable(false);
		}
	}
}
