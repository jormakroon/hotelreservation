package ee.jormakroon.hotelreservation.infrastructure.rest.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error {
    SOME_ERROR_ENUM("Some error message"),
    PAST_DATE_RESERVATION("Cannot create a reservation with check-in date in the past"),
    CHECK_OUT_BEFORE_CHECK_IN("Check-out date must be after check-in date"),
    INVALID_ROOM_NUMBER("Invalid room number"),
    INVALID_ROOM_TYPE("Invalid room type"),
    INVALID_NIGHTS("Number of nights must be at least 1");

    private final String message;
}
