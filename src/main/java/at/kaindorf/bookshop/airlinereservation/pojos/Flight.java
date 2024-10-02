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
    private String dayOfWeek;
}
