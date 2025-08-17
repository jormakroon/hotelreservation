package ee.jormakroon.hotelreservation.service.reservation;

import ee.jormakroon.hotelreservation.controller.reservation.dto.ReservationInfo;
import ee.jormakroon.hotelreservation.persistence.reservation.Reservation;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReservationMapper {
    @Mapping(source = "id", target = "reservationId")
    @Mapping(source = "room.name", target = "roomName")
    @Mapping(source = "room.roomType.typeName", target = "roomTypeName")
    @Mapping(source = "client.firstName", target = "firstName")
    @Mapping(source = "client.lastName", target = "lastName")
    @Mapping(source = "client.nationality", target = "nationality")
    @Mapping(source = "checkInDate", dateFormat = "dd.MM.yyyy", target = "checkInDate")
    @Mapping(source = "checkOutDate", dateFormat = "dd.MM.yyyy", target = "checkOutDate")
    @Mapping(source = "nights", target = "nights")
    @Mapping(source = "room.price", target = "roomPrice")
    @Mapping(source = "totalPrice", target = "totalPrice")
    ReservationInfo toReservationInfo(Reservation reservation);

    List<ReservationInfo> toReservationInfoList(List<Reservation> reservations);

}