package ajou.withme.locationData.util;

import lombok.Getter;

@Getter
public class ResFormat {

    private Boolean success;

    private Long status;

    private Object data;

    public ResFormat(Boolean success, Long status, Object data) {
        this.success = success;
        this.status = status;
        this.data = data;
    }
}
