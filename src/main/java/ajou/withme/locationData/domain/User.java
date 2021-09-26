package ajou.withme.locationData.domain;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String uid;

    private Double distance;

    private Long time;

    public void addDistance(Double distance) {
        this.distance += distance;
    }

    public void addTime(Long time) {
        this.time += time;
    }
}
