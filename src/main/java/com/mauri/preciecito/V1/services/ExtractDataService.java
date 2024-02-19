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


    public ResponseEntity<String> ScrapElementGeneric(String url, String priceSelector) {
        Product product = new Product();
        String firstPrice = null;

        do {
            driver.get(url);

            List<WebElement> priceElements = driver.findElements(By.xpath(priceSelector));


            if (priceElements != null && !priceElements.isEmpty()) {
                boolean allElementsValid = true;

                for (WebElement element : priceElements) {
                    if (element.getText().length() < 3) {
                        allElementsValid = false;
                        break;
                    }
                }

                if (allElementsValid) {
                    firstPrice = priceElements.get(0).getText();

                }

            }

        } while (firstPrice == null);

        return ResponseEntity.ok(firstPrice);
    }

    public ResponseEntity<String> ScrapElementDisco(String url, String priceSelector) {
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


    public ResponseEntity<String> ScrapElementsCarrefour(String url, String priceSelector) {
        StringBuilder concatenatedPrices = new StringBuilder();
        Product product = new Product();
        int attempts = 0;

        do {
            driver.get(url);

            List<WebElement> priceElements = driver.findElements(By.xpath(priceSelector));

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
        final String css = "//div[@class='shop-detail-right']//p[@class='offer-price mb-1']";
        return ScrapElementGeneric(url, css);
    }
    public ResponseEntity<String> extractCarrefour(String url) {
        final String css = "//span[@class='valtech-carrefourar-product-price-0-x-currencyContainer']//span[@class='valtech-carrefourar-product-price-0-x-currencyInteger']";

        return ScrapElementsCarrefour(url, css);
    }
    public ResponseEntity<String> extractDisco(String url) {
        final String css = "//div[@class='vtex-flex-layout-0-x-flexColChild pb0']//div[@class='discoargentina-store-theme-1oaMy8g_TkKDcWOQsx5V2i']//div[@class='discoargentina-store-theme-1dCOMij_MzTzZOCohX1K7w'][1]";
        return ScrapElementDisco(url, css);
    }
    public ResponseEntity<String> extractVea(String url) {
        final String css = "//div[@class='veaargentina-store-theme-1oaMy8g_TkKDcWOQsx5V2i']//div[@class='veaargentina-store-theme-1dCOMij_MzTzZOCohX1K7w'][1]";
        return ScrapElementGeneric(url, css);
    }
    private static float PriceToFloat(String inputString) {
        String cleanString = inputString.replaceAll("[^0-9,]", "").replace('.', ' ');

        int commaIndex = cleanString.lastIndexOf(',');

        if (commaIndex != -1) {
            if (commaIndex < cleanString.length() - 3) {
                cleanString = cleanString.substring(0, commaIndex) +
                        '.' +
                        cleanString.substring(commaIndex + 1);
            }
        }

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        try {
            return numberFormat.parse(cleanString).floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }
    private Product SavingProduct(String productName, String discoPrice, String veaPrice, String carrefourPrice, String cordiezPrice) {
        Product product = new Product();
        product.setName(productName);
        product.setDate(new Date());

        float disco = PriceToFloat(discoPrice);
        float vea = PriceToFloat(veaPrice);
        float carrefour = PriceToFloat(carrefourPrice);
        float cordiez = PriceToFloat(cordiezPrice);

        product.setPriceDisco(disco);
        product.setPriceCarrefour(carrefour);
        product.setPriceVea(vea);
        product.setPriceCordiez(cordiez);

        productRepository.save(product);

        return product;
    }

    public ResponseEntity<Map<String, Float>> ScrapExtractSave(
            String cordiezUrl, String discoUrl, String veaUrl, String carrefourUrl, String productName
    ) {
        String cordiezPrice = extractCordiez(cordiezUrl).getBody();
        String discoPrice = extractDisco(discoUrl).getBody();
        String veaPrice = extractVea(veaUrl).getBody();
        String carrefourPrice = extractCarrefour(carrefourUrl).getBody();

        Product product = SavingProduct(productName, discoPrice, veaPrice, carrefourPrice, cordiezPrice);

        Map<String, Float> result = new HashMap<>();
        result.put("Carrefour", product.getPriceCarrefour());
        result.put("Disco", product.getPriceDisco());
        result.put("Vea", product.getPriceVea());
        result.put("Cordiez", product.getPriceCordiez());

        return ResponseEntity.ok(result);
    }








}

