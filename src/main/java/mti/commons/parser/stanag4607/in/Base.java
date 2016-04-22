package mti.commons.parser.stanag4607.in;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import mti.commons.parser.stanag4607.IDataInput;

public abstract class Base
{
	protected int size = 0;

	protected static Charset currentCharSet = Charset.forName(
		"US-ASCII");
	protected static CharsetDecoder decoder = currentCharSet.newDecoder();
	protected static CharsetEncoder encoder = currentCharSet.newEncoder();

	protected static String nationality;
	protected static String codeword;
	protected static String control;
	protected static String mission_id;

	protected static String mission_plan;
	protected static String flight_plan;
	protected static String platform_config;

	protected static short sensor_err_track;
	protected static short sensor_err_cross;
	protected static short sensor_err_alt;

	protected static short sensor_track_err;
	protected static double sensor_speed_err;
	protected static short mdv;
	protected static short p_detection;
	protected static short p_false_alarm;
	protected static String recipient_id;

	protected static String sensor_model;
	protected static short radar_priority;
	protected static short revisit_interval;
	protected static String terrain_model;
	protected static String geoid_model;
	protected static String version_id;
	protected static int hdr_job_id;
	protected static double offset_time;

	protected static String configFileName = null;

	protected static boolean configured = false;

	/**
	 * Simple print method to aid in debugging
	 * 
	 * @param ps
	 */
	public void printSelf(
		final PrintStream ps ) {
		ps.println(
			this.getClass().getName());
	}

