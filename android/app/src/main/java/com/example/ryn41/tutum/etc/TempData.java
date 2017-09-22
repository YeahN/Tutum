package com.example.ryn41.tutum.etc;

/**
 * Created by ryn41 on 2017-09-02.
 */

public class TempData {

    private static String mUserID = "";
    private static String mUserPW = "";
    private static String mUserName = "";

    public static void setID(String v) { mUserID = v; }
    public static void setPW(String v) { mUserPW = v; }
    public static void setName(String v) { mUserName = v; }

    public static String getID() { return mUserID; }
    public static String getPW() { return mUserPW; }
    public static String getName() { return mUserName; }
}
