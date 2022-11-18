package com.example.DEM.repository;

import com.example.DEM.entity.CategoryEntity;
import com.example.DEM.entity.CategoryGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroupEntity,Integer> {
  CategoryGroupEntity findByCategoryGroupName (String name);
}
