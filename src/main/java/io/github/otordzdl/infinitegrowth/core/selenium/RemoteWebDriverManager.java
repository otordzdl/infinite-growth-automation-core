package io.github.otordzdl.infinitegrowth.core.selenium;

import io.github.otordzdl.infinitegrowth.core.base.BaseWebTest;
import io.github.otordzdl.infinitegrowth.core.utils.ConfigLoader;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RemoteWebDriverManager {
    private static final Logger logger = LoggerFactory.getLogger(BaseWebTest.class);
    public static RemoteWebDriver getRemoteWebDriver(BrowserType browserType) throws MalformedURLException {

        DesiredCapabilities caps = new DesiredCapabilities();
        Properties properties = new Properties();
        String capabilitiesFile = "src/test/resources/properties/capabilities/%s.properties";
        String deviceFarm = ConfigLoader.getProperty("device_farm");
        DeviceFarmType deviceFarmType = DeviceFarmType.valueOf(deviceFarm.toUpperCase());


        String deviceFarmUser = System.getenv("DEVICE_FARM_USER");
        String deviceFarmApiKey =System.getenv("DEVICE_FARM_API_KEY");
        String browserstackHub = ConfigLoader.getProperty("browserstack_hub");
        String saucelabsHub = ConfigLoader.getProperty("saucelabs_hub");
        String lamdatestHub = ConfigLoader.getProperty("lamdatest_hub");
        String deviceFarmUrl;
        RemoteWebDriver remoteWebDriver;
        AbstractDriverOptions options;
        Map<String, Object> devicefarmProviderOptions = new HashMap<>();

        switch (browserType) {
            case CHROME:
                capabilitiesFile = String.format(capabilitiesFile, "chrome");
                options = new ChromeOptions();
                break;
            case FIREFOX:
                capabilitiesFile = String.format(capabilitiesFile, "firefox");
                options = new FirefoxOptions();
                break;
            case EDGE:
                capabilitiesFile = String.format(capabilitiesFile, "edge");
                options = new EdgeOptions();
                break;

            case SAFARI:
                capabilitiesFile = String.format(capabilitiesFile, "safari");
                options = new SafariOptions();
                break;

            default:
                logger.error("No hay navegador compatible con core");
                throw new IllegalArgumentException("Navegador no compatible: " + browserType.getName());
        }

        try (FileInputStream fis = new FileInputStream(capabilitiesFile)) {
            properties.load(fis);
            options.setPlatformName(properties.getProperty("platfotmName"));
            options.setBrowserVersion(properties.getProperty("browserVersion"));



            caps.setCapability("browser_version", properties.getProperty("browserVersion"));
            caps.setCapability("os", properties.getProperty("os"));
            caps.setCapability("os_version", properties.getProperty("os_version"));
            caps.setCapability("name", properties.getProperty("name"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (deviceFarmType) {
            case LAMDATEST:
                deviceFarmUrl = "https://" + deviceFarmUser + ":" + deviceFarmApiKey + lamdatestHub;
                break;
            case BROWSERSTACK:
                deviceFarmUrl = "https://" + deviceFarmUser + ":" + deviceFarmApiKey + browserstackHub;
                break;
            case SAUCELABS:
                deviceFarmUrl =  saucelabsHub;

                devicefarmProviderOptions.put("username", deviceFarmUser);
                devicefarmProviderOptions.put("accessKey", deviceFarmApiKey);
                devicefarmProviderOptions.put("build", "Prueba Infinity Growth");
                devicefarmProviderOptions.put("name", properties.getProperty("name"));
                options.setCapability("sauce:options",devicefarmProviderOptions);
                break;
            default:
                throw new IllegalArgumentException("Device farm no compatible: " + deviceFarmType.getName());
        }


        try {
            logger.info("Configurando "+ browserType.toString() + " en device farm "+deviceFarm);
            remoteWebDriver = new RemoteWebDriver(new URL(deviceFarmUrl), options);
        } catch (MalformedURLException a) {
            logger.error("Configurando "+ browserType.toString() + " en device farm "+deviceFarm);
            throw new MalformedURLException("Error en conexión a Device Farm");
        }

        return remoteWebDriver;
    }
}
