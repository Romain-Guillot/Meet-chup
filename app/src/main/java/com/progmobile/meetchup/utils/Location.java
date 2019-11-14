package com.progmobile.meetchup.utils;

import androidx.annotation.Nullable;

/**
 * Data type to store location (with a longitude and a latitude)
 * Immutable
 */
public class Location {
    private String location = null;

    private Double longitude = null;
    private Double latitude = null;


    // create and initialize a point with given name and
    // (latitude, longitude) specified in degrees
    public Location(String location) {
        this.location = location;
    }

    public Location(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Nullable
    public String getLocation() {
        return location;
    }

    @Nullable
    public Double getLatitude() {
        return latitude;
    }

    @Nullable
    public Double getLongitude() {
        return longitude;
    }
}