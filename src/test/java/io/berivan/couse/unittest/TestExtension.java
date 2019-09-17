package io.berivan.couse.unittest;


import org.json.JSONObject;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.sun.org.glassfish.external.statistics.impl.StatisticImpl.START_TIME;


public class TestExtension implements TestWatcher, BeforeTestExecutionCallback, AfterTestExecutionCallback, BeforeAllCallback
{
    private Long testTime;

    String properties = System.getProperty("build.name").concat("/" + System.getProperty("build.number"));
    String reportFilePath = "/Users/berivan.goktas/Desktop/unittestcourse/src/test/Report" + properties;


    @Override
    public void beforeAll(ExtensionContext extensionContext)
    {
        Path path = Paths.get(reportFilePath);

        if (!Files.exists(path))
        {
            try
            {
                Files.createDirectories(path);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext)
    {
        getStoreForMethod(extensionContext).put(START_TIME, LocalDateTime.now());
    }

    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> optional)
    {
        createMyJsonObject(extensionContext, TestStatus.IGNORED);
    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext)
    {
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
        File file = new File(reportFilePath+ "/" + jonName.concat(".json"));

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

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception
    {
        final LocalDateTime startTime = getStoreForMethod(extensionContext).remove(START_TIME, LocalDateTime.class);
        testTime = startTime.until(LocalDateTime.now(), ChronoUnit.SECONDS);
    }

    private ExtensionContext.Store getStoreForMethod(ExtensionContext extensionContext)
    {
        return extensionContext.getStore(ExtensionContext.Namespace.create(getClass(), extensionContext.getRequiredTestMethod()));
    }


    public enum TestStatus
    {
        PASSED, FAILED, IGNORED, ABORTED
    }

}

