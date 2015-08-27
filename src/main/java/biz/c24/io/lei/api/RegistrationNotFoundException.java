package biz.c24.io.lei.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegistrationNotFoundException extends RuntimeException {

    public RegistrationNotFoundException(String identifier) {
        super("No Registration found with that identifier: " + identifier);
    }
}
