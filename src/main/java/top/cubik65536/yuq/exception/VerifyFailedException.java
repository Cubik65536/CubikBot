package top.cubik65536.yuq.exception;

public class VerifyFailedException extends RuntimeException {
    public VerifyFailedException() {
        super();
    }

    public VerifyFailedException(String message) {
        super(message);
    }
}
