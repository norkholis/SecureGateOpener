package com.example.norkholis.securegateopener.helper;

public class APIUtil {
    public static final String BASE_URL_API = "http://128.199.133.21/mobile_parking/public/api/";

    public static BaseAPIService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}
