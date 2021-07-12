package com.zensar.olxadvertises.model;


import com.zensar.olxadvertises.dto.AdvertiseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "advertise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Advertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "title")
    String title;
    @Column(name = "price")
    double price;
    @Column(name = "category_id")
    int categoryId;
    @Column(name = "description")
    String description;
    @Column(name = "user_name")
    String userName;
    @Column(name = "created_date")
    Date createdDate;
    @Column(name = "modified_date")
    Date modifiedDate;
    @Column(name = "status_id")
    int statusId;

    public Advertise(AdvertiseDto advertiseDto) {
        this.title = advertiseDto.getTitle();
        this.price =advertiseDto.getPrice();
        this.categoryId = advertiseDto.getCategoryId();
        this.description = advertiseDto.getDescription();
        this.userName = advertiseDto.getUserName();
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.statusId = 1;
    }
}
