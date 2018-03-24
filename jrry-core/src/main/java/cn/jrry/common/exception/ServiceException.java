package cn.jrry.common.exception;

public class ServiceException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 176627929463980265L;

	public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
