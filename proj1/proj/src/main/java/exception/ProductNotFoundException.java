package exception;
@SuppressWarnings("serial")
public class ProductNotFoundException extends Exception{
	public ProductNotFoundException(String message) {
        super(message);  // Pass the error message to the Exception class
    }

}
