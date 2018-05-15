package com.example.norkholis.securegateopener.helper;

public class APIUtil {
    public static final String BASE_URL_API = "http://80.211.135.231/mobile_parking/public/api/";

    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}
