package at.kaindorf.bookshop.airlinereservation.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Project: airlinereservation
 * Created by: raph
 * Date: 2024-10-02
 * Time: 13:31:49
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)

@NamedQueries({
    @NamedQuery(name = "Aircraft.findBySomething", query = "SELECT a FROM Aircraft a WHERE a.modelName = :name") // instead of :... you can also use ?1
})
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long airportId;

    @Column
    private String icaoCode;
    @Column
    private String iataCode;
    @Column
    private String name;
    @Column
    private String country;
    @Column
    private String latitude;
    @Column
    private String longitude;

    @OneToMany(mappedBy = "depAirport")
    @JsonIgnore
    private List<Flight> departingFlights;

    @OneToMany(mappedBy = "arrAirport")
    @JsonIgnore
    private List<Flight> arrivingFlights;

}