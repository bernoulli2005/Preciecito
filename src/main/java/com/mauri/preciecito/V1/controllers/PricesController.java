package com.mauri.preciecito.V1.controllers;

import com.mauri.preciecito.V1.models.Product;
import com.mauri.preciecito.V1.repository.ProductRepository;
import com.mauri.preciecito.V1.services.BasicCanasta;
import com.mauri.preciecito.V1.services.ExtractDataService;
import jakarta.persistence.Basic;
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
    private final ProductRepository productRepository;
    private final BasicCanasta basicCanasta;

    public PricesController(WebDriver driver, ExtractDataService extractDataService, ProductRepository productRepository, BasicCanasta basicCanasta) {
        this.driver = driver;
        this.extractDataService = extractDataService;
        this.productRepository = productRepository;
        this.basicCanasta = basicCanasta;
    }

    /////////////
    @GetMapping("/u/tang")
    public ResponseEntity<Map<String, Float>> extractTang() {
        return basicCanasta.extractTang();
    }
    @GetMapping("/u/cuadrado")
    public ResponseEntity<Map<String, Float>> extractCuadrado() {
        return basicCanasta.extractCuadrado();
    }
    @GetMapping("/u/cocucha")
    public ResponseEntity<Map<String, Float>> extractCocucha() {
        return basicCanasta.extractCocucha();
    }
    @GetMapping("/u/zanahoria")
    public ResponseEntity<Map<String, Float>> extractZanahoria() {
        return basicCanasta.extractZanahoria();
    }

    @GetMapping("/u/tallarin")
    public ResponseEntity<Map<String, Float>> extractTallarin() {
        return basicCanasta.extractTallarin();
    }

    @GetMapping("/u/arroz")
    public ResponseEntity<Map<String, Float>> extractArroz() {
        return basicCanasta.extractArroz();
    }

    @GetMapping("/u/cremoso")
    public ResponseEntity<Map<String, Float>> extractCremoso() {
        return basicCanasta.extractCremoso();
    }

    @GetMapping("/u/puredetomate")
    public ResponseEntity<Map<String, Float>> extractPureDeTomate() {
        return basicCanasta.extractPureDeTomate();
    }

    /////////////
    @GetMapping("/{name}")
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

