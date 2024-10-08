
package exception;
@SuppressWarnings("serial")
public class CustomerNotFoundException extends Exception{
	public CustomerNotFoundException(String message) {
        super(message);  // Pass the error message to the Exception class
    }
}
