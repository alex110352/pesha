package com.example.pesha.controller;

import com.example.pesha.dao.entity.Product;
import com.example.pesha.exception.DuplicateException;
import com.example.pesha.exception.NotFoundException;
import com.example.pesha.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        try {
            List<Product> productList = productService.getAllProduct();
            model.addAttribute("products", productList);
            return "all_products";
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "product not found");
            return "/";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return "/";
        }

    }

    @PostMapping("/add")
    @ResponseBody
    public Product creatProduct(@RequestBody Product productRequest, Model model) {
        try {
            Product product = productService.createProduct(productRequest);
            model.addAttribute("product",product);
            model.addAttribute("productRequest",productRequest);
            return product;
        } catch (DuplicateException e) {
            model.addAttribute("errorMessage", "product is duplicate");
            return null;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return null;
        }
    }

    @PutMapping("/replace/{id}")
    @ResponseBody
    public Product replaceProduct(@PathVariable("id") Long productId, @RequestBody Product productRequest,Model model) {
        try {
            Product product = productService.replaceProduct(productId, productRequest);
            model.addAttribute("productId",productId);
            model.addAttribute("product",product);
            model.addAttribute("productRequest",productRequest);
            return product;
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "product not found");
            return null;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public Product deleteProduct(@PathVariable("id") Long productId,Model model) {
        try {
            Product product = productService.deleteProduct(productId);
            model.addAttribute("product",product);
            model.addAttribute("productId",productId);
            return product;
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", "product not found");
            return null;
        } catch (Exception e) {
            model.addAttribute("errorMessage", e);
            return null;
        }
    }

}
