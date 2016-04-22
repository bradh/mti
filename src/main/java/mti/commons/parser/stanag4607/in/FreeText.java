package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;
import java.nio.charset.CharacterCodingException;

import mti.commons.parser.stanag4607.IDataInput;

public class FreeText extends Base {
	protected byte[] orig_id; // 10
	protected byte[] rec_id; // 10
	protected byte[] ftext; // max of 65515
	protected long readToLength = -1;

	/**
	 * no arg public constructor FreeText
	 */
	public FreeText() {
		setOrig_id(new byte[10]);
		setRec_id(new byte[10]);
		setSize(20);
	}

	// WAL MOD 12/06/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		try {
			sb.append("Originator ID:\t" + byteArrayToString(getOrig_id()) + "\n");
			sb.append("Recipient ID:\t" + byteArrayToString(getRec_id()) + "\n");
			sb.append("Free Text:\t" + byteArrayToString(getFtext()) + "\n");
		} catch (final CharacterCodingException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 * copy constructor FreeText
	 * 
	 * @param ft
	 */
	public FreeText(final FreeText ft) {
		this();
		copyValues(ft);

	}

	/*
	 * (non-Javadoc) Override of copyValues FreeText.copyValues
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final FreeText ft = (FreeText) o;
			for (int i = 0; i < ft.getOrig_id().length; i++) {
				getOrig_id()[i] = ft.getOrig_id()[i];
			}
			for (int i = 0; i < ft.getRec_id().length; i++) {
				getRec_id()[i] = ft.getRec_id()[i];
			}
			if (ft.getFtext() != null) {
				for (int i = 0; i < ft.getFtext().length; i++) {
					getFtext()[i] = ft.getFtext()[i];
				}
			}
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * FreeText.writeSelfToDataOutputStream
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.write(getOrig_id());
		stream.write(getRec_id());
		stream.write(getFtext());
	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * FreeText.readSelfFromIDataInput
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		final byte[] tmpBytes = new byte[MAX_FREE_TEXT];
		int actualByteCount = 0;

		readToLength(getOrig_id(), stream, 0, getOrig_id().length);
		readToLength(getRec_id(), stream, 0, getRec_id().length);
		if (getReadToLength() > 1) {
			actualByteCount = stream.read(tmpBytes, 0, (int) getReadToLength());
		} else {
			// don't read -- size to read to has not been set
		}
		setFtext(new byte[actualByteCount]);
		System.arraycopy(tmpBytes, 0, getFtext(), 0, actualByteCount);
	}

	/**
	 * return byte array of free text
	 * 
	 * @return
	 */
	public byte[] getFtext() {
		if (ftext == null) {
			throw new STANAG4607RuntimeException("Free text field not initialized yet");
		} else {
			return ftext;
		}

	}

	/**
	 * get byte array of originator id
	 * 
	 * @return
	 */
	public byte[] getOrig_id() {
		return orig_id;
	}

	/**
	 * return the read to length -- helper value Note, field currently serves no
	 * purpose
	 * 
	 * @return
	 */
	public long getReadToLength() {
		return readToLength;
	}

	/**
	 * return the byte array of the recipient id
	 * 
	 * @return
	 */
	public byte[] getRec_id() {
		return rec_id;
	}

	/**
	 * set free text
	 * 
	 * @param bs
	 */
	public void setFtext(final byte[] bs) {
		ftext = bs;
		setSize(reportSize() + ftext.length);
	}

	/**
	 * set originator id
	 * 
	 * @param bs
	 */
	public void setOrig_id(final byte[] bs) {
		orig_id = new byte[10];
		final int length = Math.min(bs.length, 10);
		for (int i = 0; i < length; ++i) {
			orig_id[i] = bs[i];
		}
	}

	/**
	 * set the read to length. Note MUST set the field to the correct value
	 * BEFORE performing a read!!!!!!!
	 * 
	 * @param l
	 */
	public void setReadToLength(final long l) {
		readToLength = l;
	}

	/**
	 * set recipient id
	 * 
	 * @param bs
	 */
	public void setRec_id(final byte[] bs) {
		rec_id = new byte[10];
		final int length = Math.min(bs.length, 10);
		for (int i = 0; i < length; ++i) {
			rec_id[i] = bs[i];
		}
	}

	public static final int MAX_FREE_TEXT = 65515;
}
