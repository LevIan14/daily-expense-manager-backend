package com.example.DEM.repository;

import com.example.DEM.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {
  CategoryEntity findByCategoryName (String category);
  List<CategoryEntity> findByCategoryGroup_CategoryGroupNameAndUserCategory_Username(String categoryGroup, String user);
  CategoryEntity findByCategoryId (int Id);
  void deleteByCategoryId(int id);
}
