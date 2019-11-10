package com.example.appprojet.utils;

/**
 * Data type to store location (with a longitude and a latitude)
 * Immutable
 */
public class Location {
    private final double longitude;
    private final double latitude;

    // create and initialize a point with given name and
    // (latitude, longitude) specified in degrees
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}