package library.selenium.common;

import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectManager {

    public static WebElement getWebElement(String methodName, String className) {

        Object obj = null;
        Method method = null;
        WebElement element = null;

        try {
            obj = Class.forName("pageobjects." + className);
            method = ((Class) obj).getMethod(methodName);
            element = (WebElement) method.invoke(obj);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            return element;
        }
    }

}


