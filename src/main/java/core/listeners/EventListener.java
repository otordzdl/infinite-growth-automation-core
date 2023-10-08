package core.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.github.otordzdl.infinitegrowth.core.base.BaseWebTest;
import io.github.otordzdl.infinitegrowth.core.interfaces.TestInterface;
import io.github.otordzdl.infinitegrowth.core.interfaces.TestWebInterface;
import io.github.otordzdl.infinitegrowth.core.reporting.ExtentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class EventListener extends TestListenerAdapter {
    protected ExtentReports extent = ExtentManager.getInstance();
    private static final Logger logger = LoggerFactory.getLogger(BaseWebTest.class);

    @Override
    public void onFinish(ITestContext testContext) {
        logger.info("FLUSH de extent report");
        extent.flush();
    }

    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        logger.info("Test Passed: "+ testName);
        Object currentClass = result.getInstance();
        if (currentClass instanceof TestWebInterface) {
            WebDriver driver = ((TestWebInterface) currentClass).getDriver();
            if (driver instanceof TakesScreenshot) {
                TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
                File screenshot = screenshotDriver.getScreenshotAs(OutputType.FILE);


                try {
                    String screenshotPath = "output/" + generateScreenshotName(testName);
                    FileUtils.copyFile(screenshot, new File(screenshotPath));
                    ExtentTest test = ((TestInterface) currentClass).getTest();

                    test.addScreenCaptureFromPath(screenshotPath);
                    driver.quit();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onTestFailure(ITestResult result) {
        Object currentClass = result.getInstance();
        ExtentTest test = ((TestInterface) currentClass).getTest();
        String testName = result.getMethod().getMethodName();
        logger.info("Test Failed: "+ testName);
        if (currentClass instanceof TestWebInterface) {
            WebDriver driver = ((TestWebInterface) currentClass).getDriver();
            if (driver instanceof TakesScreenshot) {
                TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
                File screenshot = screenshotDriver.getScreenshotAs(OutputType.FILE);


                try {
                    String screenshotPath = "output/" + generateScreenshotName(testName);
                    FileUtils.copyFile(screenshot, new File(screenshotPath));
                    test.log(Status.FAIL, result.getThrowable().getMessage());
                    test.addScreenCaptureFromPath(screenshotPath);
                    driver.quit();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (currentClass instanceof TestInterface) {
            logger.info("Test Failed: "+ testName);
            test.log(Status.FAIL, result.getThrowable().getMessage());
        }
    }

    private String generateScreenshotName(String testName) {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();

        randomUUIDString = randomUUIDString.replace("-", "");
        String screenshotName = "screenshot_" + testName + randomUUIDString + ".png";
        return screenshotName;
    }
}
