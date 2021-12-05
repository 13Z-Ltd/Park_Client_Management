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
@Table(name = "closed_wristband")
public class ClosedWristband {

	@Id
	@Column(name = "bar_code", length = 13)
	private long barCode;
	
	@Column(name = "color", length = 10)
	private String color;
	
	@Column(name = "selling_price", length = 10)
	private int sellingPrice;

	@Column(name = "discount_percent", length = 4)
	private int discountPercent;
	
	
	@Column(name = "activating_date_time")
	private LocalDateTime activatingDateTime;
	
	@Column(name = "closing_date_time")
	private LocalDateTime closeingDateTime;
	
	@Column(name = "time_spent")
	private Time timeSpent;
	
	@Column(name = "bad_weather_time")
	private Time badWeatherTime;

	@OneToOne
	@JoinColumn(name = "activator_id", referencedColumnName = "id")
	private AppUser activatorId;

	@OneToOne
	@JoinColumn(name = "dressing_id", referencedColumnName = "id")
	private AppUser dressingId;
	
	@OneToOne
	@JoinColumn(name = "registrar_id", referencedColumnName = "id")
	private AppUser registrarId;
	
	@Column(name = "registration_date_time")
	private LocalDateTime registrationDateTime;
	
	@Column(name = "overpayment_amount", length = 6)
	private int overpaymentAmount;
	
	@Column(name = "overpayment_required")
	private boolean overpaymentRequired;	

	public ClosedWristband() {		
	}
	
	public ClosedWristband(ActiveWristband activeWristband, 
			LocalDateTime closeingDateTime, Time timeSpent, Time badWeatherTime, AppUser dressingId, int overpaymentAmount) { //, boolean overpaymentRequired) {
		super();
		this.barCode = activeWristband.getBarCode();
		this.color = activeWristband.getColor();
		this.sellingPrice = activeWristband.getSellingPrice();
		this.discountPercent = activeWristband.getDiscountPercent();
		this.activatingDateTime = activeWristband.getActivatingDateTime();		
		this.activatorId = activeWristband.getActivatorId();		
		this.registrarId = activeWristband.getRegistrarId();
		this.registrationDateTime = activeWristband.getRegistrationDateTime();
		
		this.closeingDateTime = closeingDateTime;
		this.timeSpent = timeSpent;
		this.badWeatherTime = badWeatherTime;
		this.dressingId = dressingId;
		this.overpaymentAmount = overpaymentAmount;
		
		if(overpaymentAmount > 0)
			this.overpaymentRequired = true;
		else
			this.overpaymentRequired = false;
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

	public LocalDateTime getActivatingDateTime() {
		return activatingDateTime;
	}

	public void setActivatingDateTime(LocalDateTime activatingDateTime) {
		this.activatingDateTime = activatingDateTime;
	}

	public LocalDateTime getCloseingDateTime() {
		return closeingDateTime;
	}

	public void setCloseingDateTime(LocalDateTime closeingDateTime) {
		this.closeingDateTime = closeingDateTime;
	}

	public Time getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(Time timeSpent) {
		this.timeSpent = timeSpent;
	}	

	public Time getBadWeatherTime() {
		return badWeatherTime;
	}

	public void setBadWeatherTime(Time badWeatherTime) {
		this.badWeatherTime = badWeatherTime;
	}

	public AppUser getActivatorId() {
		return activatorId;
	}

	public void setActivatorId(AppUser activatorId) {
		this.activatorId = activatorId;
	}

	public AppUser getDressingId() {
		return dressingId;
	}

	public void setDressingId(AppUser dressingId) {
		this.dressingId = dressingId;
	}

	public AppUser getRegistrarId() {
		return registrarId;
	}

	public void setRegistrarId(AppUser registrarId) {
		this.registrarId = registrarId;
	}

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public int getOverpaymentAmount() {
		return overpaymentAmount;
	}

	public void setOverpaymentAmount(int overpaymentAmount) {
		this.overpaymentAmount = overpaymentAmount;
	}

	public boolean isOverpaymentRequired() {
		return overpaymentRequired;
	}

	public void setOverpaymentRequired(boolean overpaymentRequired) {
		this.overpaymentRequired = overpaymentRequired;
	}	
}
