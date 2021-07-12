package com.zensar.olxmasterdata.controller;

import com.zensar.olxmasterdata.dto.CategoryDto;
import com.zensar.olxmasterdata.dto.StatusDto;
import com.zensar.olxmasterdata.service.MasterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/advertise")
public class MasterDataController {

    @Autowired
    MasterDataService masterDataService;

    @GetMapping("/category")
    public ResponseEntity<?> getCategories() {
        return masterDataService.getCategories();
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        return masterDataService.getStatus();
    }
}
