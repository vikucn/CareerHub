package exception;
@SuppressWarnings("serial")
public class OrderNotFoundException extends Exception {
	public OrderNotFoundException(String message) {
        super(message);  // Pass the error message to the Exception class
    }

}
