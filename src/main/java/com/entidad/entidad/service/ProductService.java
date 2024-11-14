package com.entidad.entidad.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.entidad.entidad.model.Product;
import com.entidad.entidad.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }

        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El producto con el ID especificado no existe"));
    }

    public Product updateProduct(Long id, Product productActualizado) {
        return productRepository.findById(id)
                .map(existingProduct -> {
             
                    if (productActualizado.getName() != null) {
                        existingProduct.setName(productActualizado.getName());
                    }

                    if (productActualizado.getQuantity() != null) {
                        existingProduct.setQuantity(productActualizado.getQuantity());
                    }
                    if (productActualizado.getPrice() != null) {
                        existingProduct.setPrice(productActualizado.getPrice());
                    }

                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("El producto con el ID especificado no existe");
        }
        productRepository.deleteById(id);
    }

    public List<Product> getActiveProducts() {
        return productRepository.findActiveProducts();
    }

    public List<Product> getTop5BestSellingProducts() {
        Pageable pageable = PageRequest.of(0, 5);
        return productRepository.findTop5BestSellingProducts(pageable);
    }

    public List<Product> findByName(String name) {
        return productRepository.findByNameContaining(name);
    }

    public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> searchByKeyword(String keyword) {
        return productRepository.searchByKeyword(keyword);
    }

}
