package club.zhouyouwu.graduate.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author zhou
 */
@ControllerAdvice
public abstract class AbstractGlobalExceptionHandler extends RuntimeException{

    private Object errorData;

    public AbstractGlobalExceptionHandler(String message) {
        super(message);
    }

    public AbstractGlobalExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    @NonNull
    public AbstractGlobalExceptionHandler setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
