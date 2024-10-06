package at.kaindorf.bookshop.airlinereservation.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

/**
 * Project: airlinereservation
 * Created by: raph
 * Date: 2024-10-02
 * Time: 13:32:02
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@NamedQueries({
        @NamedQuery(name = "Flight.findByDepAirport_IataCodeOrderByDayOfWeekAscDepTimeAsc",
                query = "SELECT f FROM Flight f INNER JOIN Airport ap ON f.depAirport.airportId = ap.airportId WHERE ap.iataCode = :iataCode ORDER BY f.dayOfWeek, f.depTime"),
        @NamedQuery(name = "Flight.findByArrAirport_IataCodeOrderByDayOfWeekAscArrTimeAsc",
                query = "SELECT f FROM Flight f WHERE f.arrAirport.iataCode = :iataCode ORDER BY f.dayOfWeek, f.arrTime"),
        @NamedQuery(name = "Flight.findDomesticToInternationalFlights",
                query = "SELECT f FROM Flight f WHERE f.airline.country = f.depAirport.country AND f.arrAirport.country <> f.airline.country"),
        @NamedQuery(name = "Flight.findAircraftsOfAirline",
                query = "SELECT a.name, COUNT(DISTINCT ac.aircraftId) as aircraftCount " +
                        "FROM Flight f " +
                        "JOIN f.airline a " +
                        "JOIN f.aircraft ac " +
                        "GROUP BY a.name " +
                        "ORDER BY aircraftCount DESC, a.name ASC"),
        @NamedQuery(name = "Flight.findAircraftTypes",
                query = "SELECT ac.manufacturer, ac.modelName FROM Flight f INNER JOIN Airport da ON da.iataCode = :depAirport " +
                        "INNER JOIN Airport aa ON aa.iataCode = :arrAirport " +
                        "INNER JOIN Aircraft ac ON ac.aircraftId = f.aircraft.aircraftId")
})
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long flightId;

    @ManyToOne
    @JoinColumn(name = "airline_id", nullable = false)
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @ManyToOne
    @JoinColumn(name = "dep_airport_id", nullable = false)
    private Airport depAirport;

    @ManyToOne
    @JoinColumn(name = "arr_airport_id", nullable = false)
    private Airport arrAirport;

    @Column(nullable = false)
    private String depTime;

    @Column(nullable = false)
    private String arrTime;

    @Column(nullable = false)
    private DayOfWeek dayOfWeek;
}
