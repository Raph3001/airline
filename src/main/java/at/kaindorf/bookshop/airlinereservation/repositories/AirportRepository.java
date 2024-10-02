package at.kaindorf.bookshop.airlinereservation.repositories;

import at.kaindorf.bookshop.airlinereservation.pojos.Airline;
import at.kaindorf.bookshop.airlinereservation.pojos.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
}
