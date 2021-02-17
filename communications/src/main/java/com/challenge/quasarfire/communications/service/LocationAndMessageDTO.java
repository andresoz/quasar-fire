package com.challenge.quasarfire.communications.service;

import com.challenge.quasarfire.communications.domain.Position;

public class LocationAndMessageDTO {
    public LocationAndMessageDTO(Position position, String message) {
        this.position = position;
        this.message = message;
    }

    private Position position;
    private String message;

    public Position getPosition() {
        return position;
    }

    public String getMessage() {
        return message;
    }
}
