package com.riza.apipromo.feature.promo.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.riza.apipromo.feature.area.models.AreaDTO;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "promo")
public class PromoDTO {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private PromoType type;

    private Integer value;

    private String service;

    private String description;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "promo_area",
            joinColumns = @JoinColumn(name = "promo_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    @JsonIgnoreProperties("promos")
    private Set<AreaDTO> areas;

    public PromoDTO(String code, Date startDate, Date endDate, PromoType type, Integer value, String service, String description, Set<AreaDTO> areas) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.value = value;
        this.service = service;
        this.description = description;
        this.areas = areas;
    }

    public PromoDTO() {
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public PromoType getType() {
        return type;
    }

    public void setType(PromoType type) {
        this.type = type;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Set<AreaDTO> getAreas() {
        return areas;
    }

    public void setAreas(Set<AreaDTO> areas) {
        this.areas = areas;
    }
}
