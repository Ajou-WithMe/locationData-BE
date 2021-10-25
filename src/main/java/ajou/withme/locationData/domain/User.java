package ajou.withme.locationData.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String pwd;

    private String address;

    private String phone;

//    type 0 : email, 1 : kakao, 2 : 피보호자
    private Long type;

    private String profileImg;

    private String uid;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Location> locations = new LinkedList<>();

    public void addPartyMember(Location location) {
        if (this.locations == null) {
            this.locations = new LinkedList<>();
        }
        locations.add(location);
        location.setUser(this);
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserOption userOption;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SafeZone> safeZone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<InitSafeZone> initSafeZone;
}
