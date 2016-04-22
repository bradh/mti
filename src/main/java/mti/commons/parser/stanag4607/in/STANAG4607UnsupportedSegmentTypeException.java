package mti.commons.parser.stanag4607.in;

public class STANAG4607UnsupportedSegmentTypeException extends
	STANAG4607RuntimeException
{

	private static final long serialVersionUID = 1L;

	public STANAG4607UnsupportedSegmentTypeException(
		final String msg ) {
		super(
			msg);
	}

	public STANAG4607UnsupportedSegmentTypeException(
		final String msg,
		Exception e ) {
		super(
			msg,
			e);
	}

}
