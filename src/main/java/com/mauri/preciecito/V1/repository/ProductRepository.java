package com.mauri.preciecito.V1.repository;

import com.mauri.preciecito.V1.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findTopByNameOrderByDateDesc(String name);
}
