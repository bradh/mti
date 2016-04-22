package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import mti.commons.parser.stanag4607.IDataInput;

public class ProcessingRecordsCollection extends ArrayList<ProcessingRecord> {
	private static final long serialVersionUID = 1L;
	protected int size = 0;

	/**
	 * no arg constructor Processing_Records_Collection
	 */
	public ProcessingRecordsCollection() {
		setSize(0);
	}

	/**
	 * copying constructor Processing_Records_Collection
	 * 
	 * @param prc
	 */
	public ProcessingRecordsCollection(final ProcessingRecordsCollection prc) {
		this();
		copyValues(prc);
	}

	/**
	 * note due to Java's inablity to actually do a true runtime dynamic cast
	 * (cast is a static directive informing the jvm to view a reference as
	 * something, NOT dynamic), this method is not as cool as it could be. To
	 * add new conversions (incorporations), override THIS method. method
	 * signature Base.incorporate
	 * 
	 * @param o
	 * 
	 * @return
	 */
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
	 * method to copy values. Should be overridden on each subclass.
	 * Base.copyValues
	 * 
	 * @param b
	 */
	public void copyValues(final Object o) {
		if (this.getClass().isInstance(o)) {
			final ProcessingRecordsCollection prc = (ProcessingRecordsCollection) o;
			ProcessingRecord pr;
			final Iterator<ProcessingRecord> iter = prc.iterator();
			while (iter.hasNext()) {
				pr = new ProcessingRecord(iter.next());
				add(pr);
			}
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tia.base.dataElements.interfaces.IDataElement#
	 * writeSelfToDataOutputStream (java.io.DataOutputStream)
	 */
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		ProcessingRecord pr;
		for (int index = 0; index < size(); index++) {
			pr = get(index);
			pr.writeSelfToDataOutputStream(stream);
		}
	}

	public void readSelfFromIDataInput(final int numberOfTargets, final IDataInput stream) throws Exception {
		for (int count = 0; count < numberOfTargets; count++) {
			readSelfFromIDataInput(stream);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tia.base.dataElements.interfaces.IDataElement#readSelfFromIDataInput
	 * (java.io.IDataInput)
	 */
	public void readSelfFromIDataInput(final IDataInput stream) throws Exception {
		final ProcessingRecord newTarget = new ProcessingRecord();
		newTarget.readSelfFromInputStream(stream);
		add(newTarget);
	}

	/**
	 * Processing_Records_Collection.getSize
	 * 
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Processing_Records_Collection.reportSize
	 * 
	 * @return
	 */
	public int reportSize() {
		return getSize();
	}

	/**
	 * Processing_Records_Collection.setSize
	 * 
	 * @param i
	 */
	public void setSize(final int i) {
		size = i;
	}

	/**
	 * Override of add to a properly add ProcessingRecs
	 * 
	 * @param ProcessingRecord
	 */
	@Override
	public boolean add(final ProcessingRecord processingRec) {
		final ProcessingRecord pr = processingRec;
		final boolean returnValue = super.add(pr);
		setSize(getSize() + pr.reportSize());
		return returnValue;
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		final Iterator<ProcessingRecord> iter = iterator();
		int ii = 0;
		while (iter.hasNext()) {
			final ProcessingRecord processingRec = iter.next();
			sb.append("- Record # " + ++ii + " -\n" + processingRec + "\n");
		}
		return sb.toString();
	}

	/**
	 * Overload of the addAll method
	 */
	@Override
	public boolean addAll(final Collection<? extends ProcessingRecord> processingRecords) {
		ProcessingRecord pr;
		boolean returnValue = false;
		final Iterator<? extends ProcessingRecord> iter = processingRecords.iterator();
		while (iter.hasNext()) {
			pr = iter.next();
			if (add(pr)) {
				returnValue = true;
			}
		}
		return returnValue;
	}

	/**
	 * Override of the remove method. Handles the size of the collection
	 */
	@Override
	public ProcessingRecord remove(final int i) {
		final ProcessingRecord pr = super.remove(i);
		setSize(getSize() - pr.reportSize());
		return pr;
	}

}
