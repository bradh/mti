package mti.commons.track;

import java.io.IOException;

import mti.commons.model.track.TrackSet;

public interface TrackWriter {
	public void setEncoder(TrackEncoder encoder) ;
	
	public void initialize(TrackSet set);
	public void write(TrackSet set) throws IOException;
	public void write(TrackMessage msg) throws IOException;
//	public void write(TrackSet set, int index, TrackMessage msg) throws IOException;
//	public void writeInit(TrackSet set, TrackMessage msg) throws IOException;
//	public void writeAdd(TrackSet set, TrackMessage msg, TrackEvent event) throws IOException;
//	public void writeFinal(TrackSet set, TrackMessage msg) throws IOException;
	public void finalize(TrackSet set);
	
}
