package de.cookiebook.restservice.user;

import lombok.Getter;

@Getter
public enum Status {
    SUCCESS ("Login Successful",200),
    USER_ALREADY_EXISTS ("User already exists",401),
    FAILURE ("Failed to Login", 400);

    private final String status;
    private final long statuscode;


    Status(String status, long statuscode){
        this.status=status;
        this.statuscode=statuscode;
    }
}
