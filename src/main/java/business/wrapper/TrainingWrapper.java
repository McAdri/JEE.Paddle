package business.wrapper;

import java.util.Calendar;

import data.entities.Training;

public class TrainingWrapper {

	private int courtId;
	private int id;
	private int coachId;
	
	public TrainingWrapper(int courtId, Calendar startTime, Calendar endTime) {
		super();
		this.courtId = courtId;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public int getCourtId() {
		return courtId;
	}
	
	public Calendar getStartTime() {
		return startTime;
	}
	
	public Calendar getEndTime() {
		return endTime;
	}
	
	private Calendar startTime;
	private Calendar endTime;
	
	public TrainingWrapper(Training training) {
		super();
		this.id = training.getId();
		this.coachId = training.getCoach().getId();
		this.courtId = training.getCourt().getId();
		this.startTime = training.getStartTime();
		this.endTime = training.getEndTime();
	}
}
