package io.github.otordzdl.infinitegrowth.core.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.otordzdl.infinitegrowth.core.interfaces.TestWebInterface;
import io.github.otordzdl.infinitegrowth.core.listeners.EventListener;
import io.github.otordzdl.infinitegrowth.core.reporting.ExtentManager;
import io.github.otordzdl.infinitegrowth.core.selenium.BrowserType;
import io.github.otordzdl.infinitegrowth.core.selenium.RemoteWebDriverManager;
import io.github.otordzdl.infinitegrowth.core.selenium.WebDriverManagerFactory;
import io.github.otordzdl.infinitegrowth.core.selenium.Wrapper;
import io.github.otordzdl.infinitegrowth.core.utils.ConfigLoader;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;

@Listeners(EventListener.class)

public abstract class BaseWebTest implements TestWebInterface {
    protected Wrapper wrapper;
    protected ExtentReports extent = ExtentManager.getInstance();
    protected ExtentTest test;
    private static final Logger logger = LoggerFactory.getLogger(BaseWebTest.class);


    @BeforeSuite
    public void setUp() {
        logger.info("Arranque de suite de Pruebas");
    }

    @BeforeTest
    @Parameters("browser")
    public void setUpTest(String browser, ITestContext context) {

        String executionMode =  System.getenv("EXECUTION_MODE");
        test = extent.createTest(context.getName());
        logger.info("Setup de testcase " + context.getName());
        BrowserType browserType = BrowserType.valueOf(browser.toUpperCase());
        WebDriverManager driverManager = WebDriverManagerFactory.getWebDriverManager(browserType);
        driverManager.setup();
        WebDriver driver;


        try {
            if (executionMode.equalsIgnoreCase("LOCAL")) {
                logger.info("Ejecución LOCAL de  " + context.getName());
                driver = driverManager.create();
            } else {
                try {
                    logger.info("Levantando driver remoto  " + context.getName());
                    driver = RemoteWebDriverManager.getRemoteWebDriver(browserType);
                } catch (MalformedURLException e) {
                    test.log(Status.SKIP, e.getMessage());
                    logger.info("Test SKIPED por error  " + context.getName());
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            logger.info("Test SKIPED por error  " + context.getName());
            test.log(Status.SKIP, e.getMessage());
            throw new RuntimeException(e);
        }

        wrapper = new Wrapper(driver);

    }


    @AfterTest
    public void tearDownTest() {
        logger.info("Cerrando driver");
        wrapper.quitDriver();
    }

    @AfterSuite
    public void tearDownSuite() {

        extent.flush();
    }

    public ExtentTest getTest() {
        return test;
    }

    @Override
    public WebDriver getDriver() {
        return wrapper.getDriver();
    }
}
