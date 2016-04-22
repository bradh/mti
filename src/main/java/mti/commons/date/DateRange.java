package mti.commons.date;

import java.io.Serializable;
import java.util.Date;

public class DateRange implements
	Serializable
{
	private static final long serialVersionUID = 1L;

	final public Date start;
	final public Date stop;

	public DateRange(
		final Date start,
		final Date stop ) {
		this.start = start;
		this.stop = stop;
	}

	public Date getStart() {
		return start;
	}

	public Date getStop() {
		return stop;
	}

	@Override
	public String toString() {
		return String.format(
			"Date Range: from -> %s, to -> %s",
			start.toString(),
			stop.toString());
	}
}
