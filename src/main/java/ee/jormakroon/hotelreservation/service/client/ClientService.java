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
    public Client findOrCreateClient(String firstName, String lastName, String nationality, String phone) {
        return clientRepository.findByFirstNameAndLastNameAndNationality(
                        firstName, lastName, nationality)
                .map(client -> {
                    if (phone != null && !phone.equals(client.getPhone())) {
                        client.setPhone(phone);
                    return clientRepository.save(client);
                }
                return client;
                })
                .orElseGet(() -> createClient(firstName, lastName, nationality, phone));

    }

    @Transactional
    public Client findOrCreateClientFromReservationInfo(ReservationInfo reservationInfo) {
        return retrieveOrCreateClient(reservationInfo);
    }



    private Client retrieveOrCreateClient(ReservationInfo reservationInfo) {
        return findOrCreateClient(
                reservationInfo.getFirstName(),
                reservationInfo.getLastName(),
                reservationInfo.getNationality(),
                reservationInfo.getPhone()
        );
    }

    private Client createClient(String firstName, String lastName, String nationality, String phone) {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setNationality(nationality);
        client.setPhone(phone);
        return clientRepository.save(client);
    }
}
