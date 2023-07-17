package api.utility;

import java.io.IOException;
import java.util.Date;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

//ITestListener, ISuiteListener

public class ExtentListeners implements ITestListener, ISuiteListener {
    
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;	
	
	static Date d = new Date();
	static String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";

	    public void onStart(ITestContext testContext) {
	        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(".\\reports\\"+fileName);
	       
	        
	        htmlReporter.config().setTheme(Theme.DARK);
	        htmlReporter.config().setDocumentTitle("RestAssured API Test Report");
	        htmlReporter.config().setReportName("Pet Store User API test report");
	        htmlReporter.config().setEncoding("utf-8");
	        
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	        extent.setSystemInfo("Application", "Pet Store Users Api");
	        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
	        extent.setSystemInfo("User Name", System.getProperty("user.name"));
	        extent.setSystemInfo("Enviornment", "QA");
	        
	        
	     
	    }

	public void onTestSuccess(ITestResult result) {
        test=extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.PASS, "Test Passed");

	}

	public void onTestFailure(ITestResult result) {
		
		test=extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable().getMessage());

	}

	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, result.getThrowable().getMessage());

	}

	public void onFinish(ITestContext context) {

		if (extent != null) {

			extent.flush();
		}

	}

}
