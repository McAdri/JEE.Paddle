package business.wrapper;

import java.util.Calendar;

public class TrainingWrapper {

	private int courtId;
	
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
}
