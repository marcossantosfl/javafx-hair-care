package service;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/*
 * Class fade effect added to be used on all transitions screens and loading screen
 */
public class Transition {

    public static FadeTransition fade = new FadeTransition();

    public static void FadeTransitionEffect(Parent root, int millis) {

        /*
		 * Transition (using millisecond)
         */
        fade.setDuration(Duration.millis(millis));
        fade.setFromValue(10);
        fade.setToValue(0.1);
        fade.setCycleCount(4000);
        /*
		 * if set true, will be fade in and out
         */
        fade.setAutoReverse(false);
        fade.setNode(root);
        /*
		 * Play the transition
         */
        fade.play();
    }

    /*
	 * Pause transition function and set new elements
	 * Shows the spinning and next screen selected 
     */
    public static void PauseTransitionAndSetElement(final BorderPane borderA, final BorderPane borderB, int seconds) {
        PauseTransition pause = new PauseTransition(Duration.seconds(seconds));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                borderA.getChildren().setAll(borderB);
            }
        });
        pause.play();
    }

    /*
	 * Pause transition function
     */
    public static void PauseTransitionOnly() {
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.play();
    }

}