	/**
	 * Method to assist in the testing of membership in a bit mapped mask. --
	 * really just makes the code cleaner.
	 * 
	 * @param toTest
	 * @param mask
	 * @return
	 */
	public static boolean testBit(
		final byte toTest,
		final byte mask ) {
		if ((toTest & mask) != 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * General method to report the size of this data structure.
	 * 
	 * @return
	 */
	public int reportSize() {
		return getSize();
	}

	/**
	 * note due to Java's inablity to actually do a true runtime dynamic cast
	 * (cast is a static directive informing the jvm to view a reference as
	 * something, NOT dynamic), this method is not as cool as it could be. To
	 * add new conversions (incorporations), override THIS method. Also,
	 * override this method to allow for the incorporation (copying common
	 * values) from different specialization branches (share a common parent but
	 * one is NOT a specialization of the other)
	 * 
	 * Base.incorporate
	 * 
	 * @param o
	 * @return
	 */
	public boolean incorporate(
		final Object o ) {
		// if o can be cast to me (I can point to it)
		// then I can copy all of its values (incorporate it)
		// that we share in common.
		if (this.getClass().isInstance(
			o)) {
			copyValues(
				o);
			return true;
		}
		else {
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
	public void copyValues(
		final Object o ) {
		if (this.getClass().isInstance(
			o)) {
			final Base b = (Base) o;
			setSize(
				b.reportSize());
		}
		else {
			throw new STANAG4607RuntimeException(
				"Object " + o.getClass().getName() + "is not of type" + this.getClass().getName());
		}

	}

	/**
	 * @return size of this data structure
	 */
	protected int getSize() {
		return size;
	}

	/**
	 * set the size of this data structure.
	 * 
	 * @param i
	 */
	protected void setSize(
		final int i ) {
		size = i;
	}

	/**
	 * This static method is for changing the encoding/decoding charSet. Here I
	 * am assuming (for the present) that there is only one charset -- If need
	 * be, will change to handle multiple in the future.
	 * 
	 * @param charSetString
	 * @throws Exception
	 */
	public static void changeCharSet(
		final String charSetString )
			throws Exception {
		currentCharSet = Charset.forName(
			charSetString);
		decoder = currentCharSet.newDecoder();
		encoder = currentCharSet.newEncoder();
	}

	/**
	 * Method to transfer a number of bytes from a stream into a byte array
	 * 
	 * @param into
	 *            : sink for data
	 * @param stream
	 *            : source for data
	 * @param offset
	 *            : offset
	 * @param amount
	 *            : number of bytes to copy over
	 * @throws IOException
	 *             : on insuffficent number of bytes in source
	 */
	protected void readToLength(
		final byte[] into,
		final IDataInput stream,
		final int offset,
		final int amount )
			throws IOException {
		if (stream.read(
			into,
			offset,
			amount) != amount) {
			throw new IOException(
				"Insufficent bytes to be read from stream");
		}
	}

	/**
	 * Helper method to aid in clean code
	 * 
	 * @param d
	 * @param stream
	 * @throws IOException
	 */
	protected void writeAsFloat(
		final double d,
		final DataOutputStream stream )
			throws IOException {
		stream.writeFloat(
			(float) d);
	}

	/**
	 * Write this object's data out onto a data output stream according the the
	 * 4607 data definitions Base.writeSelfToDataOutputStream
	 * 
	 * @param stream
	 * @throws Exception
	 */
	public abstract void writeSelfToDataOutputStream(
		DataOutputStream stream )
			throws Exception;

	/**
	 * read and populate this 4607 object according the the specs
	 * Base.readSelfFromIDataInput
	 * 
	 * @param stream
	 * @throws Exception
	 */
	public abstract void readSelfFromInputStream(
		IDataInput stream )
			throws Exception;

	/**
	 * Helper method to aid in clean code
	 * 
	 * @param d
	 * @param stream
	 * @throws IOException
	 */
	protected void writeAsInt(
		final long l,
		final DataOutputStream stream )
			throws IOException {
		stream.writeInt(
			(int) l);
	}

	/**
	 * Helper method to aid in clean code
	 * 
	 * @param d
	 * @param stream
	 * @throws IOException
	 */
	protected void writeAsShort(
		final int l,
		final DataOutputStream stream )
			throws IOException {
		stream.writeShort(
			(short) l);
	}

	/**
	 * Static method used to convert a byte array containing ascii characters
	 * into a character array.
	 * 
	 * @param byteArray
	 *            : data source
	 * @return Character array representing ascii contents of supplied byte
	 *         array
	 * @throws CharacterCodingException
	 */
	protected static char[] asciiByteArrayToCharacterArray(
		final byte[] byteArray )
			throws CharacterCodingException {
		final ByteBuffer asciiBytes = ByteBuffer.wrap(
			byteArray);
		return decoder.decode(
			asciiBytes).array();
	}

	/**
	 * Convert the supplied ascii byte array into a string
	 * 
	 * @param byteArray
	 *            : data source
	 * @return String representing supplied ascii byte array
	 * @throws CharacterCodingException
	 */
	protected static String byteArrayToString(
		final byte[] byteArray )
			throws CharacterCodingException {
		return asciiByteArrayToCharacterArray(
			byteArray).toString();
	}

	protected static boolean isByteArrayEmpty(
		final byte[] byteArray ) {
		if ((byteArray == null) || (byteArray.length == 0)) {
			return true;
		}
		for (final byte element : byteArray) {
			if (element != 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Convert the supplied ascii valued character array into an ascii byte
	 * array
	 * 
	 * @param characterArray
	 * @return
	 * @throws CharacterCodingException
	 */
	protected static byte[] asciiCharacterArrayToAsciiByteArray(
		final char[] characterArray )
			throws CharacterCodingException {
		final CharBuffer charBuffer = CharBuffer.wrap(
			characterArray);
		return encoder.encode(
			charBuffer).array();
	}

	/**
	 * Convert the supplied ascii string into an ascii byte array
	 * 
	 * @param string
	 *            : supplied ascii string
	 * @return ascii byte array
	 * @throws CharacterCodingException
	 */
	protected static byte[] asciiToAsciiByteArray(
		final String string )
			throws CharacterCodingException {
		return asciiCharacterArrayToAsciiByteArray(
			string.toCharArray());
	}

	/**
	 * Takes an interger as input and converts it to a series of bytes in a byte
	 * array.
	 * 
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public static byte[] intToByteArray(
		final int number ) {
		try {
			final ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			final DataOutputStream outputStream = new DataOutputStream(
				byteArrayOut);
			outputStream.writeInt(
				number);
			return byteArrayOut.toByteArray();
		}
		catch (final IOException ioe) {
			throw new STANAG4607RuntimeException(
				"Error with intToByteArray Conversion\n" + "Original Exception " + ioe.toString());
		}

	}

	/**
	 * Just a method to return a value or throw a runtime exception
	 * 
	 * @param min
	 *            - integer minimum value
	 * @param max
	 *            - integer maximum value
	 * @param value
	 *            - supplied value to test
	 * @param msg
	 *            - exception message
	 * @return int value.
	 */
	protected short withinRange(
		final int min,
		final int max,
		final short value,
		final String msg ) {
		if ((min <= value) && (value <= max)) {
			return value;
		}
		else {

			throw new STANAG4607RuntimeException(
				msg);
		}
	}

	/**
	 * Just a method to return a value or throw a runtime exception
	 * 
	 * @param min
	 *            - integer minimum value
	 * @param max
	 *            - integer maximum value
	 * @param value
	 *            - supplied value to test
	 * @param msg
	 *            - exception message
	 * @return int value.
	 */
	protected int withinRange(
		final int min,
		final int max,
		final int value,
		final String msg ) {
		if ((min <= value) && (value <= max)) {
			return value;
		}
		else {
			throw new STANAG4607RuntimeException(
				msg);
		}
	}

	/**
	 * Just a method to return a value or throw a runtime exception
	 * 
	 * @param min
	 *            - integer minimum value
	 * @param max
	 *            - integer maximum value
	 * @param value
	 *            - supplied value to test
	 * @param msg
	 *            - exception message
	 * @return long value
	 */
	protected long withinRange(
		final int min,
		final int max,
		final long value,
		final String msg ) {
		if ((min <= value) && (value <= max)) {
			return value;
		}
		else {
			throw new STANAG4607RuntimeException(
				msg);
		}
	}

	/**
	 * As withinRange but with all long parameters -- solved ambigous issues
	 * 
	 * @param min
	 * @param max
	 * @param value
	 * @param msg
	 * @return
	 */
	protected long withinRangeL(
		final long min,
		final long max,
		final long value,
		final String msg ) {
		if ((min <= value) && (value <= max)) {
			return value;
		}
		else {
			throw new STANAG4607RuntimeException(
				msg);
		}
	}

	/**
	 * Just a method to return a value or throw a runtime exception
	 * 
	 * @param min
	 *            - integer minimum value
	 * @param max
	 *            - integer maximum value
	 * @param value
	 *            - supplied value to test
	 * @param msg
	 *            - exception message
	 * @return double value.
	 */
	protected double withinRange(
		final int min,
		final int max,
		final double value,
		final String msg ) {
		if ((min <= value) && (value <= max)) {
			return value;
		}
		else {
			throw new STANAG4607RuntimeException(
				msg);
		}
	}

	/**
	 * Encodes a double value into the B16 format
	 * 
	 * @param val
	 * @return
	 */
	protected short encode_B16(
		final double val ) {
		short number;
		final short f_exp = -7;

		if (val < 0.0) {
			number = (short) -(val / Math.pow(
				2,
				f_exp) - .5);
			number |= 0x8000;
		}
		else {
			number = (short) (val / Math.pow(
				2,
				f_exp) + .5);
		}
		return number;
	}

	/**
	 * Encodes a double value into the B32 format
	 * 
	 * @param val
	 * @return
	 */
	protected int encode_B32(
		final double val ) {
		int number;
		final int f_exp = -23;

		if (val < 0.0) {
			number = (int) -(val / Math.pow(
				2,
				f_exp) - .5);
			number |= 0x80000000;
		}
		else {
			number = (int) (val / Math.pow(
				2,
				f_exp) + .5);
		}
		return number;
	}

	/**
	 * Encodes a double value into the H32 format
	 * 
	 * @param val
	 * @return
	 */
	protected int encode_H32(
		final double val ) {
		int number;
		final int f_exp = -16;

		if (val < 0.0) {
			number = (int) -(val / Math.pow(
				2,
				f_exp) - .5);
			number |= 0x80000000;
		}
		else {
			number = (int) (val / Math.pow(
				2,
				f_exp) + .5);
		}
		return number;
	}

	/**
	 * Encode a float value into the BA32 format
	 * 
	 * @param val
	 *            supplied float value
	 * @return integer representing the float value encoded in the BA32 format
	 */
	protected int encode_BA32(
		final float val ) {
		double value;
		int bam;

		value = (val * 64.0 / 45.0);
		value *= Math.pow(
			2,
			23);
		bam = (int) (value + .5);
		return bam;
	}

	/**
	 * Overload of encode_BA32 method. Accepts a double, performs proper
	 * conversion
	 * 
	 * @param val
	 * @return integer representing the float (double) value encoded in the BA32
	 *         format
	 */
	protected int encode_BA32(
		final double val ) {
		double value;
		long tmpValue;

		value = (val * 64.0 / 45.0);
		value *= Math.pow(
			2,
			23);
		tmpValue = (long) (value + .5);
		if ((tmpValue < 0) || (tmpValue > Integer.MAX_VALUE)) {
			tmpValue <<= 32;
			tmpValue >>>= 32;
		}
		return (int) tmpValue;
	}

	/**
	 * Decode a B16 formatted value in a short (2 bytes)
	 * 
	 * @param bytes
	 *            encoded B16 format value
	 * @return double representing the B16 value of the supplied short
	 */
	protected double decode_B16(
		final short bytes ) {
		int b_int;
		double b_frac;
		final short f_exp = -7;
		double val;

		b_int = intAsUnsignedShort(
			(short) ((bytes & 0x7F80) >> 7));

		b_frac = (bytes & 0x007F) * Math.pow(
			2,
			f_exp);
		val = (b_int + b_frac);

		if ((bytes & 0x8000) != 0) {
			val = -(val);
		}
		return val;
	}

	/**
	 * Decode a B32 formatted value in an int (4 bytes)
	 * 
	 * @param bytes
	 *            encoded B32 format value
	 * @return double representing the B32 value of the supplied int
	 */
	protected double decode_B32(
		final int bytes ) {
		int b_int;
		double b_frac;
		final int f_exp = -23;
		double val;

		b_int = (bytes & 0x7F800000) >> 23;

		b_frac = (bytes & 0x007FFFFF) * Math.pow(
			2,
			f_exp);
		val = (b_int + b_frac);

		if ((bytes & 0x80000000) != 0) {
			val = -(val);
		}
		return val;
	}

	/**
	 * Decode an H32 formatted value in an int (4 bytes)
	 * 
	 * @param bytes
	 *            encoded H32 format value
	 * @return double representing the H32 value of the supplied int
	 */
	protected double decode_H32(
		final int bytes ) {
		int b_int;
		double b_frac;
		final int f_exp = -16;
		double val;

		b_int = (bytes & 0x7FFF0000) >> 16;

		b_frac = (bytes & 0x0000FFFF) * Math.pow(
			2,
			f_exp);
		val = (b_int + b_frac);

		if ((bytes & 0x80000000) != 0) {
			val = -(val);
		}
		return val;
	}

	/**
	 * Decode a 32 bit Binary Angle definition field from the supplied byte
	 * stream, per the CGMTI data format.
	 * 
	 * @param toBeConverted
	 * @return
	 */
	protected double decode_SA32(
		final int toBeConverted ) {
		return (toBeConverted * BAM_PRECISION * (1 / Math.pow(
			2,
			25)));
	}

	/**
	 * Decode a 32 bit signed Binary Angle definition field from the supplied
	 * byte stream, per the CGMTI data format.
	 * 
	 * @param toBeConverted
	 * @return a float quantity representing the angle
	 */
	protected double decode_BA32(
		final int toBeConverted ) {
		return (toBeConverted * BAM_PRECISION * (1 / Math.pow(
			2,
			24)));
	}

	/**
	 * Decode a 32 bit signed Binary Angle definition field from the supplied
	 * byte stream, per the CGMTI data format. -- Assumes that the supplied long
	 * is representing an unsigned int.
	 * 
	 * @param toBeConverted
	 * @return
	 */
	protected double decode_BA32(
		final long toBeConverted ) {
		return (toBeConverted * BAM_PRECISION * (1 / Math.pow(
			2,
			24)));
	}

	/**
	 * Decode a 16 bit signed Binary Angle definition field from the supplied
	 * byte stream, per the CGMTI data format.
	 * 
	 * @param toBeConverted
	 * @return
	 */
	protected double decode_SA16(
		final short toBeConverted ) {
		return (toBeConverted * BAM_PRECISION * (1 / Math.pow(
			2,
			9)));
	}

	/**
	 * Decode a 16 bit Binary Angle definition field from the supplied byte
	 * stream, per the CGMTI data format.
	 * 
	 * @param toBeConverted
	 * @return
	 */
	protected double decode_BA16(
		final short toBeConverted ) {
		return (toBeConverted * BAM_PRECISION * (1 / Math.pow(
			2,
			8)));
	}

	/**
	 * Decode a 16 bit Binary Angle definition field from the supplied byte
	 * stream, per the CGMTI data format. -- assumes the supplied integer
	 * represents an unsigned short
	 * 
	 * @param toBeConverted
	 * @return
	 */
	protected double decode_BA16(
		final int toBeConverted ) {
		return (toBeConverted * BAM_PRECISION * (1 / Math.pow(
			2,
			8)));
	}

	/**
	 * Encodes an 32 bit float point value to a SAM angle measurement and then
	 * stores into an int, per the CGMTI data format.
	 * 
	 * @param toEncode
	 * @return encoded value
	 */
	protected int encode_SA32(
		final float toEncode ) {
		double value;
		int bam;

		value = (toEncode * 64.0 / 45.0);
		value *= Math.pow(
			2,
			24);
		bam = (int) (value < 0 ? value - .5 : value + .5);

		return bam;
	}

	/**
	 * Encodes an 32 bit float point value to a SAM angle measurement and then
	 * stores into an int, per the CGMTI data format.
	 * 
	 * @param toEncode
	 * @return encoded value
	 */
	protected int encode_SA32(
		final double toEncode ) {
		double value;
		int bam;

		value = (toEncode * 64.0 / 45.0);
		value *= Math.pow(
			2,
			24);
		bam = (int) (value < 0 ? value - .5 : value + .5);
		return bam;
	}

	/**
	 * Encodes an 16 bit float (note, float still 32 bits) point value to a SAM
	 * angle measurement and then stores into an int, per the CGMTI data format.
	 * 
	 * @param toEncode
	 * @return encoded value
	 */
	protected int encode_SA16(
		final float toEncode ) {
		float value;
		int bam;

		value = (float) (toEncode * 64.0 / 45.0);
		value *= Math.pow(
			2,
			24);
		bam = (int) (value < 0 ? value - .5 : value + .5);

		return bam;
	}

	/**
	 * Encodes an 16 bit float (note, float still 32 bits) point value to a SAM
	 * angle measurement and then stores into an int, per the CGMTI data format.
	 * 
	 * @param toEncode
	 * @return encoded value
	 */
	protected int encode_SA16(
		final double toEncode ) {
		float value;
		int bam;

		value = (float) (toEncode * 64.0 / 45.0);
		value *= Math.pow(
			2,
			24);
		bam = (int) (value < 0 ? value - .5 : value + .5);

		return bam;
	}

	/**
	 * Encodes an 16 bit float (only the lower bits) point value to a BAM angle
	 * measurement and then stores into a short, per the CGMTI data format.
	 * 
	 * @param toEncode
	 * @return
	 */
	protected short encode_BA16(
		final float toEncode ) {
		double value;
		int bam;

		value = (toEncode * 64.0 / 45.0);
		value *= Math.pow(
			2,
			7);
		bam = (int) (value + .5);
		return (short) bam;
	}

	/**
	 * Encodes an 16 bit float (only the lower bits) point value to a BAM angle
	 * measurement and then stores into a short, per the CGMTI data format.
	 * 
	 * @param toEncode
	 * @return
	 */
	protected short encode_BA16(
		final double toEncode ) {
		double value;
		int bam;

		value = (toEncode * 64.0 / 45.0);
		value *= Math.pow(
			2,
			7);
		bam = (int) (value + .5);
		return (short) bam;
	}

	/**
	 * Overload of read_CGMTI_Config()
	 * 
	 * @return
	 * @throws IOException
	 */
	protected static boolean read_CGMTI_Config()
		throws IOException {
		if (getConfigFileName() == null) {
			throw new STANAG4607RuntimeException(
				"Configuration file name for 4607 Data not known");
		}
		else {
			return read_CGMTI_Config(
				getConfigFileName());
		}
	}

	/**
	 * Read the values from the CGMTI configuration file into a static memory
	 * for use throughout the conversion. Note, not used, have not received any
	 * specifications about this file. Duplicated for later implementation as
	 * suggested by original C++ code.
	 * 
	 * @param filename
	 *            : Filename of the NATOEX to CGMTI configuration file.
	 * @return
	 * @throws IOException
	 */
	private static boolean read_CGMTI_Config(
		final String filename )
			throws IOException {
		File fptr;
		FileReader fReader;
		BufferedReader bReader;

		try {
			if (!isConfigured()) {

				fptr = new File(
					filename);

				if ((fptr == null) || (!fptr.canRead())) {
					return (false);
				}

				fReader = new FileReader(
					fptr);

				bReader = new BufferedReader(
					fReader);

				nationality = bReader.readLine();
				codeword = bReader.readLine();
				control = bReader.readLine();
				mission_id = bReader.readLine();
				mission_plan = bReader.readLine();
				flight_plan = bReader.readLine();
				platform_config = bReader.readLine();
				p_detection = Short.parseShort(
					bReader.readLine());
				p_false_alarm = Short.parseShort(
					bReader.readLine());
				recipient_id = bReader.readLine();
				sensor_model = bReader.readLine();
				radar_priority = Short.parseShort(
					bReader.readLine());
				revisit_interval = Short.parseShort(
					bReader.readLine());
				terrain_model = bReader.readLine();
				geoid_model = bReader.readLine();
				version_id = bReader.readLine();
				bReader.close();
				fReader.close();
				setConfigured(
					true);
			}
			return (isConfigured());
		}
		catch (final IOException ioe) {
			throw ioe;
		}
	}

	/**
	 * Convert the NATOEX radial velocity quality to the CGMTI target radial
	 * velocity to the equivalent. NOTE: These two values map in reverse order
	 * (EX: 0 .. 42 = CGMTI: 255 .. 0 )
	 * 
	 * @param rad_vel_qual
	 *            : NATOEX radial velocity quality
	 * @return: CGMTI target radial velocity, converted.
	 */
	public static short ex2CGMTI_Tgt_Rad_Vel(
		final byte rad_vel_qual ) {
		return (short) ((-CGMTI_RAD_VEL_GOOD * (rad_vel_qual - GOOD_RVEL_QUAL)) / GOOD_RVEL_QUAL);
	}

	/* DEFINITIONS: Existence Mask values */
	public static final byte MSK0 = (byte) 0x80;
	public static final byte MSK1 = (byte) 0x40;
	public static final byte MSK2 = (byte) 0x20;
	public static final byte MSK3 = (byte) 0x10;
	public static final byte MSK4 = (byte) 0x08;
	public static final byte MSK5 = (byte) 0x04;
	public static final byte MSK6 = (byte) 0x02;
	public static final byte MSK7 = (byte) 0x01;

	/* some helpful mask for bit manipulations */

	private static int tmpIntValue = 0xFFFF;
	private static long tmpValue = 0xFFFFFFFF;

	static {
		tmpValue <<= 32;
		tmpValue >>>= 32;

		tmpIntValue <<= 16;
		tmpIntValue >>>= 16;
	}

	public static final byte BITS8ALL1S = (byte) 0xFF;
	public static final int BITS16ALL1S = tmpIntValue;
	public static final long BITS32ALL1S = tmpValue;

	/* DEFINITIONS: Exploitation Class Existance Mask Settings */
	public static final byte[] SITUATIONAWARENESSMASK = {
		(byte) 0xFF,
		(byte) 0xC7,
		(byte) 0x1F,
		(byte) 0xC2,
		(byte) 0x60,
		(byte) 0x06,
		(byte) 0x00,
		(byte) 0x00
	};
	public static final byte[] AREATARGETINGMASK = {
		(byte) 0xFF,
		(byte) 0xC7,
		(byte) 0x1F,
		(byte) 0xC2,
		(byte) 0x70,
		(byte) 0x06,
		(byte) 0x00,
		(byte) 0x00
	};
	public static final byte[] BASICTRACKINGMASK = {
		(byte) 0xFF,
		(byte) 0xC7,
		(byte) 0x1F,
		(byte) 0xFA,
		(byte) 0x7F,
		(byte) 0x86,
		(byte) 0x00,
		(byte) 0x00
	};
	public static final byte[] PRECISIONTRACKINGMASK = {
		(byte) 0xFF,
		(byte) 0x3F,
		(byte) 0xFF,
		(byte) 0xFF,
		(byte) 0x9F,
		(byte) 0xFE,
		(byte) 0x00,
		(byte) 0x00
	};

	/*
	 * DEFINITIONS: byte length of dwell fields corresponding to all mask bits
	 */
	public static final byte[] DWELL_FIELD_SIZE_PER_MASK_BIT = {
		2,
		2,
		1,
		2,
		4,
		4,
		4,
		4,
		4,
		4,
		4,
		4,
		2,
		2,
		4,
		1,
		1,
		2,
		2,
		2,
		2,
		2,
		4,
		4,
		2,
		2,
		2,
		2,
		2,
		1,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0
	};

	/*
	 * DEFINITIONS: byte length of target fields corresponding to all mask bits
	 */
	public static final byte[] TARGET_FIELD_SIZE_PER_MASK_BIT = {
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		2,
		4,
		4,
		2,
		2,
		2,
		2,
		2,
		1,
		1,
		1,
		2,
		2,
		1,
		2,
		1,
		4,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0
	};

	public static final byte[] saMask = SITUATIONAWARENESSMASK;
	public static final byte[] atMask = AREATARGETINGMASK;
	public static final byte[] btMask = BASICTRACKINGMASK;
	public static final byte[] ptMask = PRECISIONTRACKINGMASK;
	public static final byte[][] EX_CGMTI_Dwell_Mask = {
		saMask,
		atMask,
		btMask,
		ptMask
	};

	/* byte length of dwell fields corresponding to all mask bits */
	public static final byte[] EX_CGMTI_Dwell_Field_Size = DWELL_FIELD_SIZE_PER_MASK_BIT;

	/* byte length of target fields corresponding to all mask bits */
	public static final byte[] EX_CGMTI_Target_Field_Size = TARGET_FIELD_SIZE_PER_MASK_BIT;

	/* General Constants */
	public static final int CGMTI_SCALE_FACTOR = 2048;
	/* Allows up to 16 degree delta lat & lon */
	public static final int CGMTI_EDITION = 1;
	public static final int CGMTI_VERSION = 1;
	public static final int RET_ERROR = -1;
	public static final int RET_OK = 1;

	/* byte counts of cgmti segments, variations noted */
	public static final int CGMTI_MISSION_SIZE = 39;
	public static final int CGMTI_DWELL_SIZE = 86; /* MAX SIZE */
	public static final int CGMTI_TARGET_SIZE = 35; /* MAX SIZE */
	public static final int CGMTI_SHDR_SIZE = 5;
	public static final int CGMTI_HDR_SIZE = 32;
	public static final int CGMTI_JOB_SIZE = 68;
	public static final int CGMTI_JOB_REQ_SIZE = 79;
	public static final int CGMTI_JOB_ACK_SIZE = 70;
	public static final int CGMTI_FREETEXT_SIZE = 65535; /* MAX SIZE */
	public static final int CGMTI_HRR_SIZE = 21; /* PLUS RECORDS!!! */
	public static final int CGMTI_TEST_STAT_SIZE = 14;
	public static final int CGMIT_PHISTORY_SIZE = 21; /* PLUS RECORDS!!! */
	public static final int CGMTI_PLAT_LOC_SIZE = 23;

	/*
	 * Define MTI Constants
	 */
	public static final int MAX_TGT_RECS_V1 = 45;
	public static final int MAX_MTI_RPTS = 42;
	public static final int GOOD_RVEL_QUAL = 42;

	public static final double BAM_PRECISION = 1.40625;

	/* CGMTI Radar Modes ( fields J13, R12, A15 ) */
	// #ifdef CGMTISOURCE
	// char *rmText[] =
	// {"UNSPECIFIED","MTI","HRR","UHRR","HUR","FTI","JS_AC_SATC","JS_AC",
	// "JS_SATC"
	// ,"JS_AP_SATC","JS_AP","JS_MRSS","JS_LRSS","JS_WAS_GRCA","JS_WAS_RRCA"
	// ,"JS_AP_WITH_TRK"
	// ,"JS_AC_WITH_TRK","AS_AIP_WAMTI","AS_AIP_CRESS","AS_AIP_MRS"
	// ,"AS_AIP_HRS",
	// "AS_AIP_PTIMG","AS_AIP_SMTI","AS_AIP_RPTIMG","AS_AIP_MONOCAL"
	// ,"AS_2_SEARCH"
	// ,"AS_2_EMTI_WFS","AS_2_EMTI_NFS","AS_2_EMTI_AUGS","AS_2_WAMTI"
	// ,"TUAV_GMTI_PPI"
	// ,"TUAV_GMTI_EXP","ARL_M_NSS","ARL_M_SBS","ARL_M_WA","GRCA"
	// ,"RRCA","SECTOR_SEARCH"
	// ,"HORIZON_BASIC","HORIZON_HSENS","HORIZON_BURN","CRESO_ACQ"
	// ,"CRESO_COUNT",
	// "ASTOR_SPOT","ASTOR_SWATH1","ASTOR_SWATH2","ASTOR_MTIEXO","ASTOR_MTIENDO"
	// ,"TEST_STATUS","INVALID"};
	// #else
	// extern char *rmText[];
	// #endif
	// #define RM_TEXT(code)
	// (rmText[code==100?48:code>95?49:code>=91?code-48:code>88?49:code>=81?code-
	// 46
	// :code>65?49:code>=61?code-31:code>55?49:code>=51?code-26:code>38?49:code
	// >=31?code-14:code>21?49:code>=11?code-5:code>5?49:code<0?49:code])
	public static final byte RM_UNSPECIFIED = 0;
	public static final byte RM_MTI = 1;
	public static final byte RM_HRR = 2;
	public static final byte RM_UHRR = 3;
	public static final byte RM_HUR = 4;
	public static final byte RM_FTI = 5;
	public static final byte RM_JS_AC_SATC = 11;
	public static final byte RM_JS_AC = 12;
	public static final byte RM_JS_SATC = 13;
	public static final byte RM_JS_AP_SATC = 14;
	public static final byte RM_JS_AP = 15;
	public static final byte RM_JS_MRSS = 16;
	public static final byte RM_JS_LRSS = 17;
	public static final byte RM_JS_WAS_GRCA = 18;
	public static final byte RM_JS_WAS_RRCA = 19;
	public static final byte RM_JS_AP_WITH_TRK = 20;
	public static final byte RM_JS_AC_WITH_TRK = 21;
	public static final byte RM_AS_AIP_WAMTI = 31;
	public static final byte RM_AS_AIP_CRESS = 32;
	public static final byte RM_AS_AIP_MRS = 33;
	public static final byte RM_AS_AIP_HRS = 34;
	public static final byte RM_AS_AIP_PTIMG = 35;
	public static final byte RM_AS_AIP_SMTI = 36;
	public static final byte RM_AS_AIP_RPTIMG = 37;
	public static final byte RM_AS_AIP_MONOCAL = 38;
	public static final byte RM_AS_2_SEARCH = 51;
	public static final byte RM_AS_2_EMTI_WFS = 52;
	public static final byte RM_AS_2_EMTI_NFS = 53;
	public static final byte RM_AS_2_EMTI_AUGS = 54;
	public static final byte RM_AS_2_WAMTI = 55;
	public static final byte RM_TUAV_GMTI_PPI = 61;
	public static final byte RM_TUAV_GMTI_EXP = 62;
	public static final byte RM_ARL_M_NSS = 63;
	public static final byte RM_ARL_M_SBS = 64;
	public static final byte RM_ARL_M_WA = 65;
	public static final byte RM_GRCA = 81;
	public static final byte RM_RRCA = 82;
	public static final byte RM_SECTOR_SEARCH = 83;
	public static final byte RM_HORIZON_BASIC = 84;
	public static final byte RM_HORIZON_HSENS = 85;
	public static final byte RM_HORIZON_BURN = 86;
	public static final byte RM_CRESO_ACQ = 87;
	public static final byte RM_CRESO_COUNT = 88;
	public static final byte RM_ASTOR_SPOT = 91;
	public static final byte RM_ASTOR_SWATH1 = 92;
	public static final byte RM_ASTOR_SWATH2 = 93;
	public static final byte RM_ASTOR_MTIEXO = 94;
	public static final byte RM_ASTOR_MTIENDO = 95;
	public static final byte RM_TEST_STATUS = 100;

	/* DEFINITIONS: Target Radial Velocity Values */
	public static final byte CGMTI_RAD_VAL_BAD = 0;
	public static final byte CGMTI_RAD_VEL_GOOD = (byte) 0xff;

	public static final byte SA_POLAR = 0;
	public static final byte SA_RECTANGULAR = 1;
	public static final byte SA_AIMPOINT = 2;
	public static final byte SA_NONE = 3;

	/* DEFINITIONS: Security Code */
	public static final String scText[] = {
		"NONE",
		"NOCONTRACT",
		"ORCON",
		"PROPIN",
		"WNINTEL",
		"USONLY",
		"LIMDIS",
		"FOUO",
		"EFTO",
		"LIMOFFUSE",
		"NONCOMPARTMENT",
		"SPECIALCONTROL",
		"SPECIALINTEL",
		"WARNING",
		"RESERVED1",
		"RESERVED2",
		"RESERVED3",
		"INVALID"
	};

	// int scindex(unsigned short code) {unsigned short i;for(i=0;i<16;i++)
	// if(1<<i & code) return(i+1); return(0);}
	// #else
	// extern char *scText[];
	// extern scindex(unsigned short code);

	// #define SC_TEXT(code) (scText[scindex(code)])
	public static final int SC_NONE = 0x0000;
	public static final int SC_NOCONTRACT = 0x0001;
	public static final int SC_ORCON = 0x0002;
	public static final int SC_PROPIN = 0x0004;
	public static final int SC_WNINTEL = 0x0008;
	public static final int SC_USONLY = 0x0010;
	public static final int SC_LIMDIS = 0x0020;
	public static final int SC_FOUO = 0x0040;
	public static final int SC_EFTO = 0x0080;
	public static final int SC_LIMOFFUSE = 0x0100;
	public static final int SC_NONCOMPARTMENT = 0x0200;
	public static final int SC_SPECIALCONTROL = 0x0400;
	public static final int SC_SPECIALINTEL = 0x0800;
	public static final int SC_WARNING = 0x1000;
	public static final int SC_RESERVED1 = 0x2000;
	public static final int SC_RESERVED2 = 0x4000;
	public static final int SC_RESERVED3 = 0x8000;

	/* DEFINITIONS: Exercise id */
	public static final String exText[] = {
		"OPREAL",
		"OPSIMULATED",
		"OPSYNTHESIZED",
		"EXREAL",
		"EXSIMULATED",
		"EXSYNTHESIZED",
		"INVALID"
	};

	// #else
	// extern char *exText[];
	// #endif
	// #define EX_TEXT(code)
	// (exText[code<0?6:code<=2?code:code>130?6:code>=128?code-125:6])
	public static final int EX_OPREAL = 0;
	public static final int EX_OPSIMULATED = 1;
	public static final int EX_OPSYNTHESIZED = 2;
	public static final int EX_EXREAL = 128;
	public static final int EX_EXSIMULATED = 129;
	public static final int EX_EXSYNTHESIZED = 130;

	public static final String pltText[] = {
		"UNIDENT",
		"ACS",
		"ARL_M",
		"ASTOR",
		"CRESO",
		"GBLHAWK",
		"HORIZON",
		"E_8JSTARS",
		"P_3C",
		"PREDATOR",
		"RADARSAT2",
		"U_2",
		"MC2A",
		"INVALID"
	};

	// extern char *pltText[];
	// #endif
	// #define PLT_TEXT(code) (pltText[code<0?13:code>12?13:code])
	public static final byte PLT_UNIDENTIFIED = 0;
	public static final byte PLT_ACS = 1;
	public static final byte PLT_ARL_M = 2;
	public static final byte PLT_ASTOR = 3;
	public static final byte PLT_CRESO = 4;
	public static final byte PLT_GLOBALHAWK = 5;
	public static final byte PLT_HORIZON = 6;
	public static final byte PLT_E_8JSTARS = 7;
	public static final byte PLT_P_3C = 8;
	public static final byte PLT_PREDATOR = 9;
	public static final byte PLT_RADARSAT2 = 10;
	public static final byte PLT_U_2 = 11;
	public static final byte PLT_MC2A = 12;

	/*
	 * CGMTI Ground Platform Type Definitions - not identified in 10/17/2002
	 * 4607 ratification draft
	 */
	public static final byte PLT_AIRFORCE = 101;
	public static final byte PLT_ARLM_GROUND = 102;
	public static final byte PLT_ARMY = 103;
	public static final byte PLT_ASTOR_GSM = 104;
	public static final byte PLT_CRESO_GSM = 105;
	public static final byte PLT_FAST = 106;
	public static final byte PLT_GH_CONTROL = 107;
	public static final byte PLT_HORIZON_GSM = 108;
	public static final byte PLT_INTEL_CENTER = 109;
	public static final byte PLT_JSTARS_CGS = 110;
	public static final byte PLT_JSIPSN = 111;
	public static final byte PLT_MIST_TYPE = 112;
	public static final byte PLT_NAVY = 113;
	public static final byte PLT_TES = 114;
	public static final byte PLT_UNK_GROUND = (byte) 200;

	public static final String snsrText[] = {
		"UNIDENTIFIED",
		"ACS",
		"HISAR",
		"ASTOR",
		"CRESO",
		"GLOBALHAWK",
		"HORIZON",
		"APY_3JSTARS",
		"APY_6",
		"LYNX",
		"RADARSAT2",
		"ASARS_2A",
		"TESAR",
		"RTIP",
		"NOSTATEMENT",
		"INVALID"
	};

	// #endif
	// #define SNSR_TEXT(code)
	// (snsrText[code<0?15:code==255?14:code>13?15:code])
	public static final byte SNSR_UNIDENTIFIED = 0;
	public static final byte SNSR_ACS = 1;
	public static final byte SNSR_HISAR = 2;
	public static final byte SNSR_ASTOR = 3;
	public static final byte SNSR_CRESO = 4;
	public static final byte SNSR_GLOBALHAWK = 5;
	public static final byte SNSR_HORIZON = 6;
	public static final byte SNSR_APY_3JSTARS = 7;
	public static final byte SNSR_APY_6 = 8;
	public static final byte SNSR_LYNX = 9;
	public static final byte SNSR_RADARSAT2 = 10;
	public static final byte SNSR_ASARS_2A = 11;
	public static final byte SNSR_TESAR = 12;
	public static final byte SNSR_RTIP = 13;
	public static final byte SNSR_NOSTATEMENT = (byte) 0xFF;

	/**
	 * For future use when the configuration file is read as part of normal
	 * usage.
	 * 
	 * @return
	 */
	public static boolean isConfigured() {
		return configured;
	}

	/**
	 * For future use when the configuration file is read as part of normal
	 * usage.
	 * 
	 * @param b
	 */
	public static void setConfigured(
		final boolean b ) {
		configured = b;
	}

	/**
	 * @return
	 */
	public static String getConfigFileName() {
		return configFileName;
	}

	/**
	 * For future use when the configuration file is read as part of normal
	 * usage.
	 * 
	 * @param string
	 */
	public static void setConfigFileName(
		final String string ) {
		configFileName = string;
	}

	/**
	 * Helper methods for conversions. Takes a 4 byte (as int) and represents
	 * the value as a long for unsigned purposes
	 * 
	 * @param toBeConverted
	 * @return
	 */
	public static long longAsUnsignedInt(
		final int toBeConverted ) {
		if (toBeConverted < 0) {
			return toBeConverted & BITS32ALL1S;
		}
		else {
			return toBeConverted;
		}
	}

	/**
	 * Helper methods for conversions. Takes a 2 byte (as short) and represents
	 * the value as an int for unsigned purposes
	 * 
	 * @param toBeConverted
	 * @return
	 */
	public static int intAsUnsignedShort(
		final short toBeConverted ) {
		if (toBeConverted < 0) {
			return toBeConverted & BITS16ALL1S;
		}
		else {
			return toBeConverted;
		}
	}

	static public short byteAsShort(
		final byte myByte ) {
		// WAL MOD 12/08/04 Need to mask off high byte, otherwise negative bit
		// carries through. We need
		// to treat the byte as unsigned
		return (short) (myByte & 255);
	}

	/**
	 * convert the supplied integer into a byte
	 * 
	 * @param myInt
	 * @return
	 */
	static public byte shortAsByte(
		final short myInt ) {
		if (myInt > Byte.MAX_VALUE) {
			throw new RuntimeException(
				"Integer value " + myInt + " is " + "too large to convert to a byte");
		}
		else {
			return new Integer(
				myInt).byteValue();
		}
	}
}
