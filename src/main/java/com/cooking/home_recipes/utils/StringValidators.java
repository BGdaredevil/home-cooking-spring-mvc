package com.cooking.home_recipes.utils;

public class StringValidators {
    public static boolean isEmptyString(String testificate) {
        return testificate.trim().isEmpty();
    }

    public static boolean isSameString(String first, String second) {
        return first.trim().equals(second.trim());
    }
}
