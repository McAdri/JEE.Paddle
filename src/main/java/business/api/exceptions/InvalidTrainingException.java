package business.api.exceptions;

public class InvalidTrainingException {

	
	private static final long serialVersionUID = -1344640670884805385L;
	public static final int CODE = 1;
	public static final String DESCRIPTION = "Training invalido";


	 public InvalidTrainingException(String description, int code) {
	 	this("");
	 }

	 public InvalidTrainingException(String detail) {
		 super();
	 }
}
