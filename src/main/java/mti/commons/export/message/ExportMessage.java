package mti.commons.export.message;

import java.util.List;

import mti.commons.export.model.ExportParameters;
import mti.commons.export.model.ExportProfile;

public class ExportMessage {
	public String service; //MTI, Track
	public boolean success; //Success, Failure
	public String comment; //this is failure reason for failed messages
	public String filePath;
	public ExportProfile exportProfile;
	public ExportParameters exportParameters;
	public String humanFriendlyGeoBounds;
	public List<String> sourceFileNames;
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public ExportProfile getExportProfile() {
		return exportProfile;
	}
	public void setExportProfile(ExportProfile exportProfile) {
		this.exportProfile = exportProfile;
	}
	public ExportParameters getExportParameters() {
		return exportParameters;
	}
	public void setExportParameters(ExportParameters exportParameters) {
		this.exportParameters = exportParameters;
	}
	public String getHumanFriendlyGeoBounds() {
		return humanFriendlyGeoBounds;
	}
	public void setHumanFriendlyGeoBounds(String humanFriendlyGeoBounds) {
		this.humanFriendlyGeoBounds = humanFriendlyGeoBounds;
	}
	public List<String> getSourceFileNames() {
		return sourceFileNames;
	}
	public void setSourceFileNames(List<String> sourceFileNames) {
		this.sourceFileNames = sourceFileNames;
	}
}
