package controller;
import animatefx.animation.Bounce;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class SpinnerController extends PublicClassController {

	/*
	 * FXML variables
	 */
	@FXML
	private Circle circle1;
	
	@FXML
	private Circle circle2;
	
	@FXML
	private Circle circle3;

	@FXML
	protected void initialize(){
		new Bounce(circle1).setCycleCount(9999999).setDelay(Duration.millis(500)).play();
		new Bounce(circle2).setCycleCount(9999999).setDelay(Duration.millis(1000)).play();
		new Bounce(circle3).setCycleCount(9999999).setDelay(Duration.millis(1100)).play();
	}
	
}

