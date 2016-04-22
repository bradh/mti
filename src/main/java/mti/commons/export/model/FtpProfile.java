package mti.commons.export.model;

import mti.commons.export.model.FtpProtocol;

public class FtpProfile {

	//FTP Information
	private String ftpHost;
	private Integer ftpPort;
	private String ftpUserName;
	private String ftpPassword;
	private FtpProtocol ftpProtocol; //FTP, SFTP
	private String ftpDirectory;
	
	public String getFtpHost() {
		return ftpHost;
	}
	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}
	public Integer getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpUserName() {
		return ftpUserName;
	}
	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public FtpProtocol getFtpProtocol() {
		return ftpProtocol;
	}
	public void setFtpProtocol(FtpProtocol ftpProtocol) {
		this.ftpProtocol = ftpProtocol;
	}
	public String getFtpDirectory() {
		return ftpDirectory;
	}
	public void setFtpDirectory(String ftpDirectory) {
		this.ftpDirectory = ftpDirectory;
	}
}
