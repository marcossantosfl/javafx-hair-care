package controller.admin;

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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.UserLogged;
import service.PathFXMLService;
import service.GeneralAnimationService;

public class AdminViewProviderController extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private VBox vbox;

	@FXML
	private JFXButton acceptButton;

	@FXML
	private JFXButton denyButton;

	@FXML
	private ComboBox<String> comboBox;

	@FXML
	private ImageView imageView;

	/*
	 * ObservableList for combobox
	 */
	private ObservableList<String> options = FXCollections.observableArrayList();

	@FXML
	protected void initialize() throws SQLException {

		/*
		 * Rotation effect
		 */
		GeneralAnimationService.Rotation(imageView, 3000);

		/*
		 * Select all providers from sql
		 */
		providerDAOService.selectAllProviders();

		/*
		 * Clear options for combobox
		 */
		options.clear();

		/*
		 * Set combobox value as a null
		 */
		comboBox.valueProperty().set(null);

		/*
		 * Message if there is no providers to accept or deny
		 */
		if (UserLogged.adminAcceptProviders.size() < 1) {
			comboBox.setVisible(false);
			return;
		}

		/*
		 * Add up all the providers that were got by sql
		 */
		for (int i = 0; i < UserLogged.adminAcceptProviders.size(); i++) {
			options.add(i, UserLogged.adminAcceptProviders.get(i).getName());
		}

		/*
		 * Add their names on combobox
		 */
		comboBox.setItems(options);

		/*
		 * Shows the button if there is a provider selected
		 */
		comboBox.valueProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (comboBox.getValue() != null) {
					acceptButton.setVisible(true);
					denyButton.setVisible(true);

				} else {
					acceptButton.setVisible(false);
					denyButton.setVisible(false);
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
	 * Accept button
	 */
	@FXML
	protected void handleAcceptButtonAction(ActionEvent event) throws IOException, SQLException {

		if (comboBox.getValue() != null) {

			for (int i = 0; i < UserLogged.adminAcceptProviders.size(); i++) {
				if (comboBox.getValue() == options.get(i)) {
					if (providerDAOService.updateProvider(UserLogged.adminAcceptProviders.get(i).getId()) == true) {
						initialize();
					}
				}
			}
		}
	}

	/*
	 * Deny button
	 */
	@FXML
	protected void handleDenyButtonAction(ActionEvent event) throws IOException, SQLException {
		if (comboBox.getValue() != null) {
			for (int i = 0; i < UserLogged.adminAcceptProviders.size(); i++) {
				if (comboBox.getValue() == options.get(i)) {
					if (providerDAOService.removeProvider(UserLogged.adminAcceptProviders.get(i).getId()) == true) {
						initialize();
					}
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

}
