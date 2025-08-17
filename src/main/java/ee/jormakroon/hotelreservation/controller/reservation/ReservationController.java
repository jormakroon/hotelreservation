package ee.jormakroon.hotelreservation.controller.reservation;

import ee.jormakroon.hotelreservation.controller.reservation.dto.ReservationInfo;
import ee.jormakroon.hotelreservation.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")

public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/all")
    private List<ReservationInfo> getAllReservations() {
           return reservationService.getAllReservations();
    }

}
