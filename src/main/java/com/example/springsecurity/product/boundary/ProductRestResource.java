package com.example.springsecurity.product.boundary;

import com.example.springsecurity.product.model.Product;
import com.example.springsecurity.product.service.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestResource {

    private final ProductRepository productRepository;

    public ProductRestResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER", "ROLE_CUSTOMER"})
    public List<Product> list() {
        return productRepository.findAll();
    }

    @PostMapping

    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<Product> create(@RequestBody @Valid Product product) {
        Product savedProduct = productRepository.save(product);
        URI productURI = URI.create("/products/" + savedProduct.getId());
        return ResponseEntity.created(productURI).body(savedProduct);
    }

}
