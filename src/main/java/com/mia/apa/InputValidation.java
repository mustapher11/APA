package com.mia.apa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    static Pattern pattern;
    static Matcher matcher;
    public static boolean validateID(String id){
        pattern = Pattern.compile("[0-9]{6,8}");
        matcher = pattern.matcher(id);
        return !matcher.matches();
    }

    public static boolean validatePhone(String id){
        pattern = Pattern.compile("[0-9]{10,13}");
        matcher = pattern.matcher(id);
        return !matcher.matches();
    }

    public static boolean validateTextInput(String input){
        pattern = Pattern.compile("[a-zA-Z]{3,15}");
        matcher = pattern.matcher(input);
        return !matcher.matches();
    }
}
