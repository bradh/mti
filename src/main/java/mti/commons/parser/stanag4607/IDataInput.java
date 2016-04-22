package mti.commons.parser.stanag4607;

import java.io.DataInput;
import java.io.IOException;

public interface IDataInput extends DataInput {
	public int read(byte b[]) throws IOException;
	public int read(byte b[], int off, int len) throws IOException;
}
