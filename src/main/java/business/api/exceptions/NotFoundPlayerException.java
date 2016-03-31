package business.api.exceptions;

public class NotFoundPlayerException {

	
	private static final long serialVersionUID = -1344640670884805385L;
	public static final String DESCRIPTION = "Jugador no encontrado";
	public static final int CODE = 1;

	public NotFoundPlayerException() {
		this("");
	}
	public NotFoundPlayerException(String detail) {
		 super();
	}
}
