package controller.custumer;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;

import controller.PublicClassController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

public class ViewProviderController extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private VBox vbox;

	@FXML
	private ComboBox<String> comboBox;

	@FXML
	private ImageView star1;

	@FXML
	private ImageView star2;

	@FXML
	private ImageView star3;

	@FXML
	private ImageView star4;

	@FXML
	private ImageView star5;

	@FXML
	private JFXButton buttonChoose;
	
	@FXML
	private Label labelNone;

	/*
	 * ObservableList options for combobox
	 */
	private ObservableList<String> options = FXCollections.observableArrayList();

	@FXML
	protected void initialize() throws SQLException, IOException, InterruptedException {
		/*
		 * Clear otpions
		 */
		options.clear();

		comboBox.valueProperty().set(null);

		/*
		 * Get all the providers list by location
		 */
		providerDAOService.selectAllProviders(UserLogged.userLocation);

		for (int i = 0; i < UserLogged.providers.size(); i++) {
			options.add(UserLogged.providers.get(i).getName());
		}

		/*
		 * Message if there is no provider
		 */
		if (UserLogged.providers.size() < 1) {
			labelNone.setVisible(true);
			comboBox.setVisible(false);
			return;
		}

		comboBox.setItems(options);

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
	 * Combobox action (set stars according to provider stars)
	 */
	@FXML
	protected void handleComboBoxAction(ActionEvent event) throws SQLException {
		this.star1.setVisible(false);
		this.star2.setVisible(false);
		this.star3.setVisible(false);
		this.star4.setVisible(false);
		this.star5.setVisible(false);

		for (int i = 0; i < UserLogged.providers.size(); i++) {
			if (comboBox.getValue().equalsIgnoreCase(UserLogged.providers.get(i).getName())) {
				buttonChoose.setVisible(true);
				
				int totalStar = reviewDaoService.selectAllStars(UserLogged.providers.get(i).getIdProvider());
				
				if (totalStar == 1) {
					this.star1.setVisible(true);
				}
				if (totalStar == 2) {
					this.star1.setVisible(true);
					this.star2.setVisible(true);
				}
				if (totalStar == 3) {
					this.star1.setVisible(true);
					this.star2.setVisible(true);
					this.star3.setVisible(true);
				}
				if (totalStar == 4) {
					this.star1.setVisible(true);
					this.star2.setVisible(true);
					this.star3.setVisible(true);
					this.star4.setVisible(true);
				}
				if (totalStar == 5) {
					this.star1.setVisible(true);
					this.star2.setVisible(true);
					this.star3.setVisible(true);
					this.star4.setVisible(true);
					this.star5.setVisible(true);
				}
			}
		}

	}

	/*
	 * Choose button
	 */
	@FXML
	protected void handleChooseButtonAction(ActionEvent event) throws IOException {

		for (int i = 0; i < UserLogged.providers.size(); i++) {
			if (comboBox.getValue().equalsIgnoreCase(UserLogged.providers.get(i).getName())) {
				UserLogged.idProvider = UserLogged.providers.get(i).getIdProvider();
			}
		}
		LoadFXMLWithSpinner(vbox,PathFXMLService.NEW_BOOKING_SCREEN);
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