package ee.jormakroon.hotelreservation.persistence.reservation;

import ee.jormakroon.hotelreservation.persistence.room.Room;
import ee.jormakroon.hotelreservation.persistence.client.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "RESERVATION")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ROOM_ID", nullable = false)
    private Room room;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;

    @NotNull
    @Column(name = "CHECK_IN_DATE", nullable = false)
    private Instant checkInDate;

    @NotNull
    @Column(name = "CHECK_OUT_DATE", nullable = false)
    private Instant checkOutDate;

    @NotNull
    @Column(name = "NIGHTS", nullable = false)
    private Integer nights;

    @NotNull
    @Column(name = "TOTAL_PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

}