package at.kaindorf.bookshop.airlinereservation.repositories;

import at.kaindorf.bookshop.airlinereservation.pojos.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
