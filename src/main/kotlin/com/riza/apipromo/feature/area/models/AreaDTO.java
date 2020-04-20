package com.riza.apipromo.feature.area.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riza.apipromo.feature.promo.models.PromoDTO;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "area")
public class AreaDTO {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String points;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "areas")
    @JsonIgnoreProperties("areas")
    private Set<PromoDTO> promos;

    public AreaDTO(String name, String points, Set<PromoDTO> promos) {
        this.name = name;
        this.points = points;
        this.promos = promos;
    }

    public AreaDTO() {
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

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public Set<PromoDTO> getPromos() {
        return promos;
    }

    public void setPromos(Set<PromoDTO> promos) {
        this.promos = promos;
    }
}
