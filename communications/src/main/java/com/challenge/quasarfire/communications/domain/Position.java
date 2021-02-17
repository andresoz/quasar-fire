package com.challenge.quasarfire.communications.domain;

public class Position {
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }
    public Position(float[] coordinates) {
        this.x = coordinates[0];
        this.y = coordinates[1];
    }

    private float x;
    private float y;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
