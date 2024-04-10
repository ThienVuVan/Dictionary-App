package com.dictionary.api;

public class Function {
    public static String removeOuterParentheses(String s) {
        if (s == null || s.length() <= 2) {
            return "";
        }
        return s.substring(1, s.length() - 1);
    }
}
