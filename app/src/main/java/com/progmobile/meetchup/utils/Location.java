package com.progmobile.meetchup.utils;

/**
 * Data type to store location (with a longitude and a latitude)
 * Immutable
 */
//public class Location {
//    private final double longitude;
//    private final double latitude;
//
//    // create and initialize a point with given name and
//    // (latitude, longitude) specified in degrees
//    public Location(double latitude, double longitude) {
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//}

public class Location {
    private String location;

    // create and initialize a point with given name and
    // (latitude, longitude) specified in degrees
    public Location(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}