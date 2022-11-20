package com.example.DEM.controller;

import com.example.DEM.BadRequestException;
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

  @GetMapping("/list/{categoryGroupId}")
  public List<CategoryResponse> getListCategory(@PathVariable("categoryGroupId") int  categoryGroupId){
    return categoryService.getCategory(categoryGroupId);
  }

  @GetMapping("/detail/{categoryId}")
  public CategoryResponse getDetailCategory(@PathVariable ("categoryId") int categoryId) {
    return categoryService.getDetailCategory(categoryId);
  }

  @PutMapping("/update/{id}")
  public CategoryResponse getUpdateHistory(@PathVariable ("id") int categoryId,
                                           @RequestBody CategoryRequest categoryRequest) {
    return categoryService.editCategory(categoryId, categoryRequest);
  }

  @DeleteMapping("/delete/{id}")
  public Boolean getDeleteHistory(@PathVariable ("id") int id) throws BadRequestException {
    return categoryService.deleteCategory(id);
  }

  @PostMapping("/add")
  public CategoryResponse addTransaction(
      @RequestBody CategoryRequest categoryRequest
      ){
    return categoryService.addCategory(categoryRequest);
  }

}
