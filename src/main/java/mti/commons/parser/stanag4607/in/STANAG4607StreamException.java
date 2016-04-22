package mti.commons.parser.stanag4607.in;

/**
 * The base exception for unexpected processing errors.
 * 
 */
public class STANAG4607StreamException extends
	Exception
{
	private static final long serialVersionUID = 1L;

	public STANAG4607StreamException() {
		super();
	}

	public STANAG4607StreamException(
		String message,
		Throwable cause ) {
		super(
			message,
			cause);
	}

	public STANAG4607StreamException(
		String message ) {
		super(
			message);
	}

	public STANAG4607StreamException(
		Throwable cause ) {
		super(
			cause);
	}
}
