package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

public class Segment {
	public SegmentHeader m_header = null;
	public Base m_data = null;

	/**
	 * Constructor.
	 * 
	 * @param a_segHeader
	 *            The segment header.
	 * @param a_segData
	 *            The data portion of the segment.
	 */
	public Segment(final SegmentHeader a_segHeader, final Base a_segData) {
		m_header = a_segHeader;
		m_data = a_segData;
	}

	public void write(final DataOutputStream dos) throws Exception {
		m_header.writeSelfToDataOutputStream(dos);
		m_data.writeSelfToDataOutputStream(dos);
	}

	/**
	 * @return String representation of the 4607 segment header/data pair.
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		sb.append("\n*** SEGMENT HEADER ***\n");
		sb.append(m_header);
		sb.append("\n* SEGMENT DATA *\n");
		sb.append(m_data);

		return sb.toString();
	}
}
