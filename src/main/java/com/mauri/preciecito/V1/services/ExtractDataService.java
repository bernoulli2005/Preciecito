package com.mauri.preciecito.V1.services;

import com.mauri.preciecito.V1.models.Product;
import com.mauri.preciecito.V1.repository.ProductRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class ExtractDataService {
    private static final Logger logger = Logger.getLogger(ExtractDataService.class.getName());
    @Autowired
    private WebDriver driver;
    private ProductRepository productRepository;

    public ExtractDataService(WebDriver driver, ProductRepository productRepository) {
        this.driver = driver;
        this.productRepository = productRepository;
    }


    public ResponseEntity<String> extractPricesGeneric(String url, String priceSelector) {
        Product product = new Product();
        String firstPrice = null;

        do {
            driver.get(url);

            // Find prices using the provided XPath or CSS selector
            List<WebElement> priceElements = driver.findElements(By.xpath(priceSelector));

            logger.info("Number of price elements found: " + priceElements.size());

            if (priceElements != null && !priceElements.isEmpty()) {
                // Assuming you want to check the length of EACH element
                boolean allElementsValid = true;

                for (WebElement element : priceElements) {
                    // Check the length of each element
                    if (element.getText().length() < 3) {
                        allElementsValid = false;
                        break;  // Exit the loop if any element does not meet the condition
                    }
                }

                if (allElementsValid) {
                    // Get the text of the first price element
                    firstPrice = priceElements.get(0).getText();

                }

            }

        } while (firstPrice == null);

        return ResponseEntity.ok(firstPrice);
    }

    public ResponseEntity<String> extractPricesDisco(String url, String priceSelector) {
        String firstPrice = null;
        Product product = new Product();

        String defaultClassSelector = ".discoargentina-store-theme-14k7D0cUQ_45k_MeZ_yfFo";

        do {
            driver.get(url);

            // Find prices using the provided XPath or CSS selector
            List<WebElement> priceElements = driver.findElements(By.xpath(priceSelector));

            // Find elements with the default class
            List<WebElement> defaultClassElements = driver.findElements(By.cssSelector(defaultClassSelector));

            logger.info("Number of price elements found: " + priceElements.size());
            logger.info("Number of default class elements found: " + defaultClassElements.size());

            if (priceElements != null && !priceElements.isEmpty()) {
                boolean allElementsValid = true;

                for (WebElement element : priceElements) {
                    // Check the length of each element
                    if (element.getText().length() < 3) {
                        allElementsValid = false;
                        break;
                    }
                }

                if (allElementsValid) {
                    firstPrice = priceElements.get(0).getText();
                }
            }

            // Process default class elements if any are found
            if (!defaultClassElements.isEmpty()) {
                // Add your logic to process default class elements here
                // You can retrieve information from these elements as needed
            }

        } while (firstPrice == null);

        return ResponseEntity.ok(firstPrice);
    }


    public ResponseEntity<String> extractPricesCarrefour(String url, String priceSelector) {
        StringBuilder concatenatedPrices = new StringBuilder();
        Product product = new Product();
        int attempts = 0;

        do {
            driver.get(url);

            // Find prices using the provided XPath or CSS selector
            List<WebElement> priceElements = driver.findElements(By.xpath(priceSelector));
            logger.info("Number of price elements found: " + priceElements.size());

            for (WebElement priceElement : priceElements) {
                String price = priceElement.getText();
                logger.info(price);
                if (price != null) {
                    logger.info(price);
                    concatenatedPrices.append(price).append("");
                }
            }

            attempts++;
        } while (concatenatedPrices.length() == 0 && attempts < 7); // Try twice

        return ResponseEntity.ok(concatenatedPrices.toString().trim());
    }


    public ResponseEntity<String> extractCordiez(String url) {
        //final String url = "https://www.cordiez.com.ar/jugo-en-polvo-mix-naranja-limon-tang-15-gr/p";
        final String css = "//div[@class='shop-detail-right']//p[@class='offer-price mb-1']";
        return extractPricesGeneric(url, css);
    }
    public ResponseEntity<String> extractCarrefour(String url) {
        //final String url = "https://www.carrefour.com.ar/jugo-en-polvo-tang-limonada-dulce-15-g/p";
        final String css = "//span[@class='valtech-carrefourar-product-price-0-x-currencyContainer']//span[@class='valtech-carrefourar-product-price-0-x-currencyInteger']";

        return extractPricesCarrefour(url, css);
    }
    public ResponseEntity<String> extractDisco(String url) {
        final String css = "//div[@class='vtex-flex-layout-0-x-flexColChild pb0']//div[@class='discoargentina-store-theme-1oaMy8g_TkKDcWOQsx5V2i']//div[@class='discoargentina-store-theme-1dCOMij_MzTzZOCohX1K7w'][1]";
        return extractPricesDisco(url, css);
    }
    public ResponseEntity<String> extractVea(String url) {
        //final String url = "https://www.vea.com.ar/jugo-en-polvo-tang-naranja-dulce-15-gr/p";
        final String css = "//div[@class='veaargentina-store-theme-1oaMy8g_TkKDcWOQsx5V2i']//div[@class='veaargentina-store-theme-1dCOMij_MzTzZOCohX1K7w'][1]";
        return extractPricesGeneric(url, css);
    }
    private static float convertToFloat(String inputString) {
        // Remove currency symbol and replace dot with empty space
        String cleanString = inputString.replaceAll("[^0-9,]", "").replace('.', ' ');

        // Check if a comma exists in the string before using lastIndexOf
        int commaIndex = cleanString.lastIndexOf(',');

        if (commaIndex != -1) {
            // Check if the comma is in the decimal part
            if (commaIndex < cleanString.length() - 3) {
                // Replace the last comma with a dot to handle the European number format
                cleanString = cleanString.substring(0, commaIndex) +
                        '.' +
                        cleanString.substring(commaIndex + 1);
            }
        }

        // Parse the cleaned string as a float using the appropriate locale
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        try {
            return numberFormat.parse(cleanString).floatValue();
        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception according to your needs
            return 0.0f; // Default value or indicate an error
        }
    }
    private Product createAndSaveProduct(String productName, String discoPrice, String veaPrice, String carrefourPrice, String cordiezPrice) {
        Product product = new Product();
        product.setName(productName);
        product.setDate(new Date());

        float disco = convertToFloat(discoPrice);
        float vea = convertToFloat(veaPrice);
        float carrefour = convertToFloat(carrefourPrice);
        float cordiez = convertToFloat(cordiezPrice);

        product.setPriceDisco(disco);
        product.setPriceCarrefour(carrefour);
        product.setPriceVea(vea);
        product.setPriceCordiez(cordiez);

        // Save the product information to the repository
        productRepository.save(product);

        return product;
    }
    public ResponseEntity<Map<String, Float>> extractTang() {

        String cordiezPrice = extractCordiez("https://www.cordiez.com.ar/jugo-en-polvo-mix-naranja-limon-tang-15-gr/p").getBody();
        String discoPrice = extractDisco("https://www.disco.com.ar/jugo-en-polvo-tang-naranja-dulce-15-gr/p").getBody();
        String veaPrice = extractVea("https://www.vea.com.ar/jugo-en-polvo-tang-naranja-dulce-15-gr/p").getBody();
        String carrefourPrice = extractCarrefour("https://www.carrefour.com.ar/jugo-en-polvo-tang-limonada-dulce-15-g/p").getBody();

        Product product = createAndSaveProduct("tang", discoPrice, veaPrice, carrefourPrice, cordiezPrice);

        Map<String, Float> result = new HashMap<>();
        result.put("Carrefour", product.getPriceCarrefour());
        result.put("Disco", product.getPriceDisco());
        result.put("Vea", product.getPriceVea());
        result.put("Cordiez", product.getPriceCordiez());

        //////aca hay que guardar y no en la funcion de extract
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Map<String, Float>> extractCuadrado() {
        String cordiezPrice = extractCordiez("https://www.cordiez.com.ar/jamon-cuadrado-especial-x-kg/p").getBody();
        String discoPrice = extractDisco("https://www.disco.com.ar/milanesa-cuadrada/p").getBody();
        String veaPrice = extractVea("https://www.vea.com.ar/milanesa-cuadrada/p").getBody();
        String carrefourPrice = extractCarrefour("https://www.carrefour.com.ar/cuadrada-el-mercado-x-kg/p").getBody();


        Product product = createAndSaveProduct("cuadrado", discoPrice, veaPrice, carrefourPrice, cordiezPrice);

        Map<String, Float> result = new HashMap<>();
        result.put("Carrefour", product.getPriceCarrefour());
        result.put("Disco", product.getPriceDisco());
        result.put("Vea", product.getPriceVea());
        result.put("Cordiez", product.getPriceCordiez());

        //////aca hay que guardar y no en la funcion de extract
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<Map<String, Float>> extractCocucha() {
        String cordiezPrice = extractCordiez("https://www.cordiez.com.ar/coca-cola-2-5-lt/p").getBody();
        String discoPrice = extractDisco("https://www.disco.com.ar/gaseosa-coca-cola-sabor-original-2-25-l/p").getBody();
        String veaPrice = extractVea("https://www.vea.com.ar/gaseosa-coca-cola-sabor-original-2-25-l/p").getBody();
        String carrefourPrice = extractCarrefour("https://www.carrefour.com.ar/gaseosa-coca-cola-sabor-original-225-l/p").getBody();


        Product product = createAndSaveProduct("cocucha", discoPrice, veaPrice, carrefourPrice, cordiezPrice);

        Map<String, Float> result = new HashMap<>();
        result.put("Carrefour", product.getPriceCarrefour());
        result.put("Disco", product.getPriceDisco());
        result.put("Vea", product.getPriceVea());
        result.put("Cordiez", product.getPriceCordiez());

        return ResponseEntity.ok(result);
    }



}

