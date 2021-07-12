package com.zensar.olxmasterdata.dto;

import com.zensar.olxmasterdata.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    int id;
    String category;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.category = category.getCategory();
    }
}
