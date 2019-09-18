package io.berivan.couse.unittest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class ReportGeneratorUtils
{
    static String REPORT_GENERIC_PATH = "/Users/berivan.goktas/Desktop/unittestcourse/src/test/";
    static String properties = System.getProperty("build.name").concat("/" + System.getProperty("build.number"));
    static String TEST_RESULT_JSONS_PATH = REPORT_GENERIC_PATH + properties;

    public static void main(String[] args)
    {
        String TEST_RESULT_JSON_FOLDER_NAME = System.getProperty("build.name").concat("-" + System.getProperty("build.number").concat("-") + RandomStringUtils.randomAlphabetic(10));

        try (Stream<Path> paths = Files.walk(Paths.get(TEST_RESULT_JSONS_PATH)))
        {
            paths.filter(Files::isRegularFile).forEach(path -> convert(path, TEST_RESULT_JSON_FOLDER_NAME));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void convert(Path name, String folderName)
    {
        try
        {
            Path path = Paths.get(REPORT_GENERIC_PATH  + folderName);

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
        newObject.addProperty("StackTrace", testResultObject.getString("StackTrace"));

        inputObj.get("results").getAsJsonArray().add(newObject);

        return inputObj;
    }
}


