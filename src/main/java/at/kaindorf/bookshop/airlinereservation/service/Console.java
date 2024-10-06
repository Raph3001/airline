package at.kaindorf.bookshop.airlinereservation.service;

import at.kaindorf.bookshop.airlinereservation.InitDB;
import at.kaindorf.bookshop.airlinereservation.pojos.Flight;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@Service
@Transactional
public class Console {

    private static final Scanner scanner = new Scanner(System.in);
    private final DB_Access dbAccess;

    @PostConstruct
    public void gaming() {
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1 - Get Departing/Arriving Flights by IATA Code");
            System.out.println("2 - Get Flights from Domestic Airport to Non-Domestic Airport");
            System.out.println("3 - Get Number of Aircrafts by Airline");
            System.out.println("4 - Get Aircraft Types Between Two Airports");
            System.out.println("5 - Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    handleUserStory1();
                    break;
                case 2:
                    handleUserStory2();
                    break;
                case 3:
                    handleUserStory3();
                    break;
                case 4:
                    handleUserStory4();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void handleUserStory1() {
        System.out.println("Enter airport IATA code:");
        String iataCode = scanner.next();
        System.out.println("Arrival/Departure (a/d):");
        String type = scanner.next();
        List<Flight> flights = dbAccess.getFlightsByAirportAndType(iataCode, type);
        if (flights != null && !flights.isEmpty()) {
            flights.forEach(flight -> {
                System.out.printf("%12s %12s %12s %12s %12s%n", flight.getAirline().getIataCode(), flight.getArrAirport().getIataCode(),
                        flight.getDayOfWeek(), flight.getDepTime(), flight.getArrTime());
            });
        } else {
            System.out.println("No flights found.");
        }
    }

    private void handleUserStory2() {
        List<Flight> flights = dbAccess.getDomesticToInternationalFlights();
        if (!flights.isEmpty()) {
            flights.forEach(flight -> {
                System.out.printf("%30s%15s flight %10s : from %12s to %12s%n",
                        flight.getAirline().getName(), "(" + flight.getAirline().getCountry() + ")",
                        flight.getFlightId(), flight.getDepAirport().getIataCode(), flight.getArrAirport().getIataCode());
            });
        } else {
            System.out.println("No flights found.");
        }
    }

    private void handleUserStory3() {

        List<Object[]> results = dbAccess.findAircraftsOfAirline();

        for (Object[] row : results) {
            String airlineName = (String) row[0].toString();
            String aircraftCount = (String) row[1].toString();
            System.out.println(String.format("%30s - %2s aircrafts",airlineName ,aircraftCount) );
        }
    }

    private void handleUserStory4() {
        System.out.println("Enter 1st airport IATA code:");
        String airport1 = scanner.next();
        System.out.println("Enter 2nd airport IATA code:");
        String airport2 = scanner.next();
        List<Object[]> aircraftTypes = dbAccess.getAircraftTypesBetweenAirports(airport1, airport2);
        List<String> entities = new ArrayList<>();
        for (Object[] objects : aircraftTypes) {
            entities.add(objects[0] + " " + objects[1]);
        }
        if (!entities.isEmpty()) {
            entities.forEach(System.out::println);
        } else {
            System.out.println("No aircraft types found between these airports.");
        }
    }
}
