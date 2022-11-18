package com.example.DEM.service;

import com.example.DEM.BadRequestException;
import com.example.DEM.entity.CategoryEntity;
import com.example.DEM.entity.CategoryGroupEntity;
import com.example.DEM.entity.HistoryEntitty;
import com.example.DEM.entity.UserEntity;
import com.example.DEM.model.CategoryResponse;
import com.example.DEM.repository.CategoryGroupRepository;
import com.example.DEM.repository.CategoryRepository;
import com.example.DEM.repository.HistoryRepository;
import com.example.DEM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {
  @Autowired
  CategoryRepository categoryRepository;
  @Autowired
  CategoryGroupRepository categoryGroupRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  HistoryRepository historyRepository;
  @Override
  public List<CategoryResponse> getCategory(String categoryGroup) {
    String username=getUser();
    List<CategoryResponse> responses= new ArrayList<>();
    List<CategoryEntity> categoryEntity=categoryRepository.findByCategoryGroup_CategoryGroupNameAndUserCategory_Username(categoryGroup, username);
    categoryEntity.forEach(a->responses.add(CategoryResponse.builder().id(a.getCategoryId()).categoryGroup(a.getCategoryGroup().getCategoryGroupName())
        .categoryName(a.getCategoryName()).build()));
    return responses;
  }

  @Override
  public CategoryResponse editCategory(int id, String categoryGroup, String category) {
    CategoryEntity categoryEntity = categoryRepository.findByCategoryId(id);
    CategoryGroupEntity categoryGroupEntity= categoryGroupRepository.findByCategoryGroupName(categoryGroup);
    categoryEntity.setCategoryName(category);
    categoryEntity.setCategoryGroup(categoryGroupEntity);
    return CategoryResponse.builder().id(categoryEntity.getCategoryId())
        .categoryName(categoryEntity.getCategoryName())
        .categoryGroup(categoryEntity.getCategoryGroup().getCategoryGroupName()).build();
  }

  @Override
  public CategoryResponse addCategory(String categoryGroup, String category) {
    String username=getUser();
    UserEntity user= userRepository.findByUsername(username);
    CategoryGroupEntity categoryGroupEntity= categoryGroupRepository.findByCategoryGroupName(categoryGroup);
    CategoryEntity categoryEntity= new CategoryEntity();
    categoryEntity.setCategoryName(category);
    categoryEntity.setCategoryGroup(categoryGroupEntity);
    categoryEntity.setUserCategory(user);
    categoryRepository.save(categoryEntity);

    return CategoryResponse.builder()
        .id(categoryEntity.getCategoryId())
        .categoryGroup(categoryEntity.getCategoryGroup().getCategoryGroupName())
        .categoryName(categoryEntity.getCategoryName())
        .build();
  }

  @Override
  public Boolean deleteCategory(int id) throws BadRequestException {
    String username=getUser();
    CategoryEntity categoryEntity=categoryRepository.findByCategoryId(id);
    HistoryEntitty history= historyRepository.findByCategoryAndUserHistory_Username(categoryEntity,username);
    if (history!= null){
      categoryRepository.deleteByCategoryId(id);
      return true;
    }
    else {
      throw new BadRequestException("Categori tersebut sedang digunakan");
    }


  }
  public String getUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUserName = authentication.getName();
      return currentUserName;
    }
    return null;
  }
}
