package com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public class ValidationUtils {

    public static boolean isValidPhone(String phone) {
        return !TextUtils.isEmpty(phone) && phone.length() >= 10 && Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidAadhaar(String aadhaar) {
        return aadhaar != null && aadhaar.matches("\\d{12}");
    }

    public static boolean isValidPinCode(String pincode) {
        return pincode != null && pincode.matches("\\d{6}");
    }

    public static boolean isValidIFSC(String ifsc) {
        return ifsc != null && Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$").matcher(ifsc).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isOnlyAlphabets(String input) {
        return input != null && input.matches("^[a-zA-Z ]+$");
    }

    public static boolean isOnlyNumbers(String input) {
        return input != null && input.matches("^[0-9]+$");
    }

    public static boolean isAlphanumeric(String input) {
        return input != null && input.matches("^[a-zA-Z0-9]+$");
    }
}
