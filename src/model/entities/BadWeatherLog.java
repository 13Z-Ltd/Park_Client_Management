package model.entities;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bad_weather_log")
public class BadWeatherLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "starting_date_time")
	private LocalDateTime startingDateTime;
	
	@Column(name = "ending_date_time")
	private LocalDateTime endingDateTime;
	
	@Column(name = "elapsed_time")
	private Time elapsedTime;
	
	@OneToOne
	@JoinColumn(name = "activator_id", referencedColumnName = "id")
	private AppUser activatorId;
	
	@Column(name = "amount_of_affected_wristbands", length = 6)
	private Long amountOfAffectedWristbands;
	
	public BadWeatherLog() {
		
	}

	public BadWeatherLog(LocalDateTime startingDateTime, LocalDateTime endingDateTime, //Time elapsedTime,
			AppUser activatorId, Long amountOfAffectedWristbands) {
		super();
		this.startingDateTime = startingDateTime;
		this.endingDateTime = endingDateTime;
		//this.elapsedTime = elapsedTime;
		this.elapsedTime = setBadWeatherElapsedTime();
		this.activatorId = activatorId;
		this.amountOfAffectedWristbands = amountOfAffectedWristbands;
	}
	
	@SuppressWarnings("deprecation")
	private Time setBadWeatherElapsedTime() {
		if(startingDateTime != null && endingDateTime != null) {
			Duration duration = Duration.between(startingDateTime, endingDateTime);
			int hours = (int)(duration.getSeconds() / (60*60));
			int minutes = (int) (duration.getSeconds() % (60*60) / 60);
			int seconds = (int) (duration.getSeconds() % 60);
		
			return new Time(hours, minutes, seconds);
		}else {
			return null;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getStartingDateTime() {
		return startingDateTime;
	}

	public void setStartingDateTime(LocalDateTime startingDateTime) {
		this.startingDateTime = startingDateTime;
	}

	public LocalDateTime getEndingDateTime() {
		return endingDateTime;
	}

	public void setEndingDateTime(LocalDateTime endingDateTime) {
		this.endingDateTime = endingDateTime;
	}

	public Time getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Time elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public AppUser getActivatorId() {
		return activatorId;
	}

	public void setActivatorId(AppUser activatorId) {
		this.activatorId = activatorId;
	}

	public Long getAmountOfAffectedWristbands() {
		return amountOfAffectedWristbands;
	}

	public void setAmountOfAffectedWristbands(Long amountOfAffectedWristbands) {
		this.amountOfAffectedWristbands = amountOfAffectedWristbands;
	}
}
