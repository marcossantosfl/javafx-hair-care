package service;


import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/*
 * GeneralAnimation (beta, just to try some effects and animations)
 */
public class GeneralAnimationService {
	
	static FadeTransition fade = new FadeTransition();
	static RotateTransition rotate = new RotateTransition();

	public static void Rotation(Node node, int millis) {
		RotateTransition rotate = new RotateTransition(Duration.millis(millis));
		rotate.setByAngle(10f);
		rotate.setCycleCount(1000);
		rotate.setAutoReverse(true);
		rotate.setNode(node);
		rotate.play();
	}
	
	public static void FadeReverse(Node node, int millis) {
		fade = new FadeTransition(Duration.millis(millis));
		fade.setFromValue(0.0);  
        fade.setToValue(1.0); 
        fade.setCycleCount(1);
        fade.setNode(node);  
        fade.play();
	}
	
	public static void Fade(Node node, int millis) {
		fade = new FadeTransition(Duration.millis(millis));
		fade.setFromValue(1.0);  
        fade.setToValue(0.0); 
        fade.setCycleCount(1);
        fade.setNode(node);  
        fade.play();
	}

	public static void TranslationHourButton(Node node, int millis) {

		TranslateTransition translateTransition = new TranslateTransition();
		
		translateTransition.setDuration(Duration.millis(millis));
		translateTransition.setNode(node);
		translateTransition.setToY(500);
		translateTransition.setCycleCount(1);
		translateTransition.setAutoReverse(false);
		translateTransition.play();
		
		RotateTransition rotate = new RotateTransition();
		
		rotate.setDuration(Duration.millis(millis));
		rotate.setNode(node);
		rotate.setByAngle(360);
		rotate.setCycleCount(1);
		rotate.setAutoReverse(false);
		rotate.play();
	}

	public static void TranslationBack(Node node, int millis) {

		TranslateTransition translateTransition = new TranslateTransition();

		translateTransition.setDuration(Duration.millis(millis));
		translateTransition.setNode(node);
		translateTransition.setToY(0);
		translateTransition.setCycleCount(1);
		translateTransition.setAutoReverse(false);
		translateTransition.play();
	}
	
	public static void TranslationLabel(Node node, int millis) {

		TranslateTransition translateTransition = new TranslateTransition();
		
		translateTransition.setDuration(Duration.millis(millis));
		translateTransition.setNode(node);
		translateTransition.setToY(500);
		translateTransition.setCycleCount(999999999);
		translateTransition.setAutoReverse(false);
		translateTransition.play();
	}

}
