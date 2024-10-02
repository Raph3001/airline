package at.kaindorf.bookshop.airlinereservation.repositories;

import at.kaindorf.bookshop.airlinereservation.pojos.Aircraft;
import at.kaindorf.bookshop.airlinereservation.pojos.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
