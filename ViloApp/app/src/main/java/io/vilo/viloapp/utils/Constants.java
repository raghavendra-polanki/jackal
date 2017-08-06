package io.vilo.viloapp.utils;

public class Constants {
    //TODO remove commented code
    //public static final String BASE_URL = "http://ec2-52-5-192-231.compute-1.amazonaws.com/v0";


    public static final String BASE_URL = "http://api.vilo.io/v1";//"http://api.3dphy.com/v1";;
    public static final String PHONE_NUMBER = "phone_number";
    public static final int RV_CACHE_COUNT = 10;
    public static final String BROADCAST_LOGOUT = "io.vilo.viloapp.LOGOUT";

    public static final String LOGIN_NEW_USER = "Login with new account";
    public static final String LOGIN_OLD_USER = "Login with old account";
    public static final String LOGOUT = "Logout";
    public static final String NEW_SESSION = "New Session";

    public static final String REGEX_REQUIRED = "^(?=\\s*\\S).*$";

    public static final String REGEX_OTP = "^[0-9]{4,6}$";

    public static final String OTP_REGEX = "([0-9]{4})";
    public static final String SMS_SENDER = "PRPQBE";

}
