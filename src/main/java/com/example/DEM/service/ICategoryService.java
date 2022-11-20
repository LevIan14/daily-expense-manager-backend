package com.example.DEM.service;

import com.example.DEM.BadRequestException;
import com.example.DEM.model.CategoryRequest;
import com.example.DEM.model.CategoryResponse;

import java.util.List;

public interface ICategoryService {
  List<CategoryResponse> getCategory (String categoryGroup);

  CategoryResponse getDetailCategory(int categoryId);

  CategoryResponse editCategory(int id, CategoryRequest categoryRequest);

  CategoryResponse addCategory (CategoryRequest categoryRequest);

  Boolean deleteCategory(int id) throws BadRequestException;
}
