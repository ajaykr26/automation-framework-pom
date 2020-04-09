package automation.library.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;


public class Encryptor {

    private static Logger logger = LogManager.getLogger(Encryptor.class);
    private Encryptor() {
    }

    public static void main(String[] args) throws Exception {

        String envProp = Property.getVariable("cukes.env");
        String propFilePath = Constants.SECURETEXT_FILE_PATH + "SecureText-" + envProp + ".properties";
        String encryptedTextValue = encrypt(args[1]);
        System.out.println("Encrypted String:" + encryptedTextValue);
        File file = new File(propFilePath);
        if(!file.exists()){
            final boolean newFile = file.createNewFile();
            logger.info("SecureText-" + envProp + ".properties file created");
            Property.setProperty(propFilePath, args[0], encryptedTextValue);
            logger.info("encrypted values of" +args[0]+ "wrriten in the " + "SecureText-" + envProp + ".properties file");
        }else {
            Property.setProperty(propFilePath, args[0], encryptedTextValue);
            logger.info("encrypted values wrriten in the " + "SecureText-" + envProp + ".properties file");
        }


    }

    public static String parseSecureText(String propFilePath, String encryptedStringKey) {
        String encryptedString = Property.getProperty(propFilePath, encryptedStringKey);
        String decryptedString = null;
        if (encryptedString != null) {
            decryptedString = decrypt(encryptedString);
            return decryptedString;
        } else {
            logger.error("entry for key '{}' was not found in the SecureText property file", encryptedStringKey, propFilePath);
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
