package core.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Wrapper {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(Wrapper.class);

    public Wrapper(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void navigateTo(String url) {
        logger.info("Navegando a url "+url);
        driver.get(url);
    }

    public void fillFieldWithText(By selector, String text) {
        logger.info("Llenando campo "+ selector.toString() + "con "+ text);
        WebElement field = driver.findElement(selector);
        field.sendKeys(text);
    }
    public void fillFieldWithText(WebElement element, String text) {
        logger.info("Llenando campo "+ element.toString() + "con "+ text);
        element.sendKeys(text);
    }

    public void clickInElement(By selector) {
        logger.info("Click en  "+ selector.toString());
        WebElement element = driver.findElement(selector);
        element.click();
    }

    public void clickInElement(WebElement element) {
        logger.info("Click en  "+ element.toString());
        element.click();
    }

    public String getTextFromElement(By selector) {
        logger.info("Obteniendo texto de "+ selector.toString());
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
        return element.getText();
    }

    public String getTextFromElement(WebElement element) {
        logger.info("Obteniendo texto de "+ element.toString());
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public void waitElementVisible(By selector) {

        logger.info("Esperando que sea visible el elemento " + selector.toString());
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
            logger.info("Elemento visible " + selector.toString());
        } catch (Exception e) {
            logger.error("Elemento no visible " + selector.toString());
        }
    }
    public Boolean reviewIfElementIsDisplayed(By selector){
        logger.info("Revisando si elemento " + selector.toString()+" es visible");
        Boolean value;

        logger.info("Validando si existe:" + selector.toString());
        try {
            if (driver.findElement(selector).isDisplayed()) {
                value = true;
            } else {
                value = false;
            }

        } catch (Exception e) {
            value = false;
        }
          return value;
    }


    public void refreshPage() {
        logger.info("Refrescar Pagina");
        driver.navigate().refresh();
    }

    public void aceptAlert() {
        logger.info("Aceptar alerta");
        driver.switchTo().alert().accept();
    }

    public void cancelAlert() {
        logger.info("Cancelar alerta");
        driver.switchTo().alert().dismiss();
    }

    public void sendTextToAlert(String text) {
        logger.info("Enviar texto "+text+ " a alerta");
        driver.switchTo().alert().sendKeys(text);
    }

    public void quitDriver(){
        logger.info("Cerrando driver");
        driver.quit();
    }

    public WebDriver getDriver(){
        logger.info("Obteniendo driver");
        return this.driver;
    }

}
