package simpletron;

public class InterpretException extends Exception {

    private static final long serialVersionUID = 1L;

    public InterpretException() {
        super();
    }

    public InterpretException(String message) {
        super(message);
    }

    public InterpretException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterpretException(Throwable cause) {
        super(cause);
    }
}
