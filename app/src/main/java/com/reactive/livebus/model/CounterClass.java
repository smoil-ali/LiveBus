package com.reactive.livebus.model;

import java.io.Serializable;

public class CounterClass implements Serializable {
    String id;
    String busId;
    String counter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusId() {
        if (busId == null)
            return "";
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getCounter() {
        if (counter == null)
            return "";
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "CounterClass{" +
                "id='" + id + '\'' +
                ", busId='" + busId + '\'' +
                ", counter='" + counter + '\'' +
                '}';
    }
}
