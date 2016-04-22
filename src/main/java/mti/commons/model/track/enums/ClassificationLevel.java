package mti.commons.model.track.enums;

//STANAG 4676
/**
* Enumeration for Classification Level
*/
public enum ClassificationLevel {
	TOP_SECRET("TOP_SECRET"),
	SECRET("SECRET"),
	CONFIDENTIAL("CONFIDENTIAL"),
	RESTRICTED("RESTRICTED"),
	UNCLASSIFIED("UNCLASSIFIED");

	private String value;

	ClassificationLevel() {
		this.value = "TOP_SECRET";
	}
	
	ClassificationLevel(final String value) {
		this.value = value;
	}

	public static ClassificationLevel fromString(String value) {
		value = value.replace(' ', '_');  //accept underscores or spaces
		for(final ClassificationLevel item : ClassificationLevel.values()) {
			if (item.toString().equals(value)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return value;
	}	
}