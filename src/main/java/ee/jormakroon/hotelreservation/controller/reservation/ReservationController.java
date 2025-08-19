package ee.jormakroon.hotelreservation.controller.reservation;

import ee.jormakroon.hotelreservation.controller.reservation.dto.ReservationInfo;
import ee.jormakroon.hotelreservation.service.reservation.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")

public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "adds a new reservation to the system",
            description = "Accepts a new ReservationInfo and adds it to the database ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New Reservation added"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g., dates in the past, invalid date range)"),
            @ApiResponse(responseCode = "404", description = "Room not found"),
            @ApiResponse(responseCode = "409", description = "Reservation already exists")})
    public void addNewReservation(@RequestBody @Valid ReservationInfo reservationInfo) {
        reservationService.addReservation(reservationInfo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates reservation details",
            description = "Modifies an existing reservation details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g., dates in the past, invalid date range)"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")})
    public void updateReservation(@PathVariable("id") Integer reservationId, @RequestBody @Valid ReservationInfo reservationInfo) {
        reservationService.updateReservation(reservationId, reservationInfo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a reservation",
            description = "Deletes a reservation from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")})
    public void deleteReservation(@PathVariable Integer id) {
        reservationService.deleteReservation(id);
    }
}
