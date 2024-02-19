package com.mauri.preciecito.V1.controllers;

import com.mauri.preciecito.V1.models.Product;
import com.mauri.preciecito.V1.repository.ProductRepository;
import com.mauri.preciecito.V1.services.ExtractDataService;
import com.mauri.preciecito.V1.services.ShowDataService;
import org.hibernate.query.Page;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class PricesController {
    private final String predefinedUrl = "https://www.cordiez.com.ar/jugo-en-polvo-mix-naranja-limon-tang-15-gr/p";
    @Autowired
    private WebDriver driver;
    @Autowired
    private ExtractDataService extractDataService;
    private final ShowDataService showDataService;
    private final ProductRepository productRepository;

    public PricesController(WebDriver driver, ExtractDataService extractDataService, ShowDataService showDataService, ProductRepository productRepository) {
        this.driver = driver;
        this.extractDataService = extractDataService;
        this.showDataService = showDataService;
        this.productRepository = productRepository;
    }

    @GetMapping("/tang")
    public ResponseEntity<Map<String, Float>> extractTang() {
        return extractDataService.extractTang();
    }


    @GetMapping("/cuadrado")
    public ResponseEntity<Map<String, Float>> extractCuadrado() {
        return extractDataService.extractCuadrado();
    }

    @GetMapping("/cocucha")
    public ResponseEntity<Map<String, Float>> extractCocucha() {
        return extractDataService.extractCocucha();
    }

    @GetMapping("/random/{name}")
    public ResponseEntity<Product> getRandomProductByName(@PathVariable String name) {
        List<Product> products = productRepository.findByNameOrderByDateDesc(name);

        if (products.isEmpty()) {
            // Return 404 Not Found if no matching products are found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Return the latest product based on date
        return new ResponseEntity<>(products.get(0), HttpStatus.OK);
    }

}

