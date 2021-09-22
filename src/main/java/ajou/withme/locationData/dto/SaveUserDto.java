package ajou.withme.locationData.dto;

import ajou.withme.locationData.domain.User;

import javax.persistence.Column;

public class SaveUserDto {
    private String name;
    private Long distance;
    private Long time;

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .distance(0L)
                .time(0L)
                .uid(null)
                .build();
    }
}
