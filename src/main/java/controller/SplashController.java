package controller;

import java.io.IOException;
import java.sql.SQLException;

import animatefx.animation.Bounce;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import service.GeneralAnimationService;

public class SplashController extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private ImageView imageView;
	
	@FXML
	private Circle circle1;
	
	@FXML
	private Circle circle2;
	
	@FXML
	private Circle circle3;

	@FXML
	protected void initialize() throws SQLException, IOException, InterruptedException {
		/*
		 * Rotation effect
		 */
		GeneralAnimationService.Rotation(imageView, 3000);
		
		new Bounce(circle1).setCycleCount(9999999).setDelay(Duration.millis(500)).play();
		new Bounce(circle2).setCycleCount(9999999).setDelay(Duration.millis(1000)).play();
		new Bounce(circle3).setCycleCount(9999999).setDelay(Duration.millis(1100)).play();
		
		GeneralAnimationService.Fade(anchorPane, 5400);
	}
	
}