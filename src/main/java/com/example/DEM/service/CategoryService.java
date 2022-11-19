package com.example.DEM.service;

import com.example.DEM.BadRequestException;
import com.example.DEM.entity.Category;
import com.example.DEM.entity.CategoryGroup;
import com.example.DEM.entity.Transaction;
import com.example.DEM.entity.User;
import com.example.DEM.model.CategoryRequest;
import com.example.DEM.model.CategoryResponse;
import com.example.DEM.repository.CategoryGroupRepository;
import com.example.DEM.repository.CategoryRepository;
import com.example.DEM.repository.TransactionRepository;
import com.example.DEM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
  @Autowired
  CategoryRepository categoryRepository;
  @Autowired
  CategoryGroupRepository categoryGroupRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  TransactionRepository transactionRepository;
  @Override
  public List<CategoryResponse> getCategory(String categoryGroup) {
    String username = getUser();
    List<CategoryResponse> responses = new ArrayList<>();
    List<Category> category = categoryRepository.findByCategoryGroup_CategoryGroupNameAndUserCategory_Username(categoryGroup, username);
    category.forEach(a->responses.add(CategoryResponse.builder().categoryId(a.getCategoryId()).categoryGroup(a.getCategoryGroup().getCategoryGroupName())
        .categoryName(a.getCategoryName()).build()));
    return responses;
  }

  @Override
  public CategoryResponse getDetailCategory(int categoryId, int userId) {
    Optional<User> user = userRepository.findById(userId);
    Category category = categoryRepository.findByCategoryIdAndUserCategory(categoryId, user);
    return CategoryResponse.builder()
            .categoryId(category.getCategoryId())
            .categoryGroup(category.getCategoryGroup().getCategoryGroupName())
            .categoryName(category.getCategoryName())
            .build();
  }


  @Override
  public CategoryResponse editCategory(int id, CategoryRequest request) {
    Category categoryEntity = categoryRepository.findByCategoryId(id);
    CategoryGroup categoryGroupEntity= categoryGroupRepository.findByCategoryGroupId(request.getCategoryGroupId());
    categoryEntity.setCategoryName(request.getName());
    categoryEntity.setCategoryGroup(categoryGroupEntity);
    return CategoryResponse.builder().categoryId(categoryEntity.getCategoryId())
        .categoryName(categoryEntity.getCategoryName())
        .categoryGroup(categoryEntity.getCategoryGroup().getCategoryGroupName()).build();
  }

  @Override
  public CategoryResponse addCategory(CategoryRequest categoryRequest) {
    String username=getUser();
    User user= userRepository.findByUsername(username);
    CategoryGroup categoryGroupEntity= categoryGroupRepository.findByCategoryGroupId(categoryRequest.getCategoryGroupId());
    Category categoryEntity= new Category();
    categoryEntity.setCategoryName(categoryRequest.getName());
    categoryEntity.setCategoryGroup(categoryGroupEntity);
    categoryEntity.setUserCategory(user);
    categoryRepository.save(categoryEntity);

    return CategoryResponse.builder()
        .categoryId(categoryEntity.getCategoryId())
        .categoryGroup(categoryEntity.getCategoryGroup().getCategoryGroupName())
        .categoryName(categoryEntity.getCategoryName())
        .build();
  }

  @Override
  public Boolean deleteCategory(int id) throws BadRequestException {
    String username = getUser();
    Category category =categoryRepository.findByCategoryId(id);
    Transaction history= transactionRepository.findByCategoryAndUserHistory_Username(category,username);
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
      return authentication.getName();
    }
    return null;
  }
}
