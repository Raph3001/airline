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
 * Time: 13:30:31
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long airlineId;

    @Column
    private String icaoCode;
    @Column
    private String iataCode;
    @Column
    private String name;
    @Column
    private String country;

    @OneToMany(mappedBy = "airline")
    @JsonIgnore
    private List<Flight> flights;

}
