package ee.jormakroon.hotelreservation.service.reservation;

import ee.jormakroon.hotelreservation.controller.reservation.dto.ReservationInfo;
import ee.jormakroon.hotelreservation.infrastructure.rest.exception.DataNotFoundException;
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
        //Find or create client using the dedicated service
        Client client = clientService.findOrCreateClientFromReservationInfo(reservationInfo);
        //Find room by name
        Room room = roomRepository.findByName(reservationInfo.getRoomName())
                .orElseThrow(() -> new DataNotFoundException("Room not found: " + reservationInfo.getRoomName()));
        //Create reservation
        Reservation reservation = createNewReservation(reservationInfo, client, room);
        //Calculate nights if not provided
        Integer nights = calculateNightsForReservation(reservationInfo, reservation);
        //Calculate total price
        calculateTotalPrice(room, nights, reservation);
        //Save reservation
        reservationRepository.save(reservation);
    }

    public void updateReservation(Integer reservationId, ReservationInfo reservationInfo) {
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
