package com.reactive.livebus.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


import com.reactive.livebus.BR;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BusClass extends BaseObservable implements Serializable {
    String id;
    String busNumber;
    String startPoint;
    String startLat;
    String startLng;
    String destination;
    String destinationLat;
    String destinationLng;
    String startTime;
    String arrivalTime;
    String totalSeats;
    String fare;
    String formattedFare;
    String actualPrice;
    List<StopClass> list;
    boolean displayError;

    @Bindable
    public String getId() {
        if (id == null)
            return "";
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getBusNumber() {
        if (busNumber == null)
            return "";
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
        notifyPropertyChanged(BR.busNumber);
    }

    @Bindable({"displayError","busNumber"})
    public String getBusNumberError(){
        if (!isDisplayError()){
            return "";
        }

        if (getBusNumber().isEmpty()){
            return "Bus Number Field is Empty";
        }
        return "";
    }

    @Bindable
    public String getStartPoint() {
        if (startPoint == null)
            return "";
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
        notifyPropertyChanged(BR.startPoint);
    }

    @Bindable({"displayError","startPoint"})
    public String getStartPointError(){
        if (!isDisplayError()){
            return "";
        }
        if (getStartPoint().isEmpty()){
            return "Start Point is Empty";
        }
        return "";
    }

    @Bindable
    public String getStartLat() {
        if (startLat == null)
            return "";
        return startLat;
    }

    public void setStartLat(String startLat) {
        this.startLat = startLat;
        notifyPropertyChanged(BR.startLat);
    }

    @Bindable({"displayError","startLat"})
    public String getStartPointLatError(){
        if (!isDisplayError()){
            return "";
        }
        if (getStartLat().isEmpty()){
            return "Start Point Lat is Empty";
        }
        return "";
    }

    @Bindable
    public String getStartLng() {
        if (startLng == null){
            return "";
        }
        return startLng;
    }

    public void setStartLng(String startLng) {
        this.startLng = startLng;
        notifyPropertyChanged(BR.startLng);
    }

    @Bindable({"displayError","startLng"})
    public String getStartPointLngError(){
        if (!isDisplayError()){
            return "";
        }
        if (getStartLng().isEmpty()){
            return "Start Point Lng is Empty";
        }
        return "";
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

    @Bindable({"displayError","destination"})
    public String getDestinationError(){
        if (!isDisplayError()){
            return "";
        }
        if (getDestination().isEmpty()){
            return "Destination is Empty";
        }
        return "";
    }

    @Bindable
    public String getDestinationLat() {
        if (destinationLat == null)
            return "";
        return destinationLat;
    }

    public void setDestinationLat(String destinationLat) {
        this.destinationLat = destinationLat;
        notifyPropertyChanged(BR.destinationLat);
    }

    @Bindable({"displayError","destinationLat"})
    public String getDestinationLatError(){
        if (!isDisplayError()){
            return "";
        }
        if (getDestinationLat().isEmpty()){
            return "Destination Lat is Empty";
        }
        return "";
    }

    @Bindable
    public String getDestinationLng() {
        if (destinationLng == null)
            return "";
        return destinationLng;

    }

    public void setDestinationLng(String destinationLng) {
        this.destinationLng = destinationLng;
        notifyPropertyChanged(BR.destinationLng);
    }

    @Bindable({"displayError","destinationLng"})
    public String getDestinationLngError(){
        if (!isDisplayError()){
            return "";
        }
        if (getDestinationLng().isEmpty()){
            return "Destination Lng is Empty";
        }
        return "";
    }

    @Bindable
    public String getStartTime() {
        if (startTime == null)
            return "";
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
        notifyPropertyChanged(BR.startTime);
    }

    @Bindable({"displayError","startTime"})
    public String getStartTimeError(){
        if (!isDisplayError()){
            return "";
        }
        if (getStartTime().isEmpty()){
            return "Start time is Empty";
        }
        return "";
    }


    @Bindable
    public String getArrivalTime() {
        if (arrivalTime == null)
            return "";
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
        notifyPropertyChanged(BR.arrivalTime);
    }

    @Bindable({"displayError","arrivalTime"})
    public String getArrivalTimeError(){
        if (!isDisplayError()){
            return "";
        }
        if (getArrivalTime().isEmpty()){
            return "Arrival time is Empty";
        }
        return "";
    }


    @Bindable
    public String getTotalSeats() {
        if (totalSeats == null)
            return "";
        return totalSeats;
    }

    public void setTotalSeats(String totalSeats) {
        this.totalSeats = totalSeats;
        notifyPropertyChanged(BR.totalSeats);
    }

    @Bindable({"displayError","totalSeats"})
    public String getTotalSeatsError(){
        if (!isDisplayError()){
            return "";
        }
        if (getTotalSeats().isEmpty()){
            return "Total seats is Empty";
        }
        return "";
    }


    @Bindable
    public List<StopClass> getList() {
        if (list == null)
            return new ArrayList<>();
        return list;
    }

    public void setList(List<StopClass> list) {
        this.list = list;
        notifyPropertyChanged(BR.list);
    }

    @Bindable({"displayError","list"})
    public String getStopListError(){
        if (!isDisplayError()){
            return "";
        }
        if (getList().size() == 0){
            return "Stop list is Empty";
        }
        return "";
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

    @Bindable({"displayError","fare"})
    public String getFareError(){
        if (!isDisplayError()){
            return "";
        }
        if (getFare().isEmpty()){
            return "Fare Field is empty";
        }
        return "";
    }


    @Bindable({"fare"})
    public String getFormattedFare() {
        return getFare()+"-/Rs";
    }

    public void setFormattedFare(String formattedFare) {
        this.formattedFare = formattedFare;
        notifyPropertyChanged(BR.formattedFare);
    }

    public String getActualPrice() {
        if (actualPrice == null)
            return "";
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    @Bindable
    public boolean isDisplayError(){
        return displayError;
    }

    public void setDisplayError(boolean displayError) {
        this.displayError = displayError;
        notifyPropertyChanged(BR.displayError);
    }

}
