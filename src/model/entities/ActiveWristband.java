package model.entities;

import java.sql.Time;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "active_wristband")
public class ActiveWristband {
	
	@Id
	@Column(name = "bar_code", length = 13)
	private long barCode;
	
	@OneToOne
	@JoinColumn(name = "activator_id", referencedColumnName = "id")
	private AppUser activatorId;

	//@CreationTimestamp
	@Column(name = "activating_date_time")
	private LocalDateTime activatingDateTime;

	@Column(name = "selling_price", length = 10)
	private int sellingPrice;
	
	@Column(name = "discount_percent", length = 10)
	private int discountPercent;
	
	@Column(name = "color", length = 10)
	private String color;

	@Column(name = "registration_date_time")
	private LocalDateTime registrationDateTime;
	
	@Column(name = "bad_weather_elapsed_time")
	private Time badWeatherElapsedTime;

	@OneToOne
	@JoinColumn(name = "registrar_id", referencedColumnName = "id")
	private AppUser registrarId;

	public ActiveWristband() {
	}

	public ActiveWristband(long barCode, AppUser activatorId, int sellingPrice, int discountPercent, String color,
			LocalDateTime registrationDateTime, AppUser registrarId) {
		super();
		this.barCode = barCode;
		this.activatorId = activatorId;
		this.sellingPrice = sellingPrice;
		this.discountPercent = discountPercent;
		this.color = color;
		this.registrationDateTime = registrationDateTime;
		this.registrarId = registrarId;
		
		this.badWeatherElapsedTime = null;
	}

	public long getBarCode() {
		return barCode;
	}
	
	public void setBarCode(long barCode) {
		this.barCode = barCode;
	}	

	public AppUser getActivatorId() {
		return activatorId;
	}

	public void setActivatorId(AppUser activatorId) {
		this.activatorId = activatorId;
	}

	public LocalDateTime getActivatingDateTime() {
		return activatingDateTime;
	}	

	public void setActivatingDateTime(LocalDateTime activatingDateTime) {
		this.activatingDateTime = activatingDateTime;
	}

	public int getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(int discountPercent) {
		this.discountPercent = discountPercent;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public Time getBadWeatherElapsedTime() {
		return badWeatherElapsedTime;
	}

	public void setBadWeatherElapsedTime(Time badWeatherElapsedTime) {
		this.badWeatherElapsedTime = badWeatherElapsedTime;
	}

	public AppUser getRegistrarId() {
		return registrarId;
	}

	public void setRegistrarId(AppUser registrarId) {
		this.registrarId = registrarId;
	}

	@Override
	public String toString() {
		return "ActiveWristband [barCode=" + barCode + ", activatorId=" + activatorId + ", activatingDateTime="
				+ activatingDateTime + ", sellingPrice=" + sellingPrice + ", discountPercent=" + discountPercent
				+ ", color=" + color + ", registrationDateTime=" + registrationDateTime + ", badWeatherElapsedTime="
				+ badWeatherElapsedTime + ", registrarId=" + registrarId + "]";
	}	
}
