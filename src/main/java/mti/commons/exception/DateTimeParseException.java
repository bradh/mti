package mti.commons.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeParseException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public DateTimeParseException() {
		super();
	}

	public DateTimeParseException(String message, Throwable cause) {
		super(message, cause);
		log.error(message, cause);
	}

	public DateTimeParseException(String message) {
		super(message);
		log.error(message);
	}

	public DateTimeParseException(Throwable cause) {
		super(cause);
		log.error(cause.getMessage(), cause);
	}
}