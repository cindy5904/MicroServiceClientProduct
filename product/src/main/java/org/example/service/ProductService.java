package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PostLoad;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Client;
import org.example.entity.Product;
import org.example.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductService {
    @Inject
    ProductRepository productRepository;

    @Transactional
    public Product createProduct(String name, String description, double price){
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        productRepository.persist(product);
        return product;
    }

    public List<Product> getAllProduct() {
        return productRepository.listAll();
    }

    public Optional<Product> getProducttById(Long id) {
        return productRepository.findByIdOptional(id);
    }

    @Transactional
    public boolean deleteById(Long id) {
        productRepository.deleteById(id);
        return false;
    }
    @Transactional
    public Product updateProduct(Long id, String name, String description, double price) {
        Optional<Product> productUpdate = productRepository.findByIdOptional(id);
        if (productUpdate.isPresent()) {
            Product product = productUpdate.get();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            productRepository.persist(product);
            return product;
        } else {
            throw new EntityNotFoundException("Client non trouvé");
        }
    }
}
