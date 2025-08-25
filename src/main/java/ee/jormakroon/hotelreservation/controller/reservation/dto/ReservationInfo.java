package ee.jormakroon.hotelreservation.controller.reservation.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link ee.jormakroon.hotelreservation.persistence.reservation.Reservation}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationInfo implements Serializable {
    private Integer reservationId;
    @NotBlank(message = "Room name is required")
    private String roomName;
    @NotBlank(message = "Room type is required")
    private String roomTypeName;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Nationality is required")
    private String nationality;
    private String phone;
    @FutureOrPresent(message = "Check in date must be today or in the future")
    private Instant checkInDate;
    @FutureOrPresent(message = "Check out date must be today or in the future")
    private Instant checkOutDate;
    @Positive(message = "Number of nights must be positive")
    private Integer nights;
}