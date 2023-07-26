package com.example.pesha.controller;

import com.example.pesha.dao.entity.Product;
import com.example.pesha.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
    public String getAllProduct(Model model) {
        List<Product> productList = productService.getAllProduct();
        model.addAttribute("products", productList);
        return "all_products"; // Thymeleaf模板的名稱，這裡假設模板名稱為"all_products.html"
    }

    @ResponseBody
    @PostMapping("/add")
    public Product creatProduct(@RequestBody Product productRequest) {
        return productService.createProduct(productRequest);
    }

    @ResponseBody
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/replace/{id}")
    public Product replaceProduct(@PathVariable("id") Long productId, @RequestBody Product productRequest) {
        return productService.replaceProduct(productId, productRequest);
    }
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long productId) {

        productService.deleteProduct(productId);
        return "redirect:/product/all";
    }

}
