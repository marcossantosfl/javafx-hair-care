package model;

import java.util.ArrayList;
import java.util.List;

public class AccountLogged
{
	public final static int CUSTOMER = 0;
	public final static int PROVIDER = 1;
	public final static int ADMINISTRATOR = 2;
	
	public static int accountId = 0;
	public static int accountRole = -1;
	public static int accountAccepted = -1;
	public static int accountLocation = -1;
	
	public static List<Provider> providers = new ArrayList<>();
	
	public static List<DateTimeProvider> datetimeproviders = new ArrayList<>();
}
