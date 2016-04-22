package mti.commons.parser.stanag4607.in;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import mti.commons.parser.stanag4607.IDataInput;

public class ScattererRecordsCollection extends ArrayList<HRRRecord> {
	private static final long serialVersionUID = 1L;
	protected int size;

	/**
	 * Scatterer_Records_Collection
	 */
	public ScattererRecordsCollection() {
		setSize(0);
	}

	/**
	 * copying constructor Scatterer_Records_Collection
	 * 
	 * @param src
	 */
	public ScattererRecordsCollection(final ScattererRecordsCollection src) {
		this();
		copyValues(src);
	}

	/**
	 * Scatterer_Records_Collection.getSize
	 * 
	 * @return
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Scatterer_Records_Collection.reportSize
	 * 
	 * @return
	 */
	public int reportSize() {
		return getSize();
	}

	/**
	 * Scatterer_Records_Collection.setSize
	 * 
	 * @param i
	 */
	public void setSize(final int i) {
		size = i;
	}

	/**
	 * Override of add to a properly add processingRecords
	 * 
	 * @param processingRecord
	 */
	@Override
	public boolean add(final HRRRecord record) {
		if (super.add(record)) {
			final HRRRecord hr = record;
			setSize(getSize() + hr.getSize());
			return true;
		} else {
			return false;
		}
	}

	// WAL MOD 12/07/04 New method added
	/**
	 * @return String dump of contents
	 */
	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();

		final Iterator<HRRRecord> iter = iterator();
		int ii = 0;
		while (iter.hasNext()) {
			final HRRRecord hrr_rec = iter.next();
			sb.append("- Record # " + ++ii + " -\n" + hrr_rec + "\n");
		}
		return sb.toString();
	}

	/**
	 * write this collection of HRR_Recs out to the data stream
	 * Scatterer_Records_Collection.writeSelfToDataOutputStream
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	public void writeSelfToDataOutputStream(final DataOutputStream stream) throws Exception {
		HRRRecord target;
		for (int index = 0; index < size(); index++) {
			target = get(index);
			target.writeSelfToDataOutputStream(stream);
		}

	}

	/**
	 * read the number of elements of HRR_Recs from the stream and place inot
	 * this collection Scatterer_Records_Collection.readSelfFromIDataInput
	 * 
	 * @param numberOfElements
	 * @param stream
	 * 
	 * @throws Exception
	 */
	public void readSelfFromIDataInput(final int numberOfElements, final short numberOfBytesMagnitude,
			final short numberOfBytesPhase, boolean sparse, final IDataInput stream) throws Exception {
		for (int i = 0; i < numberOfElements; i++) {
			readSelfFromIDataInput(numberOfBytesMagnitude, numberOfBytesPhase, sparse, stream);
		}
	}

	/**
	 * Read in a HRR_Rec and place it into this collection
	 * Scatterer_Records_Collection.readSelfFromIDataInput
	 * 
	 * @param stream
	 * 
	 * @throws Exception
	 */
	public void readSelfFromIDataInput(final short numberOfBytesMagnitude, final short numberOfBytesPhase,
			boolean sparse, final IDataInput stream) throws Exception {
		final HRRRecord newRec = new HRRRecord();
		newRec.setNumberOfBytesMagnitude(numberOfBytesMagnitude);
		newRec.setNumberOfBytesPhase(numberOfBytesPhase);
		newRec.setSparse(sparse);
		newRec.readSelfFromInputStream(stream);
		add(newRec);

	}

	/**
	 * Overload of the addAll method
	 */
	@Override
	public boolean addAll(final Collection<? extends HRRRecord> records) {
		HRRRecord hr;
		boolean returnValue = false;
		final Iterator<? extends HRRRecord> iter = records.iterator();
		while (iter.hasNext()) {
			hr = iter.next();
			if (add(hr)) {
				setSize(getSize() + hr.getSize());
				returnValue = true;
			}
		}
		return returnValue;
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
			final ScattererRecordsCollection src = (ScattererRecordsCollection) o;
			HRRRecord pr;
			final Iterator<HRRRecord> iter = src.iterator();
			while (iter.hasNext()) {
				pr = new HRRRecord(iter.next());
				add(pr);
			}
		} else {
			throw new STANAG4607RuntimeException(
					"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/**
	 * Override of the remove method. Handles the size of the collection
	 */
	@Override
	public HRRRecord remove(final int i) {
		final HRRRecord hr = super.remove(i);
		setSize(getSize() - hr.getSize());
		return hr;
	}
}
