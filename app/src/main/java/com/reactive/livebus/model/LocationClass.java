package com.reactive.livebus.model;

import java.io.Serializable;

public class LocationClass implements Serializable {
    String id;
    String lat;
    String lng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        if (lat == null)
            return "";
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        if (lng == null)
            return "";
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
