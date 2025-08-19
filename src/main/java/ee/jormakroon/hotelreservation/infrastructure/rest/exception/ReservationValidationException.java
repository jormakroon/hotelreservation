package ee.jormakroon.hotelreservation.infrastructure.rest.exception;

import ee.jormakroon.hotelreservation.infrastructure.rest.error.Error;
import lombok.Getter;

@Getter
public class ReservationValidationException extends RuntimeException {
    private final String message;
    private final Error error;

    public ReservationValidationException(Error error) {
        super(error.getMessage());
        this.message = error.getMessage();
        this.error = error;
    }
}
