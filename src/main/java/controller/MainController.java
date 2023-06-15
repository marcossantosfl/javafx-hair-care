package controller;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.GeneralAnimationService;
import service.PathFXMLService;

public class MainController extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private BorderPane border;

	@FXML
	private VBox vbox1;

	@FXML
	private VBox vbox2;

	@FXML
	private ImageView imageView;

	@FXML
	private JFXButton button1;

	@FXML
	private JFXButton button2;

	/*
	 * ImageThread, changes the image
	 */
	public class ImageThread extends Thread {

		public void run() {
			while (true) {
				try {
					for (int i = 1; i <= 14; i++) {
						Thread.sleep(8000L);
						if (i > 9) {
							imageView.setImage(new Image("" + i + ".png"));
						} else {
							imageView.setImage(new Image("0" + i + ".png"));
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	protected void initialize() throws SQLException, IOException, InterruptedException {

		/*
		 * Thread image change
		 */
		if (!threadStarted) {
			ImageThread imageThread = new ImageThread();
			imageThread.start();
		}

		threadStarted = true;

		/*
		 * Rotation effect
		 */
		GeneralAnimationService.Rotation(imageView, 3000);

		LoadFXMLWithSpinner(vbox2, PathFXMLService.LOGIN_SCREEN);
	}

	@FXML
	protected void handleMinButtonAction(ActionEvent event) {
		Stage stage = (Stage) border.getScene().getWindow();
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	protected void handleCloseButtonAction(ActionEvent event) {
		System.exit(1);
	}
}
