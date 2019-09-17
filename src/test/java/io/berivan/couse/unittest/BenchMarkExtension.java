package io.berivan.couse.unittest;


import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class BenchMarkExtension implements BeforeAllCallback, AfterAllCallback, BeforeTestExecutionCallback , AfterTestExecutionCallback, TestWatcher
{
    private static final String START_TIME = "Satrt Time";


    @Override
    public void afterAll(ExtensionContext context) throws Exception
    {

        final LocalDateTime startTime = getStoreForContainer(context).remove(START_TIME, LocalDateTime.class);
        final long runTime = startTime.until(LocalDateTime.now(), ChronoUnit.MILLIS);
       //logger.info(String.format("Run Time for test container<%s>: %s ms", context.getRequiredTestClass().getName(),runTime ));
        System.out.println("Container bitti");
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception
    {
        getStoreForContainer(context).put(START_TIME, LocalDateTime.now());
        System.out.println("Container başladı");
    }

    private ExtensionContext.Store getStoreForContainer(ExtensionContext context)
    {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestClass()));

    }


    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception
    {
        final LocalDateTime startTime = getStoreForMethod(context).remove(START_TIME, LocalDateTime.class);
        final long runTime = startTime.until(LocalDateTime.now(), ChronoUnit.MILLIS);
       // logger.info(String.format("Run Time for test method<%s>: %s ms", context.getRequiredTestMethod().getName(),runTime ));
        System.out.println("Test bitti");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception
    {
        getStoreForMethod(context).put(START_TIME, LocalDateTime.now());
        System.out.println("test başladı");
    }


    private ExtensionContext.Store getStoreForMethod(ExtensionContext context)
    {
        return context.getStore(ExtensionContext.Namespace.create(getClass(), context.getRequiredTestMethod()));
    }


    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> optional)
    {
        System.out.println("Test ignore edildi.");
    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext)
    {

        System.out.println("Test başarılı bitti test watcher extession");

    }

    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable)
    {
        System.out.println("test Aborted edildi");
    }

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable)
    {
        System.out.println("Test fail test watcher extession");
    }


}
