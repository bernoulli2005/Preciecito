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

    public ResponseEntity<Map<String, Float>> extractZanahoria() {
        return extractDataService.ScrapExtractSave(
                "https://www.cordiez.com.ar/zanahoria-kg/p" ,
                "https://www.disco.com.ar/zanahoria-por-kg/p",
                "https://www.vea.com.ar/zanahoria-por-kg/p",
                "https://www.carrefour.com.ar/zanahoria-precios-cuidados-x-kg/p",
                "zanahoria");
    }

    public ResponseEntity<Map<String, Float>> extractTallarin() {
        return extractDataService.ScrapExtractSave(
                "https://www.cordiez.com.ar/fideos-tallarin-la-providencia-500-gr/p" ,
                "https://www.disco.com.ar/fideos-tallarin-favorita-x500-gr/p",
                "https://www.vea.com.ar/fideos-spaguetti-c-c-500gr/p",
                "https://www.carrefour.com.ar/fideos-tallarines-n7-terrabusi-500-g-729392/p",
                "tallarin");
    }

    public ResponseEntity<Map<String, Float>> extractArroz() {
        return extractDataService.ScrapExtractSave(
                "https://www.cordiez.com.ar/arroz-molto-largo-fino-500-gr/p" ,
                "https://www.disco.com.ar/arroz-largo-fino-molinos-ala-500-gr/p",
                "https://www.vea.com.ar/arroz-largo-fino-molinos-ala-500-gr/p",
                "https://www.carrefour.com.ar/arroz-tipo-largo-fino-00000-vanguardia-500-g/p",
                "arroz");
    }

    public ResponseEntity<Map<String, Float>> extractPureDeTomate() {
        return extractDataService.ScrapExtractSave(
                "https://www.cordiez.com.ar/pure-de-tomate-tetra-brik-arcor-530-gr/p" ,
                "https://www.disco.com.ar/pure-de-tomate-cuisine-co-520gr/p",
                "https://www.vea.com.ar/pure-de-tomate-la-campagnola-520-gr/p",
                "https://www.carrefour.com.ar/pure-de-tomates-alco-520-g/p",
                "puredetomate");
    }

    public ResponseEntity<Map<String, Float>> extractCremoso() {
        return extractDataService.ScrapExtractSave(
                "https://www.cordiez.com.ar/queso-cremoso-masterlac-x-kg/p" ,
                "https://www.disco.com.ar/queso-cremoso-punta-del-agua/p",
                "https://www.vea.com.ar/queso-cremoso-la-paulina-1-4-hma-minimo-1-kg-mayorista/p",
                "https://www.carrefour.com.ar/queso-cuartirolo-tremblay-x-kg/p",
                "cremoso");
    }

    public ResponseEntity<Map<String, Float>> extractPaty() {
        return extractDataService.ScrapExtractSave(
                "https://www.cordiez.com.ar/hamburguesas-paty-expres/p " ,
                "https://www.disco.com.ar/queso-cremoso-punta-del-agua/p",
                "https://www.vea.com.ar/queso-cremoso-la-paulina-1-4-hma-minimo-1-kg-mayorista/p",
                "https://www.carrefour.com.ar/queso-cuartirolo-tremblay-x-kg/p",
                "cremoso");
    }




}
