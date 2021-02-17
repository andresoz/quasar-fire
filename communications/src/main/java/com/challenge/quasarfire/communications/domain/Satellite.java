package com.challenge.quasarfire.communications.domain;

public class Satellite {
    private String name;
    private float distance;
    private String[] message;

    public Satellite(String name, float distance, String[] message) {
        this.name = name;
        this.distance = distance;
        this.message = message;
    }

    public float getDistance() {
        return distance;
    }

    public String[] getMessage() {
        return message;
    }
}
