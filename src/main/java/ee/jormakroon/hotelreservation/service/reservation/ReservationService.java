package ee.jormakroon.hotelreservation.service.reservation;

import ee.jormakroon.hotelreservation.controller.reservation.dto.ReservationInfo;
import ee.jormakroon.hotelreservation.persistence.reservation.Reservation;
import ee.jormakroon.hotelreservation.persistence.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public List<ReservationInfo> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        return reservationMapper.toReservationInfoList(reservations);
    }

}

