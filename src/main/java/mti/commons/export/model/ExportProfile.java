package mti.commons.export.model;

public class ExportProfile {
	private String userName = "anonymous";
	private String emailAddress;
	private Short limdisMask = 0;
	private String downloadType; //HTTP (Email), FTP
	private FtpProfile ftpProfile;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public Short getLimdisMask() {
		return limdisMask;
	}
	public void setLimdisMask(Short limdisMask) {
		this.limdisMask = limdisMask;
	}
	public String getDownloadType() {
		return downloadType;
	}
	public void setDownloadType(String downloadType) {
		this.downloadType = downloadType;
	}
	public FtpProfile getFtpProfile() {
		return ftpProfile;
	}
	public void setFtpProfile(FtpProfile ftpProfile) {
		this.ftpProfile = ftpProfile;
	}
}
