package business.api.exceptions;

public class NotFoundTrainingException {

	private static final long serialVersionUID = -1344640670884805385L;
	public static final int CODE = 1;
	public static final String DESCRIPTION = "Training invalido";


	 public NotFoundTrainingException(String description, int code) {
	 	this("");
	 }

	 public NotFoundTrainingException(String detail) {
		 super();
	 }
}
