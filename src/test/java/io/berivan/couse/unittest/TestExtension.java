package io.berivan.couse.unittest;


import org.json.JSONObject;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.sun.org.glassfish.external.statistics.impl.StatisticImpl.START_TIME;

public class TestExtension implements TestWatcher, BeforeTestExecutionCallback, AfterTestExecutionCallback
{


    Long testTime;


    // String properties = System.getProperty("build.name").concat("/" + System.getProperty("build.number"));
    // String reportFilePath = "/Users/berivan.goktas/Desktop/unittestcourse/src/test/Report" + properties;


   /* @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception
    {

        File file = new File(reportFilePath);

        if (!file.exists())
        {
            file.mkdir();
        }

    }*/


    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> optional)
    {
        createMyJsonObject(extensionContext, TestStatus.IGNORED);
    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext)
    {
        System.out.println("testSuccessful çalıştı");
        createMyJsonObject(extensionContext, TestStatus.PASSED);
    }

    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable)
    {
        createMyJsonObject(extensionContext, TestStatus.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable)
    {

        createMyJsonObject(extensionContext, TestStatus.FAILED);


    }


    public void createMyJsonObject(ExtensionContext extensionContext, TestStatus status)
    {

        String jonName = extensionContext.getRequiredTestClass().getName() + "." + extensionContext.getRequiredTestMethod().getName();
        File file = new File("/Users/berivan.goktas/Desktop/unittestcourse/src/test/report" + "/" + jonName.concat(".json"));

        try
        {
            JSONObject MyJsonObject = new JSONObject();
            MyJsonObject.put("Status", status);
            MyJsonObject.put("TagName", extensionContext.getTags().toString().substring(1, extensionContext.getTags().toString().length() - 1));
            MyJsonObject.put("DisplayName", extensionContext.getDisplayName());
            MyJsonObject.put("TestClassName", extensionContext.getRequiredTestClass().getName());
            MyJsonObject.put("testMethod", extensionContext.getRequiredTestMethod().getName());
            MyJsonObject.put("testTime", testTime);


            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(MyJsonObject.toString());
            bw.close();


            System.out.println(extensionContext);


        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }

    }

    private ExtensionContext.Store getStoreForMethod(ExtensionContext extensionContext)
    {
        return extensionContext.getStore(ExtensionContext.Namespace.create(getClass(), extensionContext.getRequiredTestMethod()));
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception
    {
        final LocalDateTime startTime = getStoreForMethod(extensionContext).remove(START_TIME, LocalDateTime.class);
        testTime = startTime.until(LocalDateTime.now(), ChronoUnit.SECONDS);
        System.out.println("afterTestExecution çalıştı" + testTime);

    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception
    {
        getStoreForMethod(extensionContext).put(START_TIME, LocalDateTime.now());
    }


    public enum TestStatus
    {
        PASSED, FAILED, IGNORED, ABORTED
    }

}

