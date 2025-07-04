package com.ecommerce.project.controller;


import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/admin/categories/{categoryId}/product"  , method = RequestMethod.POST)
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO , @PathVariable Long categoryId){
          ProductDTO savedProductDTO = productService.addProduct(categoryId , productDTO);
          return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/public/products" , method = RequestMethod.GET)
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "keyword" , required = false) String keyword,
            @RequestParam(name = "category" , required = false) String category,
            @RequestParam(name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(name = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(name = "sortBy" , defaultValue = AppConstants.SORT_PRODUCTS_BY , required = false) String sortBy,
            @RequestParam(name = "sortOrder" , defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder
    ){
        ProductResponse productResponse = productService.getAllProducts(pageNumber , pageSize , sortBy , sortOrder, keyword, category);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/public/categories/{categoryId}/products" , method = RequestMethod.GET)
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId ,   @RequestParam(name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
                                                                 @RequestParam(name = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
                                                                 @RequestParam(name = "sortBy" , defaultValue = AppConstants.SORT_PRODUCTS_BY , required = false) String sortBy,
                                                                 @RequestParam(name = "sortOrder" , defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder){
        ProductResponse productResponse =  productService.searchByCategory(categoryId , pageNumber , pageSize , sortBy , sortOrder);
        return new ResponseEntity<>(productResponse , HttpStatus.OK);
    }

    @RequestMapping(value = "/public/products/keyword/{keyword}" , method = RequestMethod.GET)
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword ,   @RequestParam(name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
                                                               @RequestParam(name = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
                                                               @RequestParam(name = "sortBy" , defaultValue = AppConstants.SORT_PRODUCTS_BY , required = false) String sortBy,
                                                               @RequestParam(name = "sortOrder" , defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder){
       ProductResponse productResponse =  productService.searchProductByKeyword(keyword , pageNumber , pageSize , sortBy , sortOrder);
        return new ResponseEntity<>(productResponse , HttpStatus.FOUND);
    }

    @RequestMapping(value = "/admin/products/{productId}" , method = RequestMethod.PUT)
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO , @PathVariable Long productId){
        ProductDTO updatedProductDTO = productService.updateProduct(productId , productDTO);
        return new ResponseEntity<>(updatedProductDTO , HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/products/{productId}" , method = RequestMethod.DELETE)
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct , HttpStatus.OK);
    }

//    @RequestMapping(value = "/products/{productId}/image" , method = RequestMethod.PUT)
//    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId , @RequestParam("image")MultipartFile image) throws IOException {
//        ProductDTO updatedProduct = productService.updateProductImage(productId , image);
//        return new ResponseEntity<>(updatedProduct , HttpStatus.OK);
//    }

    @PostMapping("/product/{productId}/image-url")
    public ResponseEntity<ProductDTO> updateImageUrl(@PathVariable Long productId, @RequestBody Map<String, String> body) {
        String imageUrl = body.get("imageUrl");
        ProductDTO updated = productService.updateImageUrl(productId, imageUrl);
        return ResponseEntity.ok(updated);
    }

}
