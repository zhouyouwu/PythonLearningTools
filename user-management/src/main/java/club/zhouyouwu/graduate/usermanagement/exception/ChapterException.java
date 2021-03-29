package club.zhouyouwu.graduate.usermanagement.exception;

public class ChapterException extends BadRequestException{
    public ChapterException(String message) {
        super(message);
    }

    public ChapterException(String message, Throwable cause) {
        super(message, cause);
    }
}
