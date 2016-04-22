package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;

import mti.commons.parser.stanag4607.IDataInput;

public class TruthTag extends Base {
	protected short application; // 1
	protected long entity;

	// WAL MOD 09/02/04 Added existence booleans to handle size calculations
	private boolean application_Exist = false;
	private boolean entity_Exist = false;

	/**
	 * TruthTag no arg constructor
	 */
	public TruthTag() {
		// WAL MOD 09/02/04 Changed size setting from 5 to 0. The way things are
		// written, you cannot
		// automatically assume 5 bytes will be used (could be initialized but
		// fields never set, in
		// which case you wouldn't have any size.
		setSize(0);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		if (application_Exist) {
			sb.append("Application:\t" + getApplication() + "\n");
		}
		if (entity_Exist) {
			sb.append("Entity:\t" + getEntity() + "\n");
		}

		return sb.toString();
	}

	/**
	 * copying constructor TruthTag
	 * 
	 * @param tt
	 */
	public TruthTag(final TruthTag tt) {
		this();
		copyValues(tt);

	}

	/*
	 * (non-Javadoc) Override of copyValues TruthTag.copyValues
	 * 
	 */
	@Override
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final TruthTag tt = (TruthTag) o;
			setApplication(tt.getApplication());
			setEntity(tt.getEntity());
			setSize(5);
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/*
	 * (non-Javadoc) Override of writeSelfToDataOutputStream
	 * TruthTag.writeSelfToDataOutputStream
	 * 
	 */
	@Override
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		stream.writeByte(getApplication());
		writeAsInt(getEntity(), stream);

	}

	/*
	 * (non-Javadoc) Override of readSelfFromIDataInput
	 * TruthTag.readSelfFromIDataInput
	 * 
	 */
	@Override
	public void readSelfFromInputStream(final IDataInput stream) throws Exception {
		setApplication(byteAsShort(stream.readByte()));
		setEntity(longAsUnsignedInt(stream.readInt()));

	}

	/**
	 * TruthTag.getApplication
	 * 
	 * @return
	 */
	public short getApplication() {
		return application;
	}

	/**
	 * TruthTag.getEntity
	 * 
	 * @return
	 */
	public long getEntity() {
		return entity;
	}

	/**
	 * TruthTag.setApplication
	 * 
	 * @param b
	 */
	public void setApplication(final short b) {
		application = withinRange(0, 255, b, "Application out of range");

		// WAL MOD 09/02/04 If the field has already been set once, should not
		// update the size again.
		if (!application_Exist) {
			application_Exist = true;
			setSize(getSize() + 1);
		}
	}

	/**
	 * TruthTag.setEntity
	 * 
	 * @param l
	 */
	public void setEntity(final long l) {
		entity = withinRangeL(0, 4294967295l, l, "Entity is out of range");

		// WAL MOD 09/02/04 If the field has already been set once, should not
		// update the size again.
		if (!entity_Exist) {
			entity_Exist = true;
			setSize(getSize() + 4);
		}
	}

}
