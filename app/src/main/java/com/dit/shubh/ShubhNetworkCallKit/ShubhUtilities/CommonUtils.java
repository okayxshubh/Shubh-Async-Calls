package com.dit.shubh.ShubhNetworkCallKit.ShubhUtilities;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.*;
import android.os.Build;
import android.text.format.Formatter;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;


import com.dit.shubh.ShubhNetworkCallKit.model.PhoneDetailsPojo;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtils {

    /**
     * Convert Bitmap to Base64 encoded string.
     * Usage: String base64 = CommonUtils.bitmapToBase64(bitmap);
     * @return Base64 string
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }

    /**
     * Convert Base64 string to Bitmap.
     * Usage: Bitmap bmp = CommonUtils.base64ToBitmap(base64);
     * @return Bitmap
     */
    public static Bitmap base64ToBitmap(String base64) {
        byte[] decoded = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
    }

    /**
     * Convert epoch time to formatted date string.
     * Usage: String date = CommonUtils.getDateTime(1690000000, "dd/MM/yyyy");
     * @return Date string
     */
    public static String getDateTime(long seconds, String format) {
        if (String.valueOf(seconds).length() > 10) seconds /= 1000;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(seconds * 1000);
    }

    /**
     * Get screen width in pixels.
     * Usage: int width = CommonUtils.getScreenWidth(context);
     * @return Width in px
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * Get screen height in pixels.
     * Usage: int height = CommonUtils.getScreenHeight(context);
     * @return Height in px
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * Check if string is valid email.
     * Usage: if(CommonUtils.isEmail(email)) {...}
     * @return true/false
     */
    public static boolean isEmail(String input) {
        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    /**
     * Get rounded corner bitmap.
     * Usage: Bitmap round = CommonUtils.getRoundCornerBitmap(bitmap);
     * @return Rounded Bitmap
     */
    public static Bitmap getRoundCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        RectF rectF = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawRoundRect(rectF, 20, 20, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return output;
    }

    /**
     * Get custom-rounded bitmap with options for each corner.
     * @return Rounded Bitmap
     */
    public static Bitmap getRoundedCornerBitmap(Context context, Bitmap input, int pixels, int w, int h,
                                                boolean squareTL, boolean squareTR, boolean squareBL, boolean squareBR) {
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect rect = new Rect(0, 0, w, h);
        RectF rectF = new RectF(rect);
        float roundPx = pixels * context.getResources().getDisplayMetrics().density;

        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        if (squareTL) canvas.drawRect(0, 0, w / 2, h / 2, paint);
        if (squareTR) canvas.drawRect(w / 2, 0, w, h / 2, paint);
        if (squareBL) canvas.drawRect(0, h / 2, w / 2, h, paint);
        if (squareBR) canvas.drawRect(w / 2, h / 2, w, h, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(input, 0, 0, paint);

        return output;
    }

    /**
     * Convert dp to pixel.
     * Usage: int px = CommonUtils.dpToPx(10, context);
     * @return px value
     */
    public static int dpToPx(int dp, Context ctx) {
        float density = ctx.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    /**
     * Get just file name from path.
     * Usage: String name = CommonUtils.getFileNameFromPath(path);
     * @return file name
     */
    public static String getFileNameFromPath(String path) {
        return path.substring(path.lastIndexOf('/') + 1);
    }

    /**
     * Get current date in dd/MM/yyyy format.
     * Usage: String date = CommonUtils.getCurrentDate();
     * @return formatted date
     */
    public static String getCurrentDate() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    }

    /**
     * Get device info in PhoneDetailsPojo.
     * Usage: PhoneDetailsPojo info = CommonUtils.getDeviceInfo();
     * @return phone details
     */
    public static PhoneDetailsPojo getDeviceInfo() {
        PhoneDetailsPojo info = new PhoneDetailsPojo();

        info.setBrand(Build.BRAND);
        info.setManufacturer(Build.MANUFACTURER);
        info.setModel(Build.MODEL);
        info.setId(Build.ID);
        info.setSerial(Build.SERIAL); // Returns "unknown" on Android 10+
        info.setVersionRelease(Build.VERSION.RELEASE);
        info.setSdkInt(Build.VERSION.SDK_INT);
        info.setDevice(Build.DEVICE);
        info.setHardware(Build.HARDWARE);
        info.setHost(Build.HOST);
        info.setProduct(Build.PRODUCT);
        info.setUser(Build.USER);
        info.setType(Build.TYPE);
        info.setFingerprint(Build.FINGERPRINT);
        info.setBoard(Build.BOARD);
        info.setBootloader(Build.BOOTLOADER);
        info.setDisplay(Build.DISPLAY);

        return info;
    }

    /**
     * Convert messy string to Integer.
     * Usage: Integer val = CommonUtils.str2Int("12.5");
     * @return Integer or null
     */
    public static Integer str2Int(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            str = str.replaceAll("[^\\d-]", "");
            return str.isEmpty() ? null : Integer.parseInt(str);
        }
    }

    /**
     * Check if number is positive.
     * Usage: if(CommonUtils.positiveNegative(num)) {...}
     * @return true if > 0 else false
     */
    public static boolean positiveNegative(Integer number) {
        return number != null && number > 0;
    }

    /**
     * Get local IP address.
     * @return IP string or null
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return Formatter.formatIpAddress(inetAddress.hashCode());
                    }
                }
            }
        } catch (SocketException e) {
            Log.e("IP", e.toString());
        }
        return null;
    }

    /**
     * Get IPv4 or IPv6 address.
     * @return IP Address
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            for (NetworkInterface intf : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                for (InetAddress addr : Collections.list(intf.getInetAddresses())) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        boolean isIPv4 = sAddr.indexOf(':') < 0;
                        if (useIPv4 && isIPv4) return sAddr;
                        if (!useIPv4 && !isIPv4) {
                            int delim = sAddr.indexOf('%');
                            return (delim < 0) ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                        }
                    }
                }
            }
        } catch (Exception ignored) {}
        return "";
    }

    /**
     * Encode string to Base64.
     * @return Base64 String
     */
    public static String encodeStringToBase64(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    /**
     * Encode byte array to Base64.
     * @return Base64 String
     */
    public static String encodeBytesToBase64(byte[] inputBytes) {
        return Base64.encodeToString(inputBytes, Base64.DEFAULT);
    }

    /**
     * Get app version name and code.
     * Usage: String version = CommonUtils.getVersionInfo(context);
     * @return version string
     */
    public static String getVersionInfo(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return "Version Code: " + info.versionCode + ", Version Name: " + info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Version info not available";
        }
    }
}