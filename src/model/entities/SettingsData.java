package model.entities;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="settings_data")
public class SettingsData {

	@Id	
	@Column(name="id")
	private int id;
	
	@NotNull
	@Column(name="child_singing")
	private int childSinging;

	@NotNull
	@Column(name="senior_singing")
	private int seniorSinging;
	
	@NotNull
	@Column(name="reserved_child_singing")
	private int reservedChildSinging;

	@NotNull
	@Column(name="reserved_senior_singing")
	private int reservedSeniorSinging;
	
	@NotNull
	@Column(name="closing_hour")
	private int closingHour;
	
	@Column(name = "bad_weather_start_time")
	private LocalDateTime badWeatherStartTime;

	public SettingsData() {		
	}
	
	public SettingsData(int id, int childSinging, int seniorSinging, int reservedChildSinging, int reservedSeniorSinging, int closingHour) {
		super();
		this.id = id;
		this.childSinging = childSinging;
		this.seniorSinging = seniorSinging;
		this.reservedChildSinging = reservedChildSinging;
		this.reservedSeniorSinging = reservedSeniorSinging;
		this.closingHour = closingHour;
		this.badWeatherStartTime = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChildSinging() {
		return childSinging;
	}

	public void setChildSinging(int childSinging) {
		this.childSinging = childSinging;
	}

	public int getSeniorSinging() {
		return seniorSinging;
	}

	public void setSeniorSinging(int seniorSinging) {
		this.seniorSinging = seniorSinging;
	}	

	public int getReservedChildSinging() {
		return reservedChildSinging;
	}

	public void setReservedChildSinging(int reservedChildSinging) {
		this.reservedChildSinging = reservedChildSinging;
	}

	public int getReservedSeniorSinging() {
		return reservedSeniorSinging;
	}

	public void setReservedSeniorSinging(int reservedSeniorSinging) {
		this.reservedSeniorSinging = reservedSeniorSinging;
	}

	public int getClosingHour() {
		return closingHour;
	}

	public void setClosingHour(int closingHour) {
		this.closingHour = closingHour;
	}
	
	public LocalDateTime getBadWeatherStartTime() {
		return badWeatherStartTime;
	}

	public void setBadWeatherStartTime(LocalDateTime badWeatherStartTime) {
		this.badWeatherStartTime = badWeatherStartTime;
	}
	
	@SuppressWarnings("deprecation")
	public Time getBadWeatherElapsedTime() {
		if(badWeatherStartTime != null) {
			Duration duration = Duration.between(badWeatherStartTime, LocalDateTime.now());
			int hours = (int)(duration.getSeconds() / (60*60));
			int minutes = (int) (duration.getSeconds() % (60*60) / 60);
			int seconds = (int) (duration.getSeconds() % 60);
			
			return new Time(hours, minutes, seconds);
		}else {
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public String getBadWeatherElapsedTimeString() {
		Time elapsedTime = getBadWeatherElapsedTime();
		NumberFormat numberFormatter = new DecimalFormat("00");
		String minutesString = numberFormatter.format(elapsedTime.getMinutes());
		String secondsString = numberFormatter.format(elapsedTime.getSeconds());
		return (elapsedTime.getHours() + ":" + minutesString + ":" + secondsString);
	}

	@Override
	public String toString() {
		return "SettingsData [id=" + id + ", childSinging=" + childSinging + ", seniorSinging=" + seniorSinging
				+ ", reservedChildSinging=" + reservedChildSinging + ", reservedSeniorSinging=" + reservedSeniorSinging
				+ ", closingHour=" + closingHour + ", badWeatherStartTime=" + badWeatherStartTime + "]";
	}	
}