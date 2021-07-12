package com.zensar.olxadvertises.dto;

import com.zensar.olxadvertises.model.Advertise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvertiseDto {
    int id;
    String title;
    double price;
    int categoryId;
    String category;
    String description;
    String userName;
    Date createdDate;
    Date modifiedDate;
    int statusId;
    String status;

    public AdvertiseDto(Advertise advertise) {
        this.id = advertise.getId();
        this.title = advertise.getTitle();
        this.price = advertise.getPrice();
        this.description = advertise.getDescription();
        this.userName = advertise.getUserName();
        this.createdDate = advertise.getCreatedDate();
        this.modifiedDate = advertise.getModifiedDate();
    }
}
