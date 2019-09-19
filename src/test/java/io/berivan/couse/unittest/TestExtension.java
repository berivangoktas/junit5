package io.berivan.couse.unittest;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.Contact;
import io.Kure;
import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class TestExtension implements TestWatcher, BeforeTestExecutionCallback, AfterTestExecutionCallback, BeforeAllCallback, ExtensionContext.Store.CloseableResource
{
    private Long testTime;
    private static final String START_TIME = "Start Time";
    private static Reflections reflections;

    static String TEST_RESULT_JSON_BASE_PATH = "/Users/berivan.goktas/Desktop/unittestcourse/src/test/TestResultJsons/";
    static String TEST_RESULT_BASE_PATH = "/Users/berivan.goktas/Desktop/unittestcourse/src/test/TestResult/";


    Map<String, Kure> testClassesMap = new HashMap<>();
    static String TEST_RESULT_BUILD_NUMBER_PATH;


    @Override
    public void beforeAll(ExtensionContext extensionContext)
    {
        String TEST_RESULT_BUILD_NAME_PATH = TEST_RESULT_JSON_BASE_PATH.concat(System.getProperty("build.name"));

        createFolderControl(TEST_RESULT_BUILD_NAME_PATH);

        TEST_RESULT_BUILD_NUMBER_PATH = TEST_RESULT_BUILD_NAME_PATH.concat("/").concat(System.getProperty("build.number"));

        createFolderControl(TEST_RESULT_BUILD_NUMBER_PATH);


        reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("io.berivan"))
                .setScanners(new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner())
        );

        if (testClassesMap.isEmpty())
        {

            Set<Method> categorySet = reflections.getMethodsAnnotatedWith(Test.class);
            for (Method method : categorySet)
            {
                Kure contact = method.getDeclaredAnnotation(Contact.class).value();
                String className = method.getDeclaringClass().getName();
                String methodName = method.getName();
                String classNameAndMethodName = className.concat(".").concat(methodName);
                testClassesMap.put(classNameAndMethodName, contact);
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
        createMyJsonObject(extensionContext, TestStatus.FAILED, throwable);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception
    {
        final LocalDateTime startTime = getStoreForMethod(extensionContext).remove(START_TIME, LocalDateTime.class);
        testTime = startTime.until(LocalDateTime.now(), ChronoUnit.SECONDS);


        String removeKey = extensionContext.getRequiredTestClass().getName().concat(".").concat(extensionContext.getRequiredTestMethod().getName());
        testClassesMap.remove(removeKey);
    }


    @Override
    public void close()
    {

        String TEST_RESULT_BUILD_NAME_PATH = TEST_RESULT_BASE_PATH.concat(System.getProperty("build.name"));

        createFolderControl(TEST_RESULT_BUILD_NAME_PATH);

        TEST_RESULT_BUILD_NUMBER_PATH = TEST_RESULT_JSON_BASE_PATH.concat("/").concat(System.getProperty("build.number").concat("-") + RandomStringUtils.randomAlphabetic(10));

        try (Stream<Path> paths = Files.walk(Paths.get(TEST_RESULT_BUILD_NUMBER_PATH)))
        {
            paths.filter(Files::isRegularFile).forEach(path -> convert(path, TEST_RESULT_BUILD_NUMBER_PATH));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public void createMyJsonObject(ExtensionContext extensionContext, TestStatus status)
    {
        createMyJsonObject(extensionContext, status, null);
    }

    public void createMyJsonObject(ExtensionContext extensionContext, TestStatus status, Throwable throwable)
    {
        String jonName = extensionContext.getRequiredTestClass().getName() + "." + extensionContext.getRequiredTestMethod().getName();
        File file = new File(TEST_RESULT_BUILD_NUMBER_PATH + "/" + jonName.concat(".json"));
        try
        {
            JSONObject MyJsonObject = new JSONObject();
            MyJsonObject.put("Status", status);
            MyJsonObject.put("TagName", extensionContext.getTags().toString().substring(1, extensionContext.getTags().toString().length() - 1));
            MyJsonObject.put("DisplayName", extensionContext.getDisplayName());
            MyJsonObject.put("TestClassName", extensionContext.getRequiredTestClass().getName());
            MyJsonObject.put("TestMethod", extensionContext.getRequiredTestMethod().getName());
            MyJsonObject.put("TestTime", testTime);
            MyJsonObject.put("Kure", extensionContext.getRequiredTestMethod().getAnnotation(Contact.class).value());

            if (throwable == null)
            {
                MyJsonObject.put("StackTrace", "null");
            }
            else
            {
                MyJsonObject.put("StackTrace", throwable.getMessage());
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(MyJsonObject.toString());
            bw.close();
            System.out.println(extensionContext);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private ExtensionContext.Store getStoreForMethod(ExtensionContext extensionContext)
    {
        return extensionContext.getStore(ExtensionContext.Namespace.create(getClass(), extensionContext.getRequiredTestMethod()));
    }


    public static void createFolderControl(String path)
    {

        if (!Files.exists(Paths.get(path)))
        {
            try
            {
                Files.createDirectories(Paths.get(path));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static void convert(Path name, String folderName)
    {
        try
        {
            Path path = Paths.get(folderName);

            File file = new File(path.toUri().getPath().concat("/TestResults.json"));

            if (!Files.exists(path))
            {
                Files.createDirectories(path);

                if (!file.isFile())
                {
                    file.createNewFile();

                    FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("{\"results\":[]}");
                    bw.close();
                }
            }

            Path output = Paths.get(file.getAbsolutePath());

            String object = new String(Files.readAllBytes(Paths.get(name.toUri()))).replaceAll("[\t\r\n]", "");

            String fileContent = readFileContent(file);

            JsonObject testResultObject = createTestResultObject(fileContent, object);

            Files.write(output, testResultObject.toString().getBytes(), StandardOpenOption.CREATE);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static String readFileContent(File file)
    {
        String fileContent = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath())))
        {
            String line;

            while ((line = br.readLine()) != null)
            {
                fileContent += line;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return fileContent;
    }

    private static JsonObject createTestResultObject(String fileContent, String object)
    {
        Gson gson = new Gson();

        JSONObject testResultObject = new JSONObject(object);

        JsonObject inputObj = gson.fromJson(fileContent, JsonObject.class);
        JsonObject newObject = new JsonObject();

        newObject.addProperty("Status", testResultObject.getString("Status"));
        newObject.addProperty("TestClassName", testResultObject.getString("TestClassName"));
        newObject.addProperty("TagName", testResultObject.getString("TagName"));
        newObject.addProperty("DisplayName", testResultObject.getString("DisplayName"));
        newObject.addProperty("TestMethod", testResultObject.getString("TestMethod"));
        newObject.addProperty("TestTime", testResultObject.getLong("TestTime"));
        newObject.addProperty("Kure", testResultObject.getString("Kure"));
        newObject.addProperty("StackTrace", testResultObject.getString("StackTrace"));

        inputObj.get("results").getAsJsonArray().add(newObject);

        return inputObj;
    }


    public enum TestStatus
    {
        PASSED, FAILED, IGNORED, ABORTED
    }

}

