package controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import service.AccountDAOService;
import service.DateTimeProviderDAO;
import service.ProviderDAOService;

/*
 * Class FXML Controller created
 */
public class GeneralController {

	protected BorderPane FXMLLoaderInit(BorderPane border, String fxmlService, boolean setAll) throws IOException {
		BorderPane border1 = FXMLLoader.load(getClass().getResource(fxmlService));
		if (setAll == true) {
			border.getChildren().setAll(border1);
		}
		return border1;
	}
	
	protected final int MIN_CHARACTER = 10;
	protected final int MAX_CHARACTER = 35;

	/*
	 * Pattern email (validation)
	 */
	protected Pattern pattern = Pattern.compile("^(.+)@(.+)$");
	protected Matcher matcher = null;
	
	protected AccountDAOService accountDAOService = new AccountDAOService();
	protected ProviderDAOService providerDAOService = new ProviderDAOService();
	protected DateTimeProviderDAO dateTimeProviderDAO = new DateTimeProviderDAO();
	
	protected static int idProvider = -1;

}
