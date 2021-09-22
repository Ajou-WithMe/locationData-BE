package ajou.withme.locationData.dto;

import ajou.withme.locationData.domain.User;
import lombok.Data;


@Data
public class SaveUserDto {
    private String name;

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .distance(0L)
                .time(0L)
                .uid(null)
                .build();
    }
}
