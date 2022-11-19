package com.example.DEM.controller;

import com.example.DEM.BadRequestException;
import com.example.DEM.entity.Category;
import com.example.DEM.model.CategoryResponse;
import com.example.DEM.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
  @Autowired
  private ICategoryService categoryService;

  @GetMapping("/list/{categoryGroup}")
  public List<CategoryResponse> getListCategory(@PathVariable("categoryGroup") String categoryGroup){
    return categoryService.getCategory(categoryGroup.toUpperCase());
  }

  @GetMapping("/detail/{categoryId}/{userId}")
  public CategoryResponse getDetailCategory(@PathVariable ("categoryId") int categoryId,
                                            @PathVariable("userId") int userId) {
    return categoryService.getDetailCategory(categoryId, userId);
  }

//  @PutMapping("/update/{categoryId}&{userId}")
//  public CategoryResponse getUpdateHistory(@PathVariable ("categoryId") int categoryId,
//                                         @PathVariable("userId") int userId,
//                                         @RequestBody() Category category){
//    return categoryService.editCategory(categoryId, userId, category);
//  }

  @PutMapping("/delete/{id}")
  public Boolean getDeleteHistory(@PathVariable ("id") int id) throws BadRequestException {
    return categoryService.deleteCategory(id);
  }

  @PostMapping("/add/{categoryGroup}/{category}")
  public CategoryResponse addTransaction(
      @PathVariable("categoryGroup") String categoryGroup,
      @PathVariable("category")String category
  ){
    return categoryService.addCategory(categoryGroup,category);
  }
}
