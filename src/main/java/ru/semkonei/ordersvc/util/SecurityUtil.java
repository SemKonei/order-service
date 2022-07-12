package ru.semkonei.ordersvc.util;

public class SecurityUtil {

    private static Integer authUserId = 1;

    public static Integer authUserId() {
        return authUserId;
    }

    public static void setAuthUserId(Integer id) {
        authUserId = id;
    }
}
