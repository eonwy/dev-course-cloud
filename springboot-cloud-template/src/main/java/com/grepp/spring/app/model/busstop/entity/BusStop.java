package com.grepp.spring.app.model.busstop.entity;

import com.grepp.spring.infra.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BusStop extends BaseEntity {
    
    @Id
    private Long id;
    private String stopsNumber;
    private String name;
    private String longitude;
    private String latitude;
    private String type;
}
