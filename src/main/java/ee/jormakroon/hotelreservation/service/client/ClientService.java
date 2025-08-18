package ee.jormakroon.hotelreservation.service.client;

import ee.jormakroon.hotelreservation.controller.reservation.dto.ReservationInfo;
import ee.jormakroon.hotelreservation.persistence.client.Client;
import ee.jormakroon.hotelreservation.persistence.client.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client findOrCreateClient(String firstName, String lastName, String nationality) {
        return clientRepository.findByFirstNameAndLastNameAndNationality(
                        firstName, lastName, nationality)
                .orElseGet(() -> {
                    Client client = new Client();
                    client.setFirstName(firstName);
                    client.setLastName(lastName);
                    client.setNationality(nationality);
                    return clientRepository.save(client);
                });
    }

    @Transactional
    public Client findOrCreateClientFromReservationInfo(ReservationInfo reservationInfo) {
        return findOrCreateClient(
                reservationInfo.getFirstName(),
                reservationInfo.getLastName(),
                reservationInfo.getNationality()
        );
    }
}
