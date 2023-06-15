package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import model.Account;
import model.AccountLogged;
import service.DatabaseService;
import service.FXMLService;
import service.TransitionService;

public class LoginController extends GeneralController {

    /*
	 * FXML (identifier) created to be manipulated (check FXML file, it contains all the
	 * id's) and it has to be the same name and same type of control or custom
	 * control
     */
    @FXML
    private TextField emailField;

    @FXML
    private JFXButton connectButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Hyperlink hyperlink;

    @FXML
    private BorderPane borderLogin;
    
	@FXML
	private Label emailLabel;
	
	@FXML
	private Label accountLabel;

	private boolean invalidEmail;

    /*
	 * Initialize check if database exists or connection is not null (change to errordatabase.fxml if it is not correct)
	 * 
     */
    @FXML
    protected void initialize() throws SQLException, IOException, InterruptedException {

        if (DatabaseService.getDBConnection() == null) {
            BorderPane borderTransitionError = FXMLLoaderInit(borderLogin, FXMLService.ERROR_DATABASE_SCREEN, true);

            TransitionService.PauseTransitionAndSetElement(borderLogin, borderTransitionError, 2);
        }
        
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
		passwordField.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (passwordField.getLength() >= MAX_CHARACTER) {
					k.consume();
				}
			}
		});

    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event)  throws IOException, SQLException {

		boolean error = false;

		if (emailField.getLength() < MIN_CHARACTER || invalidEmail == true) {
			this.emailLabel.setVisible(true);
			error = true;
		}
		if (passwordField.getLength() < MIN_CHARACTER) {
			error = true;
		}

		if (error == false) {
			
			connectButton.setDisable(true);
			
			boolean accountExists = accountDAOService.AccountExists(emailField.getText(), passwordField.getText());
			
			this.accountLabel.setVisible(!accountExists);
			
			if(accountExists)
			{
				if(AccountLogged.accountRole == 0)
				{
			        BorderPane border = FXMLLoaderInit(borderLogin, FXMLService.TRANSITION_SCREEN, true);
			        
			        /*
			         * Check later 
			         */
			        final BorderPane borderSignup = FXMLLoaderInit(border, FXMLService.LOCATION_SCREEN, false);
			        

			        /*
					 * Pause transition while change all the components FXML
			         */
			        TransitionService.PauseTransitionAndSetElement(border, borderSignup, 1);
				}
				else if(AccountLogged.accountRole == 1)
				{
					
					if(AccountLogged.accountAccepted == 0)
					{
				        BorderPane border = FXMLLoaderInit(borderLogin, FXMLService.TRANSITION_SCREEN, true);
				        
				        /*
				         * Check later 
				         */
				        final BorderPane borderSignup = FXMLLoaderInit(border, FXMLService.WAITING_SCREEN, false);
				        

				        /*
						 * Pause transition while change all the components FXML
				         */
				        TransitionService.PauseTransitionAndSetElement(border, borderSignup, 1);
					}
					else
					{
				        BorderPane border = FXMLLoaderInit(borderLogin, FXMLService.TRANSITION_SCREEN, true);
				        
				        /*
				         * Check later 
				         */
				        final BorderPane borderSignup = FXMLLoaderInit(border, FXMLService.DATETIME_ADMIN_SCREEN, false);
				        

				        /*
						 * Pause transition while change all the components FXML
				         */
				        TransitionService.PauseTransitionAndSetElement(border, borderSignup, 1);
					}
			        
				}
			}
			else
			{
				connectButton.setDisable(false);
			}
			
			
		}

	}

    /*
	 * Handle button method created to be used when somebody clicks on it and goes
	 * onto a signup screen
     */
    @FXML
    protected void handleCreateButtonAction(ActionEvent event) throws IOException {

        BorderPane border = FXMLLoaderInit(borderLogin, FXMLService.TRANSITION_SCREEN, true);
        
        /*
         * Check later 
         */
        final BorderPane borderSignup = FXMLLoaderInit(border, FXMLService.SIGNUP_SCREEN, false);
        

        /*
		 * Pause transition while change all the components FXML
         */
        TransitionService.PauseTransitionAndSetElement(border, borderSignup, 1);

    }

}
