package at.kaindorf.bookshop.airlinereservation.repositories;

import at.kaindorf.bookshop.airlinereservation.pojos.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
}
