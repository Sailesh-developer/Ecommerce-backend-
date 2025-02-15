package com.ecommerce.project.controller;


import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/admin/categories/{categoryId}/product"  , method = RequestMethod.POST)
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO , @PathVariable Long categoryId){
          ProductDTO savedProductDTO = productService.addProduct(categoryId , productDTO);
          return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
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
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO , @PathVariable Long productId){
        ProductDTO updatedProductDTO = productService.updateProduct(productId , productDTO);
        return new ResponseEntity<>(updatedProductDTO , HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/products/{productId}" , method = RequestMethod.DELETE)
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProduct , HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{productId}/image" , method = RequestMethod.PUT)
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId , @RequestParam("image")MultipartFile image) throws IOException {
        ProductDTO updatedProduct = productService.updateProductImage(productId , image);
        return new ResponseEntity<>(updatedProduct , HttpStatus.OK);
    }

}
