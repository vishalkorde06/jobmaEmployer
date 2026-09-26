package automation.customlistener;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;

import automation.baseclass.TestBase;

public class Listeners extends TestBase implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        try {
            String path = getScreenShotForExtent(result.getName());
            test.pass(result.getName(),
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            String path = getScreenShotForExtent(result.getName());
            test.fail(result.getName(),
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
            test.fail(result.getThrowable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        try {
            String path = getScreenShotForExtent(result.getName());
            test.skip(result.getName(),
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(ITestContext context) {}

    @Override
    public void onFinish(ITestContext context) {}

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
}