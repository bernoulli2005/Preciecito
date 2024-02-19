package com.mauri.preciecito.V1.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "preciodisco")
    private Float priceDisco;
    @Column(name = "preciocarrefour")
    private Float priceCarrefour;
    @Column(name = "preciovea")
    private Float priceVea;
    @Column(name = "preciocordiez")
    private Float priceCordiez;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha y hora")
    private Date date;

}