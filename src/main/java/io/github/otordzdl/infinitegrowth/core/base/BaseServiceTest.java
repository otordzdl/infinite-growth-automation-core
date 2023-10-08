package io.github.otordzdl.infinitegrowth.core.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.otordzdl.infinitegrowth.core.interfaces.TestInterface;
import io.github.otordzdl.infinitegrowth.core.listeners.EventListener;
import io.github.otordzdl.infinitegrowth.core.reporting.ExtentManager;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

@Listeners(EventListener.class)
public abstract class BaseServiceTest implements TestInterface {
    protected ExtentReports extent = ExtentManager.getInstance();
    protected ExtentTest test;
    protected RequestSpecification requester;
    private static final Logger logger = LoggerFactory.getLogger(BaseWebTest.class);


    @BeforeTest
    @Parameters("url")
    public void setUpTest(String url, ITestContext context) {
        test = extent.createTest(context.getName());
        logger.info("Setup de testcase " + context.getName());
        requester = RestAssured.given();
        logger.info("RestAssure url:  " + url);
        requester.baseUri(url);
    }


    @AfterSuite
    public void tearDownSuite() {

        extent.flush();
    }

    public ExtentTest getTest() {
        return test;
    }
}
