package model;

public class PreparedWristband {

	private long barCode;
	private String color;
	private boolean barCodeOn;
	
	public PreparedWristband(String color) {
		this.color = color;
		this.barCodeOn = false;
	}	
	
	public PreparedWristband(long barcode, String color) {
		this.barCode = barcode;
		this.color = color;
		this.barCodeOn = false;
	}	

	public long getBarCode() {
		return barCode;
	}

	public void setBarCode(long barCode) {
		this.barCode = barCode;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isBarCodeOn() {
		return barCodeOn;
	}

	public void setBarCodeOn(boolean barCodeIsOn) {
		this.barCodeOn = barCodeIsOn;
	}	
}
