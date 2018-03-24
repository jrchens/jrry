package cn.jrry.common.exception;

public class UserStateException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 617854700106403429L;

	public UserStateException() {
    }

    public UserStateException(String message) {
        super(message);
    }
}
