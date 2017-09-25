package com.example.ryn41.tutum.etc;

/**
 * Created by ryn41 on 2017-09-02.
 */

public class TempData {

    private static int mFragmentIndex= 0;

    private static String mUserID = "";
    private static String mUserPW = "";
    private static String mUserName = "";
    private static int mUserPoint = 0;

    public static void setID(String v) { mUserID = v; }
    public static void setPW(String v) { mUserPW = v; }
    public static void setName(String v) { mUserName = v; }
    public static void setPoint(int v) { mUserPoint = v; }

    public static void setFragmentIndex(int v){ mFragmentIndex= v; }

    public static String getID() { return mUserID; }
    public static String getPW() { return mUserPW; }
    public static String getName() { return mUserName; }
    public static int getPoint() { return mUserPoint; }

    public static int getFragmentIndex(){ return mFragmentIndex; }
}
