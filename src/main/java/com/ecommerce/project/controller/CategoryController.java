package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")      // In this RequestMapping, I have specified the common path which is applicable to all endpoints so that there is no need to specify /api in all the endpoints below.
public class CategoryController {

//    @Autowired                                                  // You can either use field injection i.e @autowired or constructor injection (lines commented below shows constructor injection)
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

//    @GetMapping("/api/public/categories")
    @RequestMapping(value = "/public/categories", method = RequestMethod.GET)  // @RequestMapping is also similar to other annotations but here we will be giving two parameters value and method. In method parameter, we will be specifying the GET,POST,PUT,DELETE methods.
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name= "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name= "pageSize"  , defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy" , defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
            @RequestParam(name = "sortOrder" , defaultValue = AppConstants.SORT_DIR) String sortOrder
    ){
        CategoryResponse categoryResponse =  categoryService.getAllCategories(pageNumber, pageSize , sortBy , sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

//    @PostMapping("/api/public/categories")
@RequestMapping(value = "/public/categories", method = RequestMethod.POST)
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
       CategoryDTO savedCategoryDTO =  categoryService.createCategory(categoryDTO);
       return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

//    @DeleteMapping("api/admin/categories/{categoryId}")
@RequestMapping(value = "/admin/categories/{categoryId}", method = RequestMethod.DELETE)
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){
            CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory, HttpStatus.OK);
//            return ResponseEntity.ok(status);
//            return ResponseEntity.status(HttpStatus.OK).body(status);   //The above lines 40 and 41 will also work. Currently we use line 40 and line 40 is the most commonly used syntax.
    }
//    @PutMapping  ("api/public/categories/{categoryId}")
@RequestMapping(value = "/public/categories/{categoryId}", method = RequestMethod.PUT)
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO , @PathVariable Long categoryId){
        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
            return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }



}
