package com.aruoxi.ebookshop.common;

import java.util.regex.Pattern;
 
public class RegexUtil {
    private static final String EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String BOOK_PAGES_SEARCH = "^[0-9]+-[0-9]+$";

    public boolean isEmail(String email) {
        return Pattern.compile(EMAIL).matcher(email).matches();
    }

    public boolean isPageStartAndEnd(String bookPagesSearch) {
        boolean matches = Pattern.compile(BOOK_PAGES_SEARCH).matcher(bookPagesSearch).matches();
        if (!matches) {
            return false;
        }
        String[] pages = bookPagesSearch.split("-");
        int compare = Integer.compare(Integer.parseInt(pages[0]), Integer.parseInt(pages[1]));
        return compare < 0;
    }

    public boolean isNumber(String numberString) {
        if (!Pattern.matches("^[0-9]+$", numberString)) {
            return false;
        }
        return Integer.parseInt(numberString) > 0;
    }
}