package business.api.exceptions;

public class TrainingNotAvailableException {

	
	private static final long serialVersionUID = -1344640670884805385L;
	public static final int CODE = 1;
	public static final String DESCRIPTION = "Training lleno";
	
	public TrainingNotAvailableException() {
		this("");
	}
	
	public TrainingNotAvailableException(String detail) {
		super();
	}
}
