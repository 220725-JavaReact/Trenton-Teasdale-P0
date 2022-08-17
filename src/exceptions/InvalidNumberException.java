package exceptions;

public class InvalidNumberException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidNumberException(String message) {
		super(message);
	}
}
