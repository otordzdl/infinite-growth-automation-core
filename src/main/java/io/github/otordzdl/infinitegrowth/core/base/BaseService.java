package io.github.otordzdl.infinitegrowth.core.base;

import com.aventstack.extentreports.ExtentReports;
import io.github.otordzdl.infinitegrowth.core.reporting.ExtentManager;
import io.restassured.specification.RequestSpecification;

public class BaseService {
    protected RequestSpecification requester;
    protected ExtentReports extent = ExtentManager.getInstance();

    public BaseService(RequestSpecification requester) {

        this.requester = requester;
    }
}
