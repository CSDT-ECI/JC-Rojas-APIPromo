package com.riza.apipromo.feature.user.models;

import javax.persistence.*;

@Entity(name = "user")
public class UserDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String name;
    private String fcmId;

    @Embedded
    private UserLocation locations;

    public UserDTO(String name) {
        this.name = name;
        this.fcmId = "";
        this.locations = new UserLocation();
    }

    public UserDTO() {
    }

    public String getFcmId() {
        return fcmId;
    }

    public void setFcmId(String fcmId) {
        this.fcmId = fcmId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserLocation getLocations() {
        return locations;
    }

    public void setLocations(UserLocation locations) {
        this.locations = locations;
    }
}
