package com.example.pesha.controller;

import com.example.pesha.dao.entity.Product;
import com.example.pesha.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "product")
public class ProductController {

    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable("productId") Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping("/all")
    public List<Product> getAllProduct() {
        return productService.getAllProduct();
    }

    @PostMapping
    public Product creatProduct(@RequestBody Product productRequest) {
        return productService.createProduct(productRequest);
    }

    @PutMapping
    public Product replaceProduct(@RequestParam(value = "replaceProductName") String ReplaceProductName, @RequestBody Product productRequest) {
        return productService.replaceProduct(ReplaceProductName, productRequest);
    }

    @DeleteMapping
    public void deleteProduct(@RequestParam(value = "productId") Long productId) {
        productService.deleteProduct(productId);
    }

}
