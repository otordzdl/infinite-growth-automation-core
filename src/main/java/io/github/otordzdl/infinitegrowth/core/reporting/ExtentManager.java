package io.github.otordzdl.infinitegrowth.core.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public synchronized static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reporte-extent.html");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

        }
        return extent;
    }
}