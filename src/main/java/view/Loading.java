package view;

import java.io.IOException;

import controller.LoginController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import service.FXMLService;
import service.TransitionService;

public class Loading extends Application {

    @Override
    public void start(final Stage primaryStage) throws IOException {

        /*
		 * Reading the loading.fxml It contains the splash screen
         */
        Parent root = FXMLLoader.load(getClass().getResource(FXMLService.LOADING_SCREEN));
        Scene scene = new Scene(root);
        /*
		 * Removed buttons and disabled resizable by using mouse
         */
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Easy Hair Care");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        /*
		 * Called fade effect until getting onto login screen
         */
        
        //I think after compiling the effect gets awful (run directly on eclipse or netbeans works okay)
        //Transition.FadeTransitionEffect(root, 4000);

        /*
		 * Show the splash screen
         */
        primaryStage.show();

        /*
		 * Added a pause transition to switch between (maybe update in the future to be
		 * smooth)
         */
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                /*
				 * Loading the login.fxml, which contains the login interface
				 * Set a controller
                 */
                Parent root = null;
                FXMLLoader loader = new FXMLLoader();
                loader.setController(new LoginController());
                try {
                    root = FXMLLoader.load(getClass().getResource(FXMLService.LOGIN_SCREEN));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene scene1 = new Scene(root);

                Stage secondStage = new Stage();

                secondStage.setTitle("Easy Hair Care");
                secondStage.setResizable(false);
                secondStage.setScene(scene1);


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
