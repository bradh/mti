package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import mti.commons.parser.stanag4607.IDataInput;

public class MTITargetCollection extends ArrayList<MTITarget> {
	private static final long serialVersionUID = 1L;
	protected int byteSize;
	protected byte[] e_mask;
	protected boolean e_mask_set = false;

	/**
	 * MTI_Target_Collection no arg constructor.
	 */
	public MTITargetCollection() {
		setSize(0);
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		final Iterator<MTITarget> iter = iterator();
		int ii = 0;
		while (iter.hasNext()) {
			final MTITarget mti_target = iter.next();
			sb.append("- Report # " + ++ii + " -\n" + mti_target + "\n");
		}
		return sb.toString();
	}

	/**
	 * Copy constructor MTI_Target_Collection
	 * 
	 * @param mtc
	 */
	public MTITargetCollection(final MTITargetCollection mtc) {
		this();
		copyValues(mtc);
	}

	/**
	 * write the number of targets to an output stream
	 * MTI_Target_Collection.writeSelfToDataOutputStream
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		MTITarget target;
		for (int index = 0; index < size(); index++) {
			target = get(index);
			target.writeSelfToDataOutputStream(stream);
		}
	}

	/**
	 * Unlike the other implementations of readSelfFromIDataInput, this only can
	 * read ONE target from the stream and add it to the current list of target.
	 */
	public void readSelfFromIDataInput(final IDataInput stream) throws Exception {
		if (!isE_mask_set()) {
			throw new STANAG4607RuntimeException("Emask not set for target group");
		} else {
			final MTITarget newTarget = new MTITarget();
			newTarget.setE_mask(getE_mask());
			newTarget.readSelfFromInputStream(stream);
			add(newTarget);
		}

	}

	/**
	 * copy all of the other MTI_Target_Collections targets into me.
	 * MTI_Target_Collection.copyValues
	 * 
	 * @param col
	 */
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final MTITargetCollection col = (MTITargetCollection) o;
			MTITarget mt;
			final Iterator<MTITarget> iter = col.iterator();
			while (iter.hasNext()) {
				mt = new MTITarget(iter.next());
				add(mt);
			}
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/**
	 * Read the number of MTI targets from the data input stream
	 * MTI_Target_Collection.readSelfFromIDataInput
	 * 
	 * @param numberOfTargets
	 * @param stream
	 * 
	 * @throws Exception
	 */
	public void readSelfFromIDataInput(final int numberOfTargets, final IDataInput stream) throws Exception {
		for (int count = 0; count < numberOfTargets; count++) {
			readSelfFromIDataInput(stream);
		}
	}

	public boolean incorporate(final Object o) {
		// if o can be cast to me (I can point to it)
		// then I can copy all of its values (incorporate it)
		// that we share in common.
		if (this.getClass().isInstance(o)) {
			copyValues(o);
			return true;
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}
	}

	/**
	 * get the number of bytes for this collection. Same as getSize and
	 * reportSize
	 * 
	 * @return
	 */
	public int getByteSize() {
		return byteSize;
	}

	/**
	 * the mask for this collection of targets. Note, adding in a target does
	 * NOT set the context appropriate bits. MTI_Target_Collection.getE_mask
	 * 
	 * @return
	 */
	public byte[] getE_mask() {
		return e_mask;
	}

	/**
	 * do we have a mask for this collection of targets
	 * MTI_Target_Collection.isE_mask_set
	 * 
	 * @return
	 */
	public boolean isE_mask_set() {
		return e_mask_set;
	}

	/**
	 * set the number of bytes forming this collection of targets
	 * MTI_Target_Collection.setByteSize
	 * 
	 * @param i
	 */
	public void setByteSize(final int i) {
		byteSize = i;
	}

	/**
	 * simple forward to setByteSize MTI_Target_Collection.setSize
	 * 
	 * @param i
	 */
	public void setSize(final int i) {
		setByteSize(i);
	}

	/**
	 * set the mask to the one supplied MTI_Target_Collection.setE_mask
	 * 
	 * @param bs
	 */
	public void setE_mask(final byte[] bs) {
		e_mask = bs;
		setE_mask_set(true);
	}

	/**
	 * set the flag indicating if the mask has been set
	 * MTI_Target_Collection.setE_mask_set
	 * 
	 * @param b
	 */
	public void setE_mask_set(final boolean b) {
		e_mask_set = b;
	}

	/**
	 * override of add method. Handles the bytes size of this collection
	 * appropriately
	 * 
	 * @param target
	 */
	@Override
	public boolean add(final MTITarget target) {
		final boolean returnValue = super.add(target);
		setSize(getByteSize() + target.reportSize());
		return returnValue;
	}

	/**
	 * Override of the addAll method
	 */
	@Override
	public boolean addAll(final Collection<? extends MTITarget> targets) {
		MTITarget mt;
		final boolean returnValue = super.addAll(targets);

		final Iterator<? extends MTITarget> iter = targets.iterator();
		while (iter.hasNext()) {
			mt = iter.next();
			add(mt);
		}
		return returnValue;
	}

	/**
	 * Override of the remove method. Handles the size of the collection
	 */
	@Override
	public MTITarget remove(final int i) {
		final MTITarget mt = super.remove(i);
		setSize(getByteSize() - mt.reportSize());
		return mt;
	}

	/**
	 * return the bytes for this target collection MTI_Target_Collection.getSize
	 * 
	 * @return
	 */
	public int getSize() {
		return getByteSize();
	}

	/**
	 * return the bytes for this target collection
	 * MTI_Target_Collection.reportSize
	 * 
	 * @return
	 */
	public int reportSize() {
		return getSize();
	}

}
