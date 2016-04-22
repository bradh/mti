package mti.commons.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceException  extends Exception {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		log.error(message, cause);
	}

	public ServiceException(String message) {
		super(message);
		log.error(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
		log.error(cause.getMessage(), cause);
	}

}
