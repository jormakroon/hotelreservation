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
    @Mapping(source = "nights", target = "nights")
    @Mapping(target = "phone", ignore = true)
    ReservationInfo toReservationInfo(Reservation reservation);

    @Mapping(source = "reservationId", target = "id")
    @Mapping(source = "roomName", target = "room.name")
    @Mapping(source = "roomTypeName", target = "room.roomType.typeName")
    @Mapping(source = "firstName", target = "client.firstName")
    @Mapping(source = "lastName", target = "client.lastName")
    @Mapping(source = "nationality", target = "client.nationality")
    @Mapping(source = "phone", target = "client.phone")
    Reservation toReservation(ReservationInfo reservationInfo);

    List<ReservationInfo> toReservationInfoList(List<Reservation> reservations);
}