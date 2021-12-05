package model.entities;

import java.sql.Time;
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
@Table(name = "User_activity")
public class UserActivities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "activity_id")
	private long activityId;
	
	@OneToOne
	@JoinColumn(name = "app_user_id", referencedColumnName = "id")
	private AppUser appUser;

	@Column(name = "type_of_use", length = 25)
	private String typeOfUse; 
		
	@Column(name = "start_of_activity")
	private LocalDateTime startOfActivity;
	
	@Column(name = "end_of_activity")
	private LocalDateTime endOfActivity;
	
	@Column(name = "elapsed_time")
	private Time elapsedTime;	

	@Column(name = "sold_wristbands")
	private int soldWristbands;
	
	@Column(name = "closed_wristbands")
	private int closedWristbands;	
	
	public UserActivities() {		
	}
		
	public UserActivities(AppUser appUser, String typeOfUse) {
		this.appUser = appUser;
		this.typeOfUse = typeOfUse;
		this.startOfActivity = LocalDateTime.now();
		this.endOfActivity = null;
		this.soldWristbands = 0;
		this.closedWristbands = 0;
		this.elapsedTime = null;
	}

	public String getTypeOfUse() {
		return typeOfUse;
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public void setTypeOfUse(String typeOfUse) {
		this.typeOfUse = typeOfUse;
	}

	public LocalDateTime getStartOfActivity() {
		return startOfActivity;
	}

	public void setStartOfActivity(LocalDateTime startOfActivity) {
		this.startOfActivity = startOfActivity;
	}
	
	public LocalDateTime getEndOfActivity() {
		return endOfActivity;
	}
	
	public void setEndOfActivity(LocalDateTime endOfActivity) {
		this.endOfActivity = endOfActivity;
	}

	public int getSoldWristbands() {
		return soldWristbands;
	}

	public void setSoldWristbands(int soldWristbands) {
		this.soldWristbands = soldWristbands;
	}

	public int getClosedWristbands () {
		return closedWristbands;
	}
	
	public void setClosedWristbands(int closedWristbands) {
		this.closedWristbands = closedWristbands;
	}	

	public Time getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(Time elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	@Override
	public String toString() {
		return "UserActivities [activityId=" + activityId + ", appUserId=" + appUser.getUserName() + ", typeOfUse=" + typeOfUse
				+ ", createDateTime=" + startOfActivity + ", endOfActivity=" + endOfActivity + ", soldArmbands="
				+ soldWristbands + ", closedArmbands=" + closedWristbands + ", elapsedTime=" + elapsedTime + "]";
	}

}
