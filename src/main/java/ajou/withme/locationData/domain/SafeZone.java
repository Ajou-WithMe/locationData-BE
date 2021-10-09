package ajou.withme.locationData.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SafeZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ttl;

    @ManyToOne
    @JoinColumn
    User user;

    @OneToMany(mappedBy = "safeZone", cascade = CascadeType.ALL)
    private List<ZoneLocation> zoneLocation;

}
