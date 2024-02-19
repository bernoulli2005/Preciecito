package com.mauri.preciecito.V1.services;

import jakarta.persistence.Basic;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BasicCanasta {

    private final ExtractDataService extractDataService;

    BasicCanasta(ExtractDataService extractDataService){
        this.extractDataService = extractDataService;
    }

    public ResponseEntity<Map<String, Float>> extractTang() {
        return extractDataService.ScrapExtractSave(
                "https://www.cordiez.com.ar/jugo-en-polvo-mix-naranja-limon-tang-15-gr/p" ,
                "https://www.disco.com.ar/jugo-en-polvo-tang-naranja-dulce-15-gr/p",
                "https://www.vea.com.ar/jugo-en-polvo-tang-naranja-dulce-15-gr/p",
                "https://www.carrefour.com.ar/jugo-en-polvo-tang-limonada-dulce-15-g/p",
                "tang");
    }

    public ResponseEntity<Map<String, Float>> extractCuadrado() {
        return extractDataService.ScrapExtractSave(
                "https://www.cordiez.com.ar/jamon-cuadrado-especial-x-kg/p" ,
                "https://www.disco.com.ar/milanesa-cuadrada/p",
                "https://www.vea.com.ar/milanesa-cuadrada/p",
                "https://www.carrefour.com.ar/cuadrada-el-mercado-x-kg/p",
                "cuadrado");
    }

    public ResponseEntity<Map<String, Float>> extractCocucha() {
        return extractDataService.ScrapExtractSave(
                "https://www.cordiez.com.ar/coca-cola-2-5-lt/p" ,
                "https://www.disco.com.ar/gaseosa-coca-cola-sabor-original-2-25-l/p",
                "https://www.vea.com.ar/gaseosa-coca-cola-sabor-original-2-25-l/p",
                "https://www.carrefour.com.ar/gaseosa-coca-cola-sabor-original-225-l/p",
                "cocucha");
    }




}
