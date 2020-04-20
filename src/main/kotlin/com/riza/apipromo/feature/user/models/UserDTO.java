package com.riza.apipromo.feature.user.models;

import javax.persistence.*;

@Entity(name = "user")
public class UserDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserLocationDTO locations;

    public UserDTO(String name) {
        this.name = name;
        this.locations = new UserLocationDTO();
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

    public UserLocationDTO getLocations() {
        return locations;
    }

    public void setLocations(UserLocationDTO locations) {
        this.locations = locations;
    }
}
