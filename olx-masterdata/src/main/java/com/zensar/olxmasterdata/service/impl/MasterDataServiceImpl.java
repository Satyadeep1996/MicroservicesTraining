package com.zensar.olxmasterdata.service.impl;

import com.zensar.olxmasterdata.dao.CategoryDao;
import com.zensar.olxmasterdata.dao.StatusDao;
import com.zensar.olxmasterdata.dto.CategoryDto;
import com.zensar.olxmasterdata.dto.StatusDto;
import com.zensar.olxmasterdata.model.Category;
import com.zensar.olxmasterdata.model.Status;
import com.zensar.olxmasterdata.service.MasterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MasterDataServiceImpl implements MasterDataService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    StatusDao statusDao;

    @Override
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<Category> categories = categoryDao.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categories.forEach(category -> categoryDtos.add(new CategoryDto(category)));
        return ResponseEntity.ok(categoryDtos);
    }

    @Override
    public ResponseEntity<List<StatusDto>> getStatus() {
        List<Status> statuses = statusDao.findAll();
        List<StatusDto> statusDtos = new ArrayList<>();
        statuses.forEach(status -> statusDtos.add(new StatusDto(status)));
        return ResponseEntity.ok(statusDtos);
    }
}
