package cz.fi.muni.pa165.soccermanager.service.exceptions;

public class ServiceLayerException extends RuntimeException {
	private static final long serialVersionUID = -6163320087416860166L;

	public ServiceLayerException() {
        super();
    }

    public ServiceLayerException(String s) {
        super(s);
    }

    public ServiceLayerException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ServiceLayerException(Throwable throwable) {
        super(throwable);
    }
}
