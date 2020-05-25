package com.riza.apipromo.feature.user.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserLocation {

    @Column(columnDefinition = "text")
    private String monday;
    @Column(columnDefinition = "text")
    private String tuesday;
    @Column(columnDefinition = "text")
    private String wednesday;
    @Column(columnDefinition = "text")
    private String thursday;
    @Column(columnDefinition = "text")
    private String friday;
    @Column(columnDefinition = "text")
    private String saturday;
    @Column(columnDefinition = "text")
    private String sunday;


    public UserLocation() {
        monday = "[]";
        tuesday = "[]";
        wednesday = "[]";
        thursday = "[]";
        friday = "[]";
        saturday = "[]";
        sunday = "[]";
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }
}
