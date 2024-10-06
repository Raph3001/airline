package at.kaindorf.bookshop.airlinereservation.service;

import at.kaindorf.bookshop.airlinereservation.pojos.Flight;
import at.kaindorf.bookshop.airlinereservation.repositories.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Component
public class DB_Access {

    private final FlightRepository flightRepository;


    public List<Flight> getFlightsByAirportAndType(String iataCode, String type) {
        if (type.equalsIgnoreCase("d")) {
            return flightRepository.findByDepAirport_IataCodeOrderByDayOfWeekAscDepTimeAsc(iataCode);
        } else if (type.equalsIgnoreCase("a")) {
            return flightRepository.findByArrAirport_IataCodeOrderByDayOfWeekAscArrTimeAsc(iataCode);
        }
        return null;
    }

    public List<Flight> getDomesticToInternationalFlights() {
        return flightRepository.findDomesticToInternationalFlights();
    }

    public List<Object[]> findAircraftsOfAirline() {
        return flightRepository.findAircraftsOfAirline();
    }


    // User-story-4: Get aircraft types for flights between two airports (both directions)
    public List<Object[]> getAircraftTypesBetweenAirports(String airport1, String airport2) {
        return flightRepository.findAircraftTypes(airport1, airport2);
    }
}
