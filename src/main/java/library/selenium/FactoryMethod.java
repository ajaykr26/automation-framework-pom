package library.selenium;

import library.selenium.common.*;

public class FactoryMethod extends BasePO{

    public static UtilsMethods getMiscMethods() {
        return new UtilsMethods();
    }

    public static NavigationMethods getNavigateMethods() {
        return new NavigationMethods();
    }

    public static ClickMethods getClickMethods() {
        return new ClickMethods();
    }

    public static InnputMethods getInputMethods() {
        return new InnputMethods();
    }

    public static WaitMethods getProgressMethods() {
        return new WaitMethods();
    }

    public static TableMethods getTableMethods() {
        return new TableMethods();
    }

    public static JSMethods getJavascriptHandlingMethods() {
        return new JSMethods();
    }

    public static ValidationMethods getAssertionMethods() {
        return new ValidationMethods();
    }
}
