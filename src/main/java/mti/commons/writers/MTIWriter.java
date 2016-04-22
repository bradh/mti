package mti.commons.writers;

import java.io.IOException;

import mti.commons.containers.MissionContainer;

public interface MTIWriter {

	public void setDirectory(String directory) throws IOException;

	public String getFilename(String rootname);
	
	public void writeMission(MissionContainer mission, String rootname) throws IOException;

}
