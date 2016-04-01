package business.wrapper;

import java.util.Calendar;

public class TrainingCreateWrapper {
	
	private int courtId;
	
	private Calendar startTime;
	
	private Calendar endTime;
	
	public TrainingCreateWrapper() {
		
	}
	
	public TrainingCreateWrapper(int courtId, Calendar startTime, Calendar endTime) {
		super();
		startTime.set(Calendar.MINUTE, 0);
		startTime.set(Calendar.SECOND, 0);
		startTime.set(Calendar.MILLISECOND, 0);
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND, 0);
		endTime.set(Calendar.MILLISECOND, 0);
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

	public void setCourtId(int courtId) {
		this.courtId = courtId;
	}

	public void setStartTime(Calendar startTime) {
		startTime.set(Calendar.MINUTE, 0);
		startTime.set(Calendar.SECOND, 0);
		startTime.set(Calendar.MILLISECOND, 0);
		this.startTime = startTime;
	}

	public void setEndTime(Calendar endTime) {
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND, 0);
		endTime.set(Calendar.MILLISECOND, 0);
		this.endTime = endTime;
	}
	
	

}
