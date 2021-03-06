package com.shtoone.shtw.utils;

public class ConstantsUtils {

    /**
     * 不允许new
     */
    private ConstantsUtils() {
        throw new Error("Do not need instantiate!");
    }

    public static final String DOMAIN_1 = "shtoone.com";
    public static final String DOMAIN_2 = "sh-toone";
    public static final String ISFIRSTENTRY = "isfirstentry";
    public static final String ISFIRSTGUIDE = "isfirstguide";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String LOGINCHECK = "logincheck";
    public static final String FROMTO = "fromto";
    public static final String PARAMETERS = "parameters";
    public static final String USERGROUPID = "usergroupid";

    public static final int LABORATORYFRAGMENT = 0;
    public static final int YALIJIFRAGMENT = 1;
    public static final int WANNENGJIFRAGMENT = 2;
    public static final int CONCRETEFRAGMENT = 3;
    public static final int MATERIALSTATISTICFRAGMENT = 4;
    public static final int PRODUCEQUERYFRAGMENT = 5;
    public static final int OVERPROOFFRAGMENT = 6;

    public static final int CAMERA = 1;
    public static final int ALBUM = 2;
    public static final int REFRESH = 11;
}
