package main;

import javafx.application.Application;
import view.SplashScreen;

public class EasyCareMain {

    public static void main(String[] args) {

        new SplashScreen();
        /*
		 * Interface loading (first initial splash screen)
         */
        Application.launch(SplashScreen.class, args);
    }

}
