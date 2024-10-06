package at.kaindorf.bookshop.airlinereservation.repositories;

import at.kaindorf.bookshop.airlinereservation.pojos.Flight;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByDepAirport_IataCodeOrderByDayOfWeekAscDepTimeAsc(String iataCode);

    List<Flight> findByArrAirport_IataCodeOrderByDayOfWeekAscArrTimeAsc(String iataCode);

    @Query(name = "Flight.findDomesticToInternationalFlights")
    List<Flight> findDomesticToInternationalFlights();

    @Query(name = "Flight.findAircraftsOfAirline")
    List<Object[]> findAircraftsOfAirline();

    @Query(name = "Flight.findAircraftTypes")
    List<Object[]> findAircraftTypes(String arrAirport, String depAirport);

}
