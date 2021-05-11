package com.reactive.livebus.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.reactive.livebus.BR;

import java.io.Serializable;

public class DriverClass extends BaseObservable implements Serializable {
    String id;
    String name;
    String cnic;
    String password;
    String assignedBus;
    boolean displayError;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable({"displayError","name"})
    public String getNameError(){
        if (!isDisplayError()){
            return "";
        }
        if (getName().isEmpty()){
            return "Name is Empty";
        }
        return "";
    }

    @Bindable
    public String getCnic() {
        if (cnic == null)
            return "";
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
        notifyPropertyChanged(BR.cnic);
    }

    @Bindable({"displayError","cnic"})
    public String getCnicError(){
        if (!isDisplayError()){
            return "";
        }
        if (getCnic().isEmpty()){
            return "ID is empty";
        }
        return "";
    }

    @Bindable
    public String getPassword() {
        if (password == null)
            return "";
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable({"displayError","password"})
    public String getPasswordError(){
        if (!isDisplayError()){
            return "";
        }
        if (getPassword().isEmpty()){
            return "Password is empty";
        }
        return "";
    }

    @Bindable
    public String getAssignedBus() {
        if (assignedBus == null)
            return "";
        return assignedBus;
    }

    public void setAssignedBus(String assignedBus) {
        this.assignedBus = assignedBus;
        notifyPropertyChanged(BR.assignedBus);
    }

    @Bindable({"displayError","assignedBus"})
    public String getAssignedBusError(){
        if (!isDisplayError()){
            return "";
        }
        if (getAssignedBus().isEmpty()){
            return "Bus number is empty";
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

    @Override
    public String toString() {
        return "DriverClass{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cnic='" + cnic + '\'' +
                ", password='" + password + '\'' +
                ", displayError=" + displayError +
                '}';
    }
}
