package com.zensar.olxmasterdata.service;

import com.zensar.olxmasterdata.dto.CategoryDto;
import com.zensar.olxmasterdata.dto.StatusDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MasterDataService {
    ResponseEntity<List<CategoryDto>> getCategories();

    ResponseEntity<List<StatusDto>> getStatus();
}
