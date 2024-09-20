package com.entidad.entidad.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entidad.entidad.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.quantity > 0")
    List<Product> findActiveProducts();

    @Query("SELECT p FROM Product p ORDER BY p.quantity DESC")
    List<Product> findTop5BestSellingProducts(Pageable pageable);

    List<Product> findByNameContaining(String name);

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    @Query("SELECT p FROM Product p WHERE p.name LIKE :keyword% ")
    List<Product> searchByKeyword(String keyword);
}