package club.zhouyouwu.graduate.usermanagement.exception;

import club.zhouyouwu.graduate.common.constant.CodeMsg;
import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractGlobalExceptionHandler{
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
