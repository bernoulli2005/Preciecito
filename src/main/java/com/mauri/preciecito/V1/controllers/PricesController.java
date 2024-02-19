package com.mauri.preciecito.V1.controllers;

import com.mauri.preciecito.V1.models.Product;
import com.mauri.preciecito.V1.services.ExtractDataService;
import com.mauri.preciecito.V1.services.ShowDataService;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class PricesController {
    private final String predefinedUrl = "https://www.cordiez.com.ar/jugo-en-polvo-mix-naranja-limon-tang-15-gr/p";
    @Autowired
    private WebDriver driver;
    @Autowired
    private ExtractDataService extractDataService;
    private final ShowDataService showDataService;

    public PricesController(WebDriver driver, ExtractDataService extractDataService, ShowDataService showDataService) {
        this.driver = driver;
        this.extractDataService = extractDataService;
        this.showDataService = showDataService;
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

    @GetMapping("/latest/{name}")
    public Product getLatestProduct(@PathVariable String name) {
        return ShowDataService.findLatestProductByName(name);
    }


}

