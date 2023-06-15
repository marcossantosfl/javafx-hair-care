package controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import service.UserDaoService;
import service.DatesDaoService;
import service.PathFXMLService;
import service.NotificationService;
import service.ProviderDaoService;
import service.ReviewDaoService;

/*
 * Class PublicClassController created
 */
public class PublicClassController {
	/*
	 * Load FXML and change all the elements
	 */
	protected void LoadFXMLWithSpinner(VBox vbox, String PathFXML) throws IOException {
		
		VBox spin = FXMLLoader.load(getClass().getResource(PathFXMLService.SPINNER_SCREEN));
		vbox.getChildren().clear();
		vbox.getChildren().setAll(spin);
		
		
		VBox newVbox = FXMLLoader.load(getClass().getResource(PathFXML));
		
		PauseTransition pause = new PauseTransition(Duration.seconds(2));
		pause.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				vbox.getChildren().clear();
				vbox.getChildren().setAll(newVbox);
			}
		});
		pause.play();
	}

	protected final int MIN_CHARACTER = 10;
	protected final int MAX_CHARACTER = 35;

	/*
	 * Pattern email (validation)
	 */
	protected Pattern pattern = Pattern.compile("^(.+)@(.+)$");
	protected Matcher matcher = null;

	/*
	 * All the DAO services
	 */
	protected UserDaoService accountDAOService = new UserDaoService();
	protected ProviderDaoService providerDAOService = new ProviderDaoService();
	protected DatesDaoService dateTimeProviderDAO = new DatesDaoService();
	protected NotificationService notificationService = new NotificationService();
	protected ReviewDaoService reviewDaoService = new ReviewDaoService();

	/*
	 * DateFormat class (convert date into string with -)
	 */
	protected class DateFormat {
		private int year;
		private int month;
		private int day;
		private int hour;
		private int minute;

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getHour() {
			return hour;
		}

		public void setHour(int hour) {
			this.hour = hour;
		}

		public int getMinute() {
			return minute;
		}

		public void setMinute(int minute) {
			this.minute = minute;
		}

		public boolean dateFormatString(String value) {

			for (int i = 0; i < value.length(); i++) {
				if (value.charAt(i) == '-') {
					this.setYear(Integer.parseInt(value.substring(0, 4)));
					this.setMonth(Integer.parseInt(value.substring(5, 7)));
					this.setDay(Integer.parseInt(value.substring(8, 10)));
					return true;
				}
			}

			return false;
		}

		public boolean timeFormatString(String value) {

			for (int i = 0; i < value.length(); i++) {
				if (value.charAt(i) == ':') {
					this.setHour(Integer.parseInt(value.substring(0, 2)));
					this.setMinute(Integer.parseInt(value.substring(3, 5)));
					return true;
				}
			}

			return false;
		}
	}

	public DateFormat dateFormat = new DateFormat();
	
	public static boolean threadStarted = false;
}
