package com.reactive.livebus.model;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.reactive.livebus.BR;

import java.io.Serializable;

public class StopClass extends BaseObservable implements Serializable {
    String id;
    String stopName;
    String Lat;
    String Lng;
    boolean selected;
    boolean selected2;
    boolean displayError;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getStopName() {
        if (stopName == null)
            return "";
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
        notifyPropertyChanged(BR.stopName);
    }

    @Bindable({"displayError","stopName"})
    public String getStopNameError(){
        if (!isDisplayError()){
            return "";
        }

        if (getStopName().isEmpty()){
            return "Stop Field is Empty";
        }
        return "";
    }

    @Bindable
    public String getLat() {
        if (Lat == null)
            return "";
        return Lat;
    }

    public void setLat(String lat) {
        this.Lat = lat;
        notifyPropertyChanged(BR.lat);
    }

    @Bindable({"displayError","lat"})
    public String getLatError(){
        if (!isDisplayError()){
            return "";
        }

        if (getLat().isEmpty()){
            return "Lat Field is Empty";
        }
        return "";
    }

    @Bindable
    public String getLng() {
        if (Lng == null)
            return "";
        return Lng;
    }

    public void setLng(String lng) {
        this.Lng = lng;
        notifyPropertyChanged(BR.lng);
    }

    @Bindable({"displayError","lng"})
    public String getLngError(){
        if (!isDisplayError()){
            return "";
        }

        if (getLng().isEmpty()){
            return "Lng Field is Empty";
        }
        return "";
    }


    @Bindable
    public boolean isDisplayError(){
        return displayError;
    }

    public void setDisplayError(boolean displayError) {
        this.displayError = displayError;
        notifyPropertyChanged(BR.displayError);
    }

    @Bindable
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyPropertyChanged(BR.selected);
    }

    @Bindable
    public boolean isSelected2() {
        return selected2;
    }

    public void setSelected2(boolean selected2) {
        this.selected2 = selected2;
        notifyPropertyChanged(BR.selected2);
    }

    @Override
    public String toString() {
        return "StopClass{" +
                "id='" + id + '\'' +
                ", stopName='" + stopName + '\'' +
                ", Lat='" + Lat + '\'' +
                ", Lng='" + Lng + '\'' +
                ", selected=" + selected +
                ", selected2=" + selected2 +
                ", displayError=" + displayError +
                '}';
    }
}
