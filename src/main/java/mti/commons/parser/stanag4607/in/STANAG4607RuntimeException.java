package mti.commons.parser.stanag4607.in;

public class STANAG4607RuntimeException extends
	RuntimeException
{
	private static final long serialVersionUID = 1L;

	public STANAG4607RuntimeException(
		final String msg ) {
		super(
			msg);
	}

	public STANAG4607RuntimeException(
		final String msg,
		Exception e ) {
		super(
			msg,
			e);
	}
}
