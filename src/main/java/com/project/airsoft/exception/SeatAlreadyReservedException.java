package com.project.airsoft.exception;

public class SeatAlreadyReservedException extends RuntimeException {

    public SeatAlreadyReservedException(String message) {
        super(message);
    }

}
