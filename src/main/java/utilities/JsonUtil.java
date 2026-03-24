package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {
    private static final Logger logger = LogManager.getLogger(JsonUtil.class);
    private static final Gson gson = new Gson();

    public static <T> T getTestData(String filePath, Class<T> classOfT) {
        try (Reader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, classOfT);
        } catch (IOException e) {
            logger.error("Failed to read JSON file: {}", e.getMessage());
            return null;
        }
    }

    public static JsonObject getJsonObject(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            logger.error("Failed to read JSON file: {}", e.getMessage());
            return null;
        }
    }
}