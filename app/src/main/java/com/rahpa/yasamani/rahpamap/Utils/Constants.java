package com.rahpa.yasamani.rahpamap.Utils;

/**
 * Created by Saeed on 4/4/2018.
 */

public class Constants {
    public static String BASE_URL = "https://maps.googleapis.com/maps/api/";
    public static String MAP_KEY = "AIzaSyBjhnG7O8SHx9jNACGFMSFxJszasc5bdcc";
    public static WebServiceRequests webServiceRequests = RetrofitServiceCreator.CreateService(WebServiceRequests.class);
}
