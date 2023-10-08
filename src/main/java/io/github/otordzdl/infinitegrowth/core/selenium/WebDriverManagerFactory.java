package io.github.otordzdl.infinitegrowth.core.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.OperatingSystem;
import io.github.otordzdl.infinitegrowth.core.base.BaseWebTest;
import io.github.otordzdl.infinitegrowth.core.utils.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WebDriverManagerFactory {
    private static final Logger logger = LoggerFactory.getLogger(BaseWebTest.class);
    public static WebDriverManager getWebDriverManager(BrowserType browserType) {
        String executionMode = ConfigLoader.getProperty("execution_mode").toUpperCase();

        if (executionMode == "LOCAL") {
            String os = System.getProperty("os.name").toLowerCase();
            logger.info("Realizando configuracion LOCAL para os :"+os);
            Map<String, OperatingSystem> osMap = new HashMap<>();
            osMap.put("win", OperatingSystem.WIN);
            osMap.put("nix", OperatingSystem.LINUX);
            osMap.put("nux", OperatingSystem.LINUX);
            osMap.put("mac", OperatingSystem.MAC);

            OperatingSystem operatingSystem = osMap.get(os.contains("win") ? "win" : os.contains("nix") || os.contains("nux") ? "nix" : os.contains("mac") ? "mac" : null);

            if (operatingSystem == null) {
                logger.error("Sistema operativo no soportado: "+os);
                throw new UnsupportedOperationException("Sistema operativo no compatible: " + os);
            }

            logger.info("Configurando driver "+ browserType.toString() + " para os "+ os);
            switch (browserType) {
                case CHROME:
                    return WebDriverManager.chromedriver().operatingSystem(operatingSystem);
                case FIREFOX:
                    return WebDriverManager.firefoxdriver().operatingSystem(operatingSystem);
                case EDGE:
                    return WebDriverManager.edgedriver().operatingSystem(operatingSystem);
                case OPERA:
                    return WebDriverManager.operadriver().operatingSystem(operatingSystem);
                case SAFARI:
                    return WebDriverManager.safaridriver().operatingSystem(operatingSystem);

                default:
                    logger.error("Driver no soportado para os "+ os);
                    throw new IllegalArgumentException("Navegador no compatible: " + browserType.getName());
            }
        } else {
            switch (browserType) {
                case CHROME:
                    return WebDriverManager.chromedriver();
                case FIREFOX:
                    return WebDriverManager.firefoxdriver();
                case EDGE:
                    return WebDriverManager.edgedriver();
                case OPERA:
                    return WebDriverManager.operadriver();
                case SAFARI:
                    return WebDriverManager.safaridriver();

                default:
                    logger.error("Driver no soportado");
                    throw new IllegalArgumentException("Navegador no compatible: " + browserType.getName());
            }
        }
    }
}