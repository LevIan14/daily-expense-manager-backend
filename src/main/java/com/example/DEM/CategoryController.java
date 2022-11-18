package com.example.DEM;

import com.example.DEM.entity.CategoryEntity;
import com.example.DEM.entity.HistoryEntitty;
import com.example.DEM.model.AddTransactionRequest;
import com.example.DEM.model.AddTransactionResponse;
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
    return categoryService.getCategory(categoryGroup);
  }

  @PutMapping("/update/{id}/{categoryGroup}/{category}")
  public CategoryResponse getUpdateHistory(@PathVariable ("id") int id,
                                         @PathVariable("categoryGroup") String categoryGroup,
                                         @PathVariable("category")String category){
    return categoryService.editCategory(id,categoryGroup,category);
  }
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
