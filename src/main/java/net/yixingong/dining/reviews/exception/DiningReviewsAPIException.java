package net.yixingong.dining.reviews.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;

// Throw this exception whenever we write some business logic or validate request parameters

@Getter
public class DiningReviewsAPIException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public DiningReviewsAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public DiningReviewsAPIException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

}
