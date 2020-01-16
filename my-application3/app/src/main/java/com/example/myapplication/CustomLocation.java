package com.example.myapplication;

class CustomLocation
{
    Double lat;
    Double longitude;

    public CustomLocation(Double lat, Double longitude) {
        this.lat = lat;
        this.longitude = longitude;
    }
    public CustomLocation()
    {

    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
