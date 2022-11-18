package com.example.DEM.service;

import com.example.DEM.BadRequestException;
import com.example.DEM.entity.CategoryEntity;
import com.example.DEM.model.CategoryResponse;

import java.util.List;

public interface ICategoryService {
  List<CategoryResponse> getCategory (String categoryGroup);
  CategoryResponse editCategory (int id, String categoryGroup, String category);
  CategoryResponse addCategory (String categoryGroup, String category);
  Boolean deleteCategory(int id) throws BadRequestException;
}
