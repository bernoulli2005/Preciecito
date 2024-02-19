package com.mauri.preciecito.V1.services;

import com.mauri.preciecito.V1.models.Product;
import com.mauri.preciecito.V1.repository.ProductRepository;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowDataService {
    private static Logger log = LoggerFactory.getLogger(ShowDataService.class);
    private static ProductRepository productRepository = null;

    @Autowired
    public ShowDataService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static Product findLatestProductByName(String name) {
        log.info("Finding latest product by name: {}", name);
        return productRepository.findTopByNameOrderByDateDesc(name);
    }
}
