package com.reactive.livebus.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


import com.reactive.livebus.BR;

import java.io.Serializable;

public class BookingClass extends BaseObservable implements Serializable {
    String id;
    BusClass busClass;
    String userId;
    String seats;
    String fare;
    String formattedFare;
    String destination;

    public String getId() {
        if (id == null)
            return "";
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public BusClass getBusClass() {
        if (busClass == null)
            return new BusClass();
        return busClass;
    }


    public void setBusClass(BusClass busClass) {
        this.busClass = busClass;
        notifyPropertyChanged(BR.busClass);
    }

    public String getUserId() {
        if (userId == null)
            return "";
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Bindable
    public String getSeats() {
        if (seats == null)
            return "";
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
        notifyPropertyChanged(BR.seats);
    }

    @Bindable
    public String getFare() {
        if (fare == null)
            return "";
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
        notifyPropertyChanged(BR.fare);
    }

    @Bindable({"fare"})
    public String getFormattedFare() {
        return getFare()+"-/Rs";
    }

    public void setFormattedFare(String formattedFare) {
        this.formattedFare = formattedFare;
        notifyPropertyChanged(BR.formattedFare);
    }

    @Bindable
    public String getDestination() {
        if (destination == null)
            return "";
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
        notifyPropertyChanged(BR.destination);
    }

    @Override
    public String toString() {
        return "BookingClass{" +
                "id='" + id + '\'' +
                ", busClass=" + busClass +
                ", userId='" + userId + '\'' +
                ", seats='" + seats + '\'' +
                ", fare='" + fare + '\'' +
                ", formattedFare='" + formattedFare + '\'' +
                '}';
    }
}
