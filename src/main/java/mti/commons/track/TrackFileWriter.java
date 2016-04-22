package mti.commons.track;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mti.commons.model.track.TrackSet;

public class TrackFileWriter implements TrackWriter {

	private String fileName;
	private String dirName;
	private File destFile;
	private TrackEncoder encoder;
	
	@Override
	public void setEncoder(TrackEncoder encoder) {
		this.encoder = encoder;
	}
	
	@Override
	public void initialize(TrackSet set) {
		//TODO: add logic if dirName and Filename are not set
		File dir = new File(dirName);
		dir.mkdirs();
		destFile = new File(fileName); 
	}

	@Override
	public void write(TrackSet set) throws IOException {
		FileOutputStream fos = new FileOutputStream(destFile, true);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		encoder.setOutputStream(bos);
		encoder.encode(set);
		bos.flush();
		fos.close();
	}

	@Override
	public void write(TrackMessage msg) throws IOException {
		FileOutputStream fos = new FileOutputStream(destFile, true);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		encoder.setOutputStream(bos);
		encoder.encode(msg);
		bos.flush();
		fos.close();
	}

	public void setDirName(String dir) {
		dirName = dir;
	}
	
	public void setFilename(String filename) {
		this.fileName = filename;
		destFile = new File(this.fileName); 
	}

	public String getFilename() {
		return this.fileName;
	}

	@Override
	public void finalize(TrackSet set) {}

}
