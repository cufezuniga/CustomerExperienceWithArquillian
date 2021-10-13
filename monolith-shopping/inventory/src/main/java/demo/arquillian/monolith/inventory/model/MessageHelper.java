package demo.arquillian.monolith.inventory.model;

public class MessageHelper {

	public static final String MSG_PREFIX_ERROR_HEADER = "INVENTORY ERROR! ";
	public static final String MSG_ERROR_INSUFFICIENT_QTY = 
			"%s. The quantity ordered for '%s' must be more than 0 and less than or equal to '%d'";
	public static final String MSG_ERROR_MISSING_INVENTORY = "%s. The inventory for item '%s' was not found.";
	
	public static String formatMessage(String message, Object...args) {
		return String.format(message, args);
	}
}
