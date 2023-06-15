package view;

import java.io.IOException;

import controller.MainController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import service.PathFXMLService;

public class SplashScreen extends Application {

	@Override
	public void start(final Stage primaryStage) throws IOException {

		/*
		 * Reading the loading.fxml It contains the splash screen
		 */
		Parent root = FXMLLoader.load(getClass().getResource(PathFXMLService.SPLASH_SCREEN));
		Scene scene = new Scene(root);
		/*
		 * Removed buttons and disabled resizable by using mouse
		 */
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setTitle("Easy Hair Care");
		primaryStage.setResizable(false);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("scissors.png"));
		/*
		 * Called fade effect until getting onto login screen
		 */

		// I think after compiling the effect gets awful (run directly on eclipse or
		// netbeans works okay)
		// Transition.FadeTransitionEffect(root, 4000);

		/*
		 * Show the splash screen
		 */
		primaryStage.show();

		/*
		 * Added a pause transition to switch between (maybe update in the future to be
		 * smooth)
		 */
		PauseTransition pause = new PauseTransition(Duration.seconds(5));
		pause.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				/*
				 * Loading the login.fxml, which contains the login interface Set a controller
				 */
				Parent root = null;
				FXMLLoader loader = new FXMLLoader();
				loader.setController(new MainController());
				try {
					root = FXMLLoader.load(getClass().getResource(PathFXMLService.MAIN_SCREEN));
				} catch (IOException e) {
					e.printStackTrace();
				}

				Scene scene1 = new Scene(root);

				Stage secondStage = new Stage();

				secondStage.initStyle(StageStyle.TRANSPARENT);
				scene1.setFill(Color.TRANSPARENT);
				secondStage.setTitle("Easy Hair Care");
				secondStage.setResizable(false);
				secondStage.setScene(scene1);
				secondStage.getIcons().add(new Image("scissors.png"));

				/*
				 * Hide splash screen and show login screen
				 */
				primaryStage.hide();
				secondStage.show();

			}
		});
		/*
		 * Keep going
		 */
		pause.play();
	}

}
