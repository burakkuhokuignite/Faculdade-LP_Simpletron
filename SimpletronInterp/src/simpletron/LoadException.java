package simpletron;

public class LoadException extends Exception {

    private static final long serialVersionUID = 1L;

    public LoadException() {
        super();
    }

    public LoadException(String message) {
        super(message);
    }

    public LoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadException(Throwable cause) {
        super(cause);
    }
}
