package de.cookiebook.restservice.user;

import lombok.Getter;

@Getter
public enum Status {
    SUCCESS("Successful", 20),
    USER_ALREADY_EXISTS("User already exists", 41),
    FAILURE("Failed", 40);

    private final String status;
    private final long statuscode;


    Status(String status, long statuscode) {
        this.status = status;
        this.statuscode = statuscode;
    }
}
