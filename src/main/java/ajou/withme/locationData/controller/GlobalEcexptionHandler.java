package ajou.withme.locationData.controller;

import ajou.withme.locationData.util.ResFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalEcexptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResFormat handleException(Exception e) {

        log.error("------------------------e : "+ e);
        log.error("------------------------e.toString() : "+e.toString());
        log.error("------------------------Arrays.toString(e.getStackTrace()) : "+ Arrays.toString(e.getStackTrace()));
        return new ResFormat(false, 500L, e.getMessage());
    }

}

