package com.solvd.webAutomation.utils;

import java.util.regex.Pattern;

/**
 * Utility class for string management and validation.
 */
public class StringUtils {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final String PHONE_REGEX = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})([- .]?\\d{3})?([- .]?\\d{3,4})?$";

    private StringUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Checks if a string is a valid email address.
     *
     * @param email The string to check.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email) || email.isBlank()) {
            return false;
        }
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    /**
     * Checks if a string is a valid phone number.
     * Supports formats like: +1 1234567890, +999 123456, (123) 456-7890, 123-456-7890, 1234567890.
     *
     * @param phone The string to check.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidPhone(String phone) {
        if (isNullOrEmpty(phone) || phone.isBlank()) {
            return false;
        }
        return Pattern.compile(PHONE_REGEX).matcher(phone).matches();
    }

    /**
     * Checks if a string is null or empty (including whitespace).
     *
     * @param str The string to check.
     * @return true if null or empty, false otherwise.
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Capitalizes the first letter of a string and lowers the rest.
     *
     * @param str The string to capitalize.
     * @return The capitalized string.
     */
    public static String capitalize(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Removes all non-numeric characters from a string.
     * Useful for extracting price values or clean phone numbers.
     *
     * @param str The string to clean.
     * @return A string containing only digits.
     */
    public static String extractDigits(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\D+", "");
    }

    /**
     * Truncates a string to a specific length and adds an ellipsis if needed.
     *
     * @param str       The string to truncate.
     * @param maxLength The maximum length.
     * @return The truncated string.
     */
    public static String truncate(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }

}
