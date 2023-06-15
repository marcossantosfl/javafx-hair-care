package controller.custumer;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;

import controller.PublicClassController;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.Duration;
import model.UserLogged;
import service.PathFXMLService;

public class ReviewController extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private VBox vbox;

	@FXML
	private JFXButton submit;

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

	private boolean star1Clicked = false;
	private boolean star2Clicked = false;
	private boolean star3Clicked = false;
	private boolean star4Clicked = false;
	private boolean star5Clicked = false;

	private int selected = 0;

	private TranslateTransition translateStar1 = new TranslateTransition();
	private TranslateTransition translateStar2 = new TranslateTransition();
	private TranslateTransition translateStar3 = new TranslateTransition();
	private TranslateTransition translateStar4 = new TranslateTransition();
	private TranslateTransition translateStar5 = new TranslateTransition();

	/*
	 * ObservableList for combobox
	 */
	private ObservableList<String> options = FXCollections.observableArrayList();

	@FXML
	protected void initialize() throws SQLException {

		/*
		 * Select all reviews
		 */
		reviewDaoService.selectAllReviewIncompleted(UserLogged.userId);

		/*
		 * Clear options
		 */
		options.clear();

		/*
		 * Set combobox as a null
		 */
		comboBox.valueProperty().set(null);

		/*
		 * Build a string with name and date/time
		 */
		for (int i = 0; i < UserLogged.reviews.size(); i++) {
			if (UserLogged.reviews.get(i).getStarGiven() == 0) {
				options.add(i, UserLogged.reviews.get(i).getProviderName());
			}
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
					star1.setVisible(true);
					star2.setVisible(true);
					star3.setVisible(true);
					star4.setVisible(true);
					star5.setVisible(true);

				} else {
					star1.setVisible(false);
					star2.setVisible(false);
					star3.setVisible(false);
					star4.setVisible(false);
					star5.setVisible(false);
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

		star1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (star1Clicked == false) {
					star1.setImage(new Image("star.png"));
					translateStar1.setDuration(Duration.millis(500));
					translateStar1.setNode(star1);
					translateStar1.setToY(-20);
					translateStar1.setCycleCount(999999999);
					translateStar1.setAutoReverse(true);
					translateStar1.play();
					star1Clicked = true;
					selected++;
				} else {
					star1.setImage(new Image("star_grey.png"));
					translateStar1.stop();
					star1Clicked = false;
					selected--;
				}
				if (selected > 0) {
					submit.setVisible(true);
				} else {
					submit.setVisible(false);
				}
				event.consume();
			}
		});
		star2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (star2Clicked == false) {
					star2.setImage(new Image("star.png"));
					translateStar2.setDuration(Duration.millis(500));
					translateStar2.setNode(star2);
					translateStar2.setToY(-20);
					translateStar2.setCycleCount(999999999);
					translateStar2.setAutoReverse(true);
					translateStar2.play();
					star2Clicked = true;
					selected++;
				} else {
					star2.setImage(new Image("star_grey.png"));
					translateStar2.stop();
					star2Clicked = false;
					selected--;
				}
				if (selected > 0) {
					submit.setVisible(true);
				} else {
					submit.setVisible(false);
				}
				event.consume();
			}
		});
		star3.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (star3Clicked == false) {
					star3.setImage(new Image("star.png"));
					translateStar3.setDuration(Duration.millis(500));
					translateStar3.setNode(star3);
					translateStar3.setToY(-20);
					translateStar3.setCycleCount(999999999);
					translateStar3.setAutoReverse(true);
					translateStar3.play();
					star3Clicked = true;
					selected++;
				} else {
					star3.setImage(new Image("star_grey.png"));
					translateStar3.stop();
					star3Clicked = false;
					selected--;
				}
				if (selected > 0) {
					submit.setVisible(true);
				} else {
					submit.setVisible(false);
				}
				event.consume();
			}
		});
		star4.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (star4Clicked == false) {
					star4.setImage(new Image("star.png"));
					translateStar4.setDuration(Duration.millis(500));
					translateStar4.setNode(star4);
					translateStar4.setToY(-20);
					translateStar4.setCycleCount(999999999);
					translateStar4.setAutoReverse(true);
					translateStar4.play();
					star4Clicked = true;
					selected++;
				} else {
					star4.setImage(new Image("star_grey.png"));
					translateStar4.stop();
					star4Clicked = false;
					selected--;
				}
				if (selected > 0) {
					submit.setVisible(true);
				} else {
					submit.setVisible(false);
				}
				event.consume();
			}
		});
		star5.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (star5Clicked == false) {
					star5.setImage(new Image("star.png"));
					translateStar5.setDuration(Duration.millis(500));
					translateStar5.setNode(star5);
					translateStar5.setToY(-20);
					translateStar5.setCycleCount(999999999);
					translateStar5.setAutoReverse(true);
					translateStar5.play();
					star5Clicked = true;
					selected++;
				} else {
					star5.setImage(new Image("star_grey.png"));
					translateStar5.stop();
					star5Clicked = false;
					selected--;
				}

				if (selected > 0) {
					submit.setVisible(true);
				} else {
					submit.setVisible(false);
				}

				event.consume();
			}
		});

	}

	/*
	 * Submit button
	 */
	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException, SQLException {
		for (int i = 0; i < UserLogged.reviews.size(); i++) {
			if (options.get(i).equalsIgnoreCase(comboBox.getValue())) {
				dateTimeProviderDAO.finishedAndCloseReview(UserLogged.reviews.get(i).getIdDate());
				reviewDaoService.submitReview(UserLogged.reviews.get(i).getId(), selected);
				UserLogged.reviews.remove(i);
				options.remove(i);
			}
		}

	    LoadFXMLWithSpinner(vbox, PathFXMLService.CANCEL_BOOKING_SCREEN);
	}

	/*
	 * Logout button
	 */
	@FXML
	protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox, PathFXMLService.LOGIN_SCREEN);
	}

	/*
	 * View bookings button
	 */
	@FXML
	protected void handleViewBookingButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox, PathFXMLService.CANCEL_BOOKING_SCREEN);
	}

	/*
	 * New booking button
	 */
	@FXML
	protected void handleNewBookingButtonAction(ActionEvent event) throws IOException {
		LoadFXMLWithSpinner(vbox, PathFXMLService.WHICHAREA_SCREEN);
	}

}
