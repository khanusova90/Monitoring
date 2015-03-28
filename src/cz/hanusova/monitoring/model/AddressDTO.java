package cz.hanusova.monitoring.model;

public class AddressDTO {

	private String postalCode;
	private String city;
	private String subLocality;
	private String number;
	private String latitude;
	private String longitude;
	private String error;

	/*
	 * Getters and setters
	 */
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubLocality() {
		return subLocality;
	}

	public void setSubLocality(String street) {
		this.subLocality = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String lattitude) {
		this.latitude = lattitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
