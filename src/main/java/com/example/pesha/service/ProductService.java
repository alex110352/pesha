package com.example.pesha.service;

import com.example.pesha.dao.entity.Cart;
import com.example.pesha.dao.entity.OrderItem;
import com.example.pesha.dao.entity.Product;
import com.example.pesha.dao.repositories.CartRepository;
import com.example.pesha.dao.repositories.OrderItemRepository;
import com.example.pesha.dao.repositories.ProductRepository;
import com.example.pesha.exception.DuplicateException;
import com.example.pesha.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final OrderItemRepository orderItemRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository, OrderItemRepository orderItemRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Product getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElseThrow(() -> new NotFoundException("can't find " + productId));
    }

    public List<Product> getAllProduct() {
        return productRepository.findAll();
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

    public Product replaceProduct(Long productId, Product productRequest) {

        Product replaceProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("can't find product"));

        String productName = productRequest.getProductName();
        int productPrice = productRequest.getProductPrice();

        if (replaceProduct.getProductPrice() == productPrice && Objects.equals(replaceProduct.getProductName(), productName)) {
            throw new DuplicateException("replace information is duplicate");
        }

        replaceProduct.setProductName(productName);
        replaceProduct.setProductPrice(productPrice);
        return productRepository.save(replaceProduct);
    }

    public Product deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("can't find " + productId));

        List<Cart> carts = cartRepository.findByProductsContaining(product);
        List<OrderItem> orderItems = orderItemRepository.findByProduct(product);


        for (Cart cart : carts) {
            cart.getProductQuantity().remove(product);
        }

        for (Cart cart : carts) {
            cart.getProducts().remove(product);
            cartRepository.save(cart);
        }

        orderItemRepository.deleteAll(orderItems);
        productRepository.delete(product);

        return product;

    }

}
