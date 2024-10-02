package at.kaindorf.bookshop.airlinereservation;

import at.kaindorf.bookshop.airlinereservation.pojos.Aircraft;
import at.kaindorf.bookshop.airlinereservation.pojos.Airline;
import at.kaindorf.bookshop.airlinereservation.pojos.Airport;
import at.kaindorf.bookshop.airlinereservation.pojos.Flight;
import at.kaindorf.bookshop.airlinereservation.repositories.AircraftRepository;
import at.kaindorf.bookshop.airlinereservation.repositories.AirlineRepository;
import at.kaindorf.bookshop.airlinereservation.repositories.AirportRepository;
import at.kaindorf.bookshop.airlinereservation.repositories.FlightRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Project: airlinereservation
 * Created by: raph
 * Date: 2024-10-02
 * Time: 13:48:11
 */

@Component
@RequiredArgsConstructor
public class InitDB {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;
    @PostConstruct
    public void initDb() {
        List<Aircraft> aircrafts = new ArrayList<>();
        List<Airport> airports = new ArrayList<>();
        List<Airline> airlines = new ArrayList<>();
        List<Flight> flights = new ArrayList<>();

        aircrafts = readAircraftData();
        airlines = readAirlineData();
        airports = readAirportData();
        flights = readFlightData(aircrafts, airports, airlines);

        aircraftRepository.saveAll(aircrafts);
        airlineRepository.saveAll(airlines);
        airportRepository.saveAll(airports);
        flightRepository.saveAll(flights);

    }

    private List<Aircraft> readAircraftData() {
        InputStream is = getClass().getResourceAsStream("/aircraftTypes.json");
        if (is != null) {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule());
            try {
                List<Aircraft> aircrafts = mapper.readerForListOf(Aircraft.class).readValue(is);
                return aircrafts;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<Airport> readAirportData() {
        InputStream is = getClass().getResourceAsStream("/airports.json");
        if (is != null) {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule());
            try {
                List<Airport> airports = mapper.readerForListOf(Airport.class).readValue(is);
                return airports;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private List<Airline> readAirlineData() {
        InputStream is = getClass().getResourceAsStream("/airlines.json");
        if (is != null) {
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule());
            try {
                List<Airline> airlines = mapper.readerForListOf(Airline.class).readValue(is);
                return airlines;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Flight> readFlightData(List<Aircraft> aircrafts, List<Airport> airports, List<Airline> airlines) {
        String csvFile = "C:\\Users\\Rapha\\coding\\5DHIF\\airlinereservation\\src\\main\\resources\\airlcraftsAndFlights.csv";
        String line;
        List<Flight> flights = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            br.readLine(); //skips the header

            while ((line = br.readLine()) != null) {
                String[] flightData = line.split(",");

                String airlineIata = flightData[0];
                String airlineIcao = flightData[1];
                String aircraftType = flightData[2];
                String dep = flightData[3];
                String arr = flightData[4];
                String depTime = flightData[5];
                String arrTime = flightData[6];
                String dayOfWeek = flightData[7];

                Airline airline = airlines.stream().filter(a -> a.getIataCode().equals(airlineIata)).findFirst().get();
                Aircraft aircraft = aircrafts.stream().filter(a -> a.getIcaoCode().equals(aircraftType)).findFirst().get();
                Airport arrAirport = airports.stream().filter(a -> a.getIataCode().equals(arr)).findFirst().get();
                Airport depAirport = airports.stream().filter(a -> a.getIataCode().equals(dep)).findFirst().get();

                Flight flight = new Flight(0L, airline, aircraft, depAirport, arrAirport, depTime, arrTime, dayOfWeek);
                flights.add(flight);
            }
            return flights;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
