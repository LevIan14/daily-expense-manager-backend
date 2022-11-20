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
  public List<CategoryResponse> getCategory(int categoryGroupId) {
    String username = getUser();
    User user = userRepository.findByUsername(username);
    List<CategoryResponse> responses = new ArrayList<>();
//    List<Category> category = categoryRepository.findByCategoryGroup_CategoryGroupNameAndUserCategory_Username(categoryGroupId, username);
    List<Category> category = categoryRepository.findByCategoryGroupIdAndUserId(categoryGroupId, user.getId());
    category.forEach(a -> responses.add(CategoryResponse.builder()
            .categoryId(a.getCategoryId())
            .categoryGroupId(a.getCategoryGroup().getCategoryGroupId())
            .categoryName(a.getCategoryName())
            .build()));
    return responses;
  }

  @Override
  public CategoryResponse getDetailCategory(int categoryId) {
    String username = getUser();
    User user = userRepository.findByUsername(username);
    Category category = categoryRepository.findByCategoryIdAndUserCategory(categoryId, Optional.ofNullable(user));
    return CategoryResponse.builder()
            .categoryId(category.getCategoryId())
            .categoryGroupId(a.getCategoryGroup().getCategoryGroupId())
            .categoryName(category.getCategoryName())
            .build();
  }


  @Override
  public CategoryResponse editCategory(int id, CategoryRequest request) {
    Category categoryEntity = categoryRepository.findByCategoryId(id);
    CategoryGroup categoryGroupEntity = categoryGroupRepository.findByCategoryGroupId(request.getCategoryGroupId());
    categoryEntity.setCategoryId(id);
    categoryEntity.setCategoryName(request.getName());
    categoryEntity.setCategoryGroup(categoryGroupEntity);

    categoryRepository.save(categoryEntity);

    return CategoryResponse.builder()
            .categoryId(id)
            .categoryName(categoryEntity.getCategoryName())
            .categoryGroupId(a.getCategoryGroup().getCategoryGroupId())
            .build();
  }

  @Override
  public CategoryResponse addCategory(CategoryRequest categoryRequest) {
    String username = getUser();
    User user= userRepository.findByUsername(username);
    CategoryGroup categoryGroupEntity = categoryGroupRepository.findByCategoryGroupId(categoryRequest.getCategoryGroupId());
    Category categoryEntity= new Category();
    categoryEntity.setCategoryName(categoryRequest.getName());
    categoryEntity.setCategoryGroup(categoryGroupEntity);
    categoryEntity.setUserCategory(user);
    categoryRepository.save(categoryEntity);

    return CategoryResponse.builder()
        .categoryId(categoryEntity.getCategoryId())
        .categoryGroupId(a.getCategoryGroup().getCategoryGroupId())
        .categoryName(categoryEntity.getCategoryName())
        .build();
  }

  @Override
  public Boolean deleteCategory(int id) throws BadRequestException {
    String username = getUser();
    Category category = categoryRepository.findByCategoryId(id);
    Transaction transaction = transactionRepository.findByCategoryAndUserHistory_Username(category,username);
    if (transaction == null){
      categoryRepository.deleteById(id);
      return true;
    }
    else {
      throw new BadRequestException("Kategori tersebut sedang digunakan");
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
