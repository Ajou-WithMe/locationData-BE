package ajou.withme.locationData.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Getter
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;
    private String accessToken;

    @ManyToOne
    @JoinColumn
    private User user;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
