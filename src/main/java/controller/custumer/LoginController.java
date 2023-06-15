package controller.custumer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;

import controller.PublicClassController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import model.UserLogged;
import service.ConnectSQLService;
import service.PathFXMLService;

public class LoginController extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private VBox vbox;
	
	@FXML
	private TextField emailField;

	@FXML
	private JFXButton signIn;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Hyperlink hyperlink;

	@FXML
	private Label emailLabel;

	@FXML
	private Label accountLabel;

	@FXML
	private ImageView imageView;

	private boolean invalidEmail;

	@FXML
	protected void initialize() throws SQLException, IOException, InterruptedException {

		/*
		 * Check if database exists or connection is not null
		 */
		if (ConnectSQLService.getDBConnection() == null) {
			LoadFXMLWithSpinner(vbox,PathFXMLService.ERROR_SQL_SCREEN);
		}

		/*
		 * Email validation
		 */
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
		/*
		 * Email validation
		 */
		emailField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (emailField.getLength() >= MAX_CHARACTER) {
					k.consume();
				}
			}
		});
		/*
		 * Password validation
		 */
		passwordField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (passwordField.getLength() >= MAX_CHARACTER) {
					k.consume();
				}
			}
		});

	}

	/*
	 * Sign in button
	 */
	@FXML
	protected void handleSignInButtonAction(ActionEvent event) throws IOException, SQLException {
		/*
		 * All validations
		 */
		boolean error = false;

		if (emailField.getLength() < MIN_CHARACTER || invalidEmail == true) {
			this.emailLabel.setVisible(true);
			error = true;
		}
		if (passwordField.getLength() < MIN_CHARACTER) {
			this.accountLabel.setVisible(true);
			error = true;
		}

		if (error == false) {

			signIn.setDisable(true);

			/*
			 * Check if userExists sql
			 */
			boolean userExists = accountDAOService.AccountExists(emailField.getText(), passwordField.getText());

			this.accountLabel.setVisible(!userExists);

			if (userExists) {
				/*
				 * Load screens according to user Role
				 */
				if (UserLogged.userRole == 0) {
					LoadFXMLWithSpinner(vbox,PathFXMLService.WHICHAREA_SCREEN);
				} else if (UserLogged.userRole == 1) {

					if (UserLogged.userAccepted == 0) {
						LoadFXMLWithSpinner(vbox,PathFXMLService.AWAITING_SCREEN);
					} else {
						LoadFXMLWithSpinner(vbox,PathFXMLService.PROVIDER_CUSTUMER_BOOKING_SCREEN);
					}

				} else if (UserLogged.userRole == 2) {
					LoadFXMLWithSpinner(vbox,PathFXMLService.ADMIN_VIEW_PROVIDER_SCREEN);
				}

			} else {
				signIn.setDisable(false);
			}
		} else {
			signIn.setDisable(false);
		}

	}

	/*
	 * Sign up button
	 */
	@FXML
	protected void handleCreateButtonAction(ActionEvent event) throws IOException {

		LoadFXMLWithSpinner(vbox,PathFXMLService.SIGNUP_SCREEN);

	}

}
