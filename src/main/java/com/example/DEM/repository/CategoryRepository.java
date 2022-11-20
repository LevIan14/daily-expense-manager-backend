package com.example.DEM.repository;

import com.example.DEM.entity.Category;
import com.example.DEM.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
  Category findByCategoryId (int category);
  List<Category> findByCategoryGroup_CategoryGroupNameAndUserCategory_Username(int categoryGroupId, String user);
  @Query(value = "select * from category where category_group_id like ?1 and user_id like ?2", nativeQuery = true)
  List<Category> findByCategoryGroupIdAndUserId(int categoryGroupId, int userId);
  Category findByCategoryIdAndUserCategory (int categoryId, Optional<User> userCategory);
  void deleteByCategoryId(int id);
}
