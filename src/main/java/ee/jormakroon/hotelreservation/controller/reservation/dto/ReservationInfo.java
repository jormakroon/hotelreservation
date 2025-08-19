package ee.jormakroon.hotelreservation.controller.reservation.dto;

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
    private String roomName;
    private String roomTypeName;
    private String firstName;
    private String lastName;
    private String nationality;
    private String phone;
    private Instant checkInDate;
    private Instant checkOutDate;
    private Integer nights;
}