package com.aruoxi.ebookshop.common;

import java.util.regex.Pattern;
 
public class RegexUtil {
    private static final String EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public boolean isEmail(String email) {
        return Pattern.compile(EMAIL).matcher(email).matches();
    }
}