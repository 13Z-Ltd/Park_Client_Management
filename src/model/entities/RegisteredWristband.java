package model.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "registered_wristband")
public class RegisteredWristband {

	@Id
	@Column(name = "bar_code", length = 13)
	private long barCode;

	@Column(name = "color", length = 10)
	private String color;

	@CreationTimestamp
	@Column(name = "registration_date_time")
	private LocalDateTime registrationDateTime;

	// OneToMany
	@OneToOne
	@JoinColumn(name = "responsible_user_id", referencedColumnName = "id")
	private AppUser responsibleUserId;

	public RegisteredWristband() {
	}

	public RegisteredWristband(long barCode, String color, AppUser responsibleUserId) {
		// super();
		this.barCode = barCode;
		this.color = color;
		this.responsibleUserId = responsibleUserId;
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

	public LocalDateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public void setRegistrationDateTime(LocalDateTime registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public AppUser getResponsibleUserId() {
		return responsibleUserId;
	}

	public void setResponsibleUserId(AppUser responsibleUserId) {
		this.responsibleUserId = responsibleUserId;
	}

	@Override
	public String toString() {
		return "RegisteredWristband [barCode=" + barCode + ", color=" + color + ", registrationDateTime="
				+ registrationDateTime + ", responsibleUserId=" + responsibleUserId + "]";
	}
}
