package at.kaindorf.bookshop.airlinereservation.service;

import at.kaindorf.bookshop.airlinereservation.pojos.Flight;
import at.kaindorf.bookshop.airlinereservation.repositories.FlightRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Component
public class FlightService {

    private final FlightRepository flightRepository;

    public List<Flight> getDepartingFlights(String iataCode) {
        return flightRepository.findByDepAirport_IataCodeOrderByDayOfWeekAscDepTimeAsc(iataCode);
    }

    public List<Flight> getArrivingFlights(String iataCode) {
        return flightRepository.findByArrAirport_IataCodeOrderByDayOfWeekAscArrTimeAsc(iataCode);
    }

    public List<Flight> getDomesticToInternationalFlights() {
        return flightRepository.findDomesticToInternationalFlights();
    }

    @PostConstruct
    public void testMethods() {
        List<Flight> flights = getDomesticToInternationalFlights();
        System.out.println("Flights from domestic departure airport to non domestic arrival airport:");
        for (Flight flight : flights) {
            System.out.println(flight.getAirline().getName() + "(" + flight.getAirline().getCountry() + ") flight " + flight.getFlightId() + " : from " + flight.getArrAirport().getName() + " to " + flight.getDepAirport().getName());
        }
    }


}
