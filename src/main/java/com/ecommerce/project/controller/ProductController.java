package com.ecommerce.project.controller;

import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/admin/categories/{categoryId}/product"  , method = RequestMethod.POST)
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product , @PathVariable Long categoryId){
          ProductDTO productDTO = productService.addProduct(categoryId , product);
          return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/public/products" , method = RequestMethod.GET)
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/public/categories/{categoryId}/products" , method = RequestMethod.GET)
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){
        ProductResponse productResponse =  productService.searchByCategory(categoryId);
        return new ResponseEntity<>(productResponse , HttpStatus.OK);
    }

    @RequestMapping(value = "/public/products/keyword/{keyword}" , method = RequestMethod.GET)
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword){
       ProductResponse productResponse =  productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse , HttpStatus.FOUND);
    }

    @RequestMapping(value = "/admin/products/{productId}" , method = RequestMethod.PUT)
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product , @PathVariable Long productId){
        ProductDTO updatedProductDTO = productService.updateProduct(productId , product);
        return new ResponseEntity<>(updatedProductDTO , HttpStatus.OK);
    }

}
