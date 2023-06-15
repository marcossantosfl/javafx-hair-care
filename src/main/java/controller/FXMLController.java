package controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/*
 * Class FXML Controller created
 */
public class FXMLController {

	protected BorderPane FXMLLoaderInit(BorderPane border, String fxmlService, boolean setAll) throws IOException {
		BorderPane border1 = FXMLLoader.load(getClass().getResource(fxmlService));
		if (setAll == true) {
			border.getChildren().setAll(border1);
		}
		return border1;
	}

}
