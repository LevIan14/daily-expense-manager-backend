package com.example.DEM.controller;

import com.example.DEM.BadRequestException;
import com.example.DEM.entity.Category;
import com.example.DEM.model.CategoryRequest;
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

  @PutMapping("/update/{id}")
  public CategoryResponse getUpdateHistory (@PathVariable ("id") int categoryId,
                                         @RequestBody CategoryRequest categoryRequest){
    return categoryService.editCategory(categoryId, categoryRequest);
  }

  @PutMapping("/delete/{id}")
  public Boolean getDeleteHistory(@PathVariable ("id") int id) throws BadRequestException {
    return categoryService.deleteCategory(id);
  }

  @PostMapping("/add/}")
  public CategoryResponse addTransaction(
      @RequestBody CategoryRequest categoryRequest
      ){
    return categoryService.addCategory(categoryRequest);
  }
}
