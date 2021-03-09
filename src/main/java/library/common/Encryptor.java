package library.common;

import library.common.Constants;
import library.common.Property;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Encryptor {

    private static Logger logger = LogManager.getLogger(Encryptor.class);

    private Encryptor() {
    }

    public static void main(String[] args) throws Exception {
        File folder = new File(Constants.ENVIRONMENT_PATH);
        File[] files = folder.listFiles();
        int i = 0;
        Map<Object, String> envMap = new HashMap<Object, String>();
        assert files != null;
        for (File file : files) {
            if (!file.getName().startsWith("SecureText-")) {
                envMap.put(i, file.getName().replace(".properties", ""));
                System.out.println(i + ". " + file.getName().replace(".properties", ""));
                i++;
            }
        }
        Scanner intScanner = new Scanner(System.in);
        System.out.println("Please select the environment");
        int envir = intScanner.nextInt();
        String environment = envMap.get(envir);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the key");
        String key = scanner.nextLine();
        System.out.println("Please enter the value");
        String value = scanner.nextLine();

        writeSecureText(environment, key, value);
    }

    private static void writeSecureText( String environment, String key, String valueToEncrypt) throws IOException {
        if (environment!=null){
            String propFilePath = Constants.ENVIRONMENT_PATH + "SecureText-" + environment + ".properties";
            String encryptedTextValue = encrypt(valueToEncrypt);
            System.out.println("Encrypted String:" + encryptedTextValue);
            File file = new File(propFilePath);
            if (!file.exists()) {
                final boolean newFile = file.createNewFile();
                System.out.println("SecureText-" + environment + ".properties file created");
                Property.setProperty(propFilePath, key, encryptedTextValue);
                System.out.println("encrypted values of" + valueToEncrypt + "written in the " + "SecureText-" + environment + ".properties file");
            } else {
                Property.setProperty(propFilePath, key, encryptedTextValue);
                System.out.println("encrypted values written in the " + "SecureText-" + environment + ".properties file");
            }
        }else{
            System.out.println("environment can not be null. please check the environment");
        }
    }

    private static void writeSecureText( String key, String valueToEncrypt) throws IOException {
        String environment = Property.getVariable("environment");
        if (environment!=null){
            String propFilePath = Constants.ENVIRONMENT_PATH + "SecureText-" + environment + ".properties";
            String encryptedTextValue = encrypt(valueToEncrypt);
            System.out.println("Encrypted String:" + encryptedTextValue);
            File file = new File(propFilePath);
            if (!file.exists()) {
                final boolean newFile = file.createNewFile();
                System.out.println("SecureText-" + environment + ".properties file created");
                Property.setProperty(propFilePath, key, encryptedTextValue);
                System.out.println("encrypted values of" + valueToEncrypt + "written in the " + "SecureText-" + environment + ".properties file");
            } else {
                Property.setProperty(propFilePath, key, encryptedTextValue);
                System.out.println("encrypted values written in the " + "SecureText-" + environment + ".properties file");
            }
        }else{
            System.out.println("environment can not be null. please check the environment");
        }
    }

    public static String parseSecureText(String propFilePath, String encryptedStringKey) {
        String encryptedString = Property.getProperty(propFilePath, encryptedStringKey);
        String decryptedString = null;
        if (encryptedString != null) {
            decryptedString = decrypt(encryptedString);
            return decryptedString;
        } else {
            logger.error("entry for key '{}' was not found in the SecureText property file", encryptedStringKey);
        }
        return null;
    }

    private static String encrypt(String plainString) {
        String encryptedString = null;
        if (plainString != null) {
            byte[] byteArray = Base64.encodeBase64(plainString.getBytes());
            encryptedString = new String(byteArray);
        } else {
            logger.error("please enter plain text to be encrypted");
        }
        return encryptedString;
    }

    public static String decrypt(String encryptedString) {
        String decryptedString = null;
        if (encryptedString != null) {
            byte[] decodedByteArray = Base64.decodeBase64(encryptedString.getBytes());
            decryptedString = new String(decodedByteArray);
            return decryptedString;
        } else {
            logger.error("encrypted string not found in the SecureText property file");
        }
        return null;
    }

}
