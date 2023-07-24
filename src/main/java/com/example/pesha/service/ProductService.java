package com.example.pesha.service;

import com.example.pesha.dao.entity.Product;
import com.example.pesha.dao.repositories.ProductRepository;
import com.example.pesha.exception.DuplicateException;
import com.example.pesha.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElseThrow(() -> new NotFoundException("can't find " + productId));
    }

    public List<Product> getAllProduct() {
        List<Product> listProduct = productRepository.findAll();
        if (listProduct.isEmpty()) {
            throw new NotFoundException("product list is empty");
        }
        return listProduct;
    }

    public Product createProduct(Product productRequest) {

        String productName = productRequest.getProductName();
        int productPrice = productRequest.getProductPrice();

        if (productRepository.findByProductName(productName).isPresent()) {
            throw new DuplicateException(productName + " is duplicate");
        }

        Product product = new Product();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        return productRepository.save(product);
    }

    public Product replaceProduct(String replaceProductName, Product productRequest) {

        Optional<Product> OptionalReplaceProduct = productRepository.findByProductName(replaceProductName);
        OptionalReplaceProduct.orElseThrow(() -> new NotFoundException("can't find " + replaceProductName));

        Product replaceProduct = OptionalReplaceProduct.get();
        String productName = productRequest.getProductName();
        int productPrice = productRequest.getProductPrice();

        if (replaceProduct.getProductPrice() == productPrice && Objects.equals(replaceProductName, productName)) {
            throw new DuplicateException("replace information is duplicate");
        }

        replaceProduct.setProductName(productName);
        replaceProduct.setProductPrice(productPrice);
        return productRepository.save(replaceProduct);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("can't find " + productId));
        productRepository.delete(product);
    }

}
