package mti.commons.track;

import java.io.OutputStream;

import mti.commons.model.track.TrackSet;

public interface TrackEncoder {
	public void setOutputStream(OutputStream os);
	
	public void encode(TrackSet set);
	public void encode(TrackMessage msg);
//	TODO: public void encodeInit(TrackMessage msg);
//	TODO: public void encodeAddEvent(TrackMessage msg, TrackEvent event);
//	TODO: public void encodeFinal(TrackMessage msg);
}
