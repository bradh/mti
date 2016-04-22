package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;
import java.io.RandomAccessFile;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mti.commons.parser.stanag4607.IDataInput;

public class Packet {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public PacketHeader m_packetHeader; // The header portion of the packet
	public Vector<Segment> m_segments = new Vector<Segment>(); // One or more
	// segment
	// header/data pairs
	public long endingFilePosition;
	public boolean hitEof = false;

	public Packet() {
		m_packetHeader = new PacketHeader(this);
		m_segments = new Vector<Segment>();
	}

	/**
	 * Constructor.
	 * <p/>
	 * Reads a raw 4607 packet from an input stream.
	 * 
	 * @param a_dis
	 *            Points to beginning the raw 4607 packet (packet header).
	 */
	public Packet(final IDataInput a_dis) throws Exception {
		this(a_dis, 0);
	}

	public Packet(final IDataInput a_dis, final long endingFilePosition) throws Exception {
		this.endingFilePosition = endingFilePosition;

		try {
			read4607(a_dis);
		} catch (final java.io.IOException ee) {
			hitEof = true;
		}
	}

	public void write(final DataOutputStream dos) throws Exception {
		m_packetHeader.writeSelfToDataOutputStream(dos);

		for (final Segment segment : m_segments) {
			segment.write(dos);
		}
	}

	private void read4607(final IDataInput stream) throws Exception {
		m_packetHeader = new PacketHeader(this);
		m_packetHeader.readSelfFromInputStream(stream);

		Long lastGoodFilePointer = null;
		if (stream instanceof RandomAccessFile) {
			lastGoodFilePointer = ((RandomAccessFile) stream).getFilePointer();
		}

		try {
			readAfterPacketHeader(m_packetHeader, stream);
		} catch (final Exception e) {
			if (lastGoodFilePointer != null) {
				final RandomAccessFile raf = (RandomAccessFile) stream;
				log.error("Couldn't parse packet, skipping..." + e.getMessage());
				raf.seek(lastGoodFilePointer + m_packetHeader.getPacket_Size() - m_packetHeader.reportSize());
			} else {
				throw new Exception("Bad Packet", e);
			}
		}
	}

	private void readAfterPacketHeader(final PacketHeader header, final IDataInput stream) throws Exception {
		Base afterSegHeader;
		SegmentHeader segHead = null;
		long byteCount = header.reportSize();
		while (byteCount < header.getPacket_Size())
		// still more bytes to read
		{
			segHead = new SegmentHeader();
			segHead.readSelfFromInputStream(stream);
			// we may decide later to not include the segment headers
			byteCount += segHead.reportSize();

			Long lastGoodFilePointer = null;
			if (stream instanceof RandomAccessFile) {
				lastGoodFilePointer = ((RandomAccessFile) stream).getFilePointer();
			}

			try {
				afterSegHeader = readAfterSegmentHeader(segHead, stream);
				m_segments.add(new Segment(segHead, afterSegHeader));
				byteCount += afterSegHeader.reportSize();
			} catch (final STANAG4607RuntimeException sre) {
				log.error("Couldn't parse segment type, skipping..." + sre.getMessage());
				byteCount += segHead.getSegmentSize() - segHead.reportSize();
				stream.skipBytes((int) segHead.getSegmentSize() - segHead.reportSize());
			} catch (final Exception e) {
				if (lastGoodFilePointer != null) {
					final RandomAccessFile raf = (RandomAccessFile) stream;
					log.error("Couldn't parse segment type, skipping..." + e.getMessage());
					raf.seek(lastGoodFilePointer + segHead.getSegmentSize() - segHead.reportSize());
				} else {
					throw new Exception("Bad Segment", e);
				}
			}
		}
	}

	private static Base readAfterSegmentHeader(final SegmentHeader segHeader, final IDataInput stream)
			throws Exception {
		Base afterSegHeader = null;
		switch (segHeader.getSegmentType()) {
		case SegmentHeader.SEG_TYPE_MISSION:
			afterSegHeader = new MissionSegment();
			break;
		case SegmentHeader.SEG_TYPE_DWELL:
			afterSegHeader = new DwellSegment();
			break;
		case SegmentHeader.SEG_TYPE_JOB:
			afterSegHeader = new JobSegment();
			break;
		case SegmentHeader.SEG_TYPE_FREETEXT:
			final FreeText freeText = new FreeText();
			freeText.setReadToLength(segHeader.getSegmentSize() - Base.CGMTI_SHDR_SIZE - 20);
			afterSegHeader = freeText;
			break;
		case SegmentHeader.SEG_TYPE_HRR:
			afterSegHeader = new HRRSegment();
			break;
		case SegmentHeader.SEG_TYPE_JOB_REQ:
			afterSegHeader = new JobReqSeg();
			break;
		case SegmentHeader.SEG_TYPE_JOB_ACK:
			afterSegHeader = new JobAcknowledgeSet();
			break;
		case SegmentHeader.SEG_TYPE_TEST_STAT:
			afterSegHeader = new TestStatusSeg();
			break;
		case SegmentHeader.SEG_TYPE_PHISTORY:
			afterSegHeader = new History();
			break;
		case SegmentHeader.SEG_TYPE_PLAT_LOC:
			afterSegHeader = new PlatformLocation();
			break;
		case SegmentHeader.SEG_TYPE_RANGE_DOPP:
			throw new STANAG4607RuntimeException("RANGE_DOPP currently unimplemented");
		case SegmentHeader.SEG_TYPE_LOW_REFL:
			throw new STANAG4607RuntimeException("Low Refl currently unimplemented");
		case SegmentHeader.SEG_TYPE_GROUP:
			throw new STANAG4607RuntimeException("Type Group currently unimplemented");
		case SegmentHeader.SEG_TYPE_ATT_TARG:
			throw new STANAG4607RuntimeException("ATT_TARG currently unimplemented");
		case SegmentHeader.SEG_TYPE_SYS_SPEC:
			throw new STANAG4607RuntimeException("SYS_SPEC currently unimplemented");
		default:
			throw new STANAG4607RuntimeException("Unknown type in data stream");
		}
		afterSegHeader.readSelfFromInputStream(stream);
		return afterSegHeader;
	}

	/**
	 * @return String representation of 4607 packet.
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("***** PACKET HEADER *****\n");
		sb.append(m_packetHeader);

		for (int ii = 0; ii < m_segments.size(); ii++) {
			sb.append(m_segments.get(ii));
		}
		return sb.toString();
	}

	/**
	 * This simply looks at the first packet header and segment header to verify
	 * it is a 4607 format
	 * 
	 * @param header
	 * @param stream
	 * @throws Exception
	 */
	public static boolean peek(final IDataInput stream) {
		SegmentHeader segHead = null;
		final PacketHeader packetHeader = new PacketHeader();

		try {
			packetHeader.readSelfFromInputStream(stream);
			segHead = new SegmentHeader();
			segHead.readSelfFromInputStream(stream);

			readAfterSegmentHeader(segHead, stream);
		} catch (final Exception e) {
			return false;
		}
		return true;
	}

}
