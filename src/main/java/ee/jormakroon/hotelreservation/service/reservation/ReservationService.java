package ee.jormakroon.hotelreservation.service.reservation;

import ee.jormakroon.hotelreservation.controller.reservation.dto.ReservationInfo;
import ee.jormakroon.hotelreservation.infrastructure.rest.exception.DataNotFoundException;
import ee.jormakroon.hotelreservation.infrastructure.rest.exception.ReservationValidationException;
import ee.jormakroon.hotelreservation.infrastructure.rest.error.Error;
import ee.jormakroon.hotelreservation.persistence.client.Client;
import ee.jormakroon.hotelreservation.persistence.reservation.Reservation;
import ee.jormakroon.hotelreservation.persistence.reservation.ReservationRepository;
import ee.jormakroon.hotelreservation.persistence.room.Room;
import ee.jormakroon.hotelreservation.persistence.room.RoomRepository;
import ee.jormakroon.hotelreservation.service.client.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final ClientService clientService;
    private final RoomRepository roomRepository;

    public List<ReservationInfo> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservationMapper.toReservationInfoList(reservations);
    }

    public ReservationInfo getReservation(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()
                -> new DataNotFoundException("Reservation not found"));

        return reservationMapper.toReservationInfo(reservation);
    }

    @Transactional
    public void addReservation(ReservationInfo reservationInfo) {
        validateReservationDates(reservationInfo);
        Client client = clientService.findOrCreateClientFromReservationInfo(reservationInfo);

        Room room = roomRepository.findByName(reservationInfo.getRoomName())
                .orElseThrow(() -> new DataNotFoundException("Room not found: " + reservationInfo.getRoomName()));

        Reservation reservation = createNewReservation(reservationInfo, client, room);
        Integer nights = calculateNightsForReservation(reservationInfo, reservation);
        calculateTotalPrice(room, nights, reservation);
        reservationRepository.save(reservation);
    }

    public void updateReservation(Integer reservationId, ReservationInfo reservationInfo) {
        validateReservationDates(reservationInfo);

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()
                -> new DataNotFoundException("Reservation not found"));

        reservation.setClient(clientService.findOrCreateClientFromReservationInfo(reservationInfo));
        reservation.setRoom(roomRepository.findByName(reservationInfo.getRoomName())
                .orElseThrow(() -> new DataNotFoundException("Room not found: " + reservationInfo.getRoomName())));
        reservation.setCheckInDate(reservationInfo.getCheckInDate());
        reservation.setCheckOutDate(reservationInfo.getCheckOutDate());
        Integer nights = calculateNightsForReservation(reservationInfo, reservation);
        calculateTotalPrice(reservation.getRoom(), nights, reservation);
        reservationRepository.save(reservation);
    }

    public void deleteReservation(Integer reservationId) {
        reservationRepository.deleteById(reservationId);
    }


    private static void calculateTotalPrice(Room room, Integer nights, Reservation reservation) {
        BigDecimal totalPrice = room.getPrice().multiply(BigDecimal.valueOf(nights));
        reservation.setTotalPrice(totalPrice);
    }

    private Integer calculateNightsForReservation(ReservationInfo reservationInfo, Reservation reservation) {
        Integer nights = reservationInfo.getNights();
        if (nights == null || nights == 0) {
            nights = calculateNights(reservationInfo.getCheckInDate(), reservationInfo.getCheckOutDate());
        }
        reservation.setNights(nights);
        return nights;
    }

    private void validateReservationDates(ReservationInfo reservationInfo) {
        Instant now = Instant.now();
        if (reservationInfo.getCheckInDate() != null && reservationInfo.getCheckInDate().isBefore(now)) {
            throw new ReservationValidationException(Error.PAST_DATE_RESERVATION);
        }
        if (reservationInfo.getCheckInDate() != null && reservationInfo.getCheckOutDate() != null
                && reservationInfo.getCheckOutDate().isBefore(reservationInfo.getCheckInDate())) {
            throw new ReservationValidationException(Error.CHECK_OUT_BEFORE_CHECK_IN);
        }
        if (reservationInfo.getNights() != null && reservationInfo.getNights() < 1) {
            throw new ReservationValidationException(Error.INVALID_NIGHTS);
        }
    }

    private static Reservation createNewReservation(ReservationInfo reservationInfo, Client client, Room room) {
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setRoom(room);
        reservation.setCheckInDate(reservationInfo.getCheckInDate());
        reservation.setCheckOutDate(reservationInfo.getCheckOutDate());
        return reservation;
    }

    private Integer calculateNights(Instant checkIn, Instant checkOut) {
        if (checkIn == null || checkOut == null) {
            return 1;
        }
        long days = Duration.between(checkIn, checkOut).toDays();
        return Math.max(1, (int) days);
    }

}
