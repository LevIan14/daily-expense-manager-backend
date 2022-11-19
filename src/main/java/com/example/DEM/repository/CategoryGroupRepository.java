package com.example.DEM.repository;

import com.example.DEM.entity.CategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryGroupRepository extends JpaRepository<CategoryGroup,Integer> {
  CategoryGroup findByCategoryGroupName (String name);
}
