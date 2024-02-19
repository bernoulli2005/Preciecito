package com.mauri.preciecito.V1.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class AppConfig {

    @Bean
    public WebDriver webDriver() throws IOException {
        // Obtener la ruta del directorio de trabajo actual
        String currentWorkingDirectory = System.getProperty("user.dir");

        // Construir la ruta completa al chromedriver.exe
        String chromeDriverPath = currentWorkingDirectory + "/src/main/resources/chromedriver.exe";

        // Configurar la propiedad del sistema con la nueva ruta
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        // Configurar ChromeOptions para el modo sin cabeza (headless mode)
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");

        return new ChromeDriver(chromeOptions);
    }
}

