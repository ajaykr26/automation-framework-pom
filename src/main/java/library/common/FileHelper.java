package library.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.*;


public class FileHelper {
    private static final int MAX_DEPTH = 10;
    static Logger logger = LogManager.getLogger(FileHelper.class);

    private FileHelper() {

    }

    public static JSONObject getJSONObject(String filepath, String... key) {
        try {
            FileReader reader = new FileReader(filepath);
            JSONTokener token = new JSONTokener(reader);
            JSONObject jsonObject = (JSONObject) (key.length > 0 ? new JSONObject(token).get(key[0]) : new JSONObject(token));
            return jsonObject;
        } catch (FileNotFoundException e) {
//            logger.error(e);
            return null;
        }
    }

    public static Map<String, String> getJSONObjectToMap(String filepath, String key) {
        return FileHelper.getJSONToMap(FileHelper.getJSONObject(filepath, key));
    }

    public static Map<String, String> getJSONObjectToMap(String filepath) {
        return FileHelper.getJSONToMap(FileHelper.getJSONObject(filepath));
    }

    public static <T> T getDataPOJO(String filepath, Class<T> clazz) throws IOException {
        Gson gson = new Gson();
        File file = new File(filepath);
        T dataobject = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            dataobject = gson.fromJson(bufferedReader, clazz);
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
        return dataobject;
    }

    public static Map<String, String> getJSONToMap(JSONObject json) {
        Map<String, String> map = new HashMap<>();
        String[] keys = JSONObject.getNames(json);
        for (String key : keys) {
            map.put(key, json.get(key).toString());
        }
        return map;
    }

    public static Map<String, String> getJSONToListOfMap(String filepath) {
        return FileHelper.getJSONToMap(FileHelper.getJSONObject(filepath));
    }

    public static List getJSONAsListOfMaps(String path) {
        Gson gson = new GsonBuilder().create();
        try {
            return gson.fromJson(new FileReader(path), List.class);
        } catch (FileNotFoundException e) {
            return Collections.emptyList();
        }
    }

}
