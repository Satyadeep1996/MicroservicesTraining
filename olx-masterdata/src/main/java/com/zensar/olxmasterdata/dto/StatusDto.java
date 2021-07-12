package com.zensar.olxmasterdata.dto;

import com.zensar.olxmasterdata.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto {

    int id;
    String status;

    public StatusDto(Status status) {
        this.id = status.getId();
        this.status = status.getStatus();
    }
}
