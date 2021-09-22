package ajou.withme.locationData.domain;

import lombok.Builder;

import javax.persistence.*;

@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String uid;

    private Long distance;

    private Long time;

}
