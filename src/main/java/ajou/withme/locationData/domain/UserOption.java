package ajou.withme.locationData.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean safeMove;

    private boolean pushAlarm;

    // 세이프존 테이블이 2개니까, 신규 유저인지 확인하는 칼럼. true면 신규유저, false면 새로 safe존을 만든것
    private boolean isInitSafeZone;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date currentNetwork;

    private Long time;
    private Double distance;

    private Double boxSize;

    @JoinColumn
    @OneToOne
    User user;

    public void addTime(Long time) {
        this.time += time;
    }

    public void addDistance(Double distance) {
        this.distance += distance;
    }

    public void updateCurrentNetwork() {
        this.currentNetwork = new Date();
    }
}
