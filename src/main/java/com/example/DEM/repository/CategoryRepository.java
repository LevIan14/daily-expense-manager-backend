package com.example.DEM.repository;

import com.example.DEM.entity.Category;
import com.example.DEM.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
  Category findByCategoryName (String category);
  List<Category> findByCategoryGroup_CategoryGroupNameAndUserCategory_Username(String categoryGroup, String user);
  Category findByCategoryId (int Id);
  Category findByCategoryIdAndUserCategory (int categoryId, Optional<User> userCategory);
  void deleteByCategoryId(int id);
}
