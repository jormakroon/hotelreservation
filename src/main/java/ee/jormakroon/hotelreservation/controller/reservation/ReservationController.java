package ee.jormakroon.hotelreservation.controller.reservation;

import ee.jormakroon.hotelreservation.controller.reservation.dto.ReservationInfo;
import ee.jormakroon.hotelreservation.service.reservation.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")

public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/all")
    @Operation(summary = "Returns all reservations",
            description = "If there is no reservations, returns an empty array")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public List<ReservationInfo> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns reservation info by id",
            description = "If reservation does not exist, responds with error code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")})
    public ReservationInfo getReservationById(@PathVariable("id") Integer id) {
        return reservationService.getReservation(id);
    }

}
