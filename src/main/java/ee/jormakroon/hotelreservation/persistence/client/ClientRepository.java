package ee.jormakroon.hotelreservation.persistence.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByFirstNameAndLastNameAndNationality(String firstName, String lastName, String nationality);
}