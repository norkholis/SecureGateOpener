package com.example.norkholis.securegateopener.helper;

import com.example.norkholis.securegateopener.model.ListLogin;
import com.example.norkholis.securegateopener.model.ListRegistrasi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseAPIService {
    @FormUrlEncoded
    @POST("registrasi")
    Call<ListRegistrasi>requestRegister (@Field("nama_lengkap") String nama_lengkap,
                                        @Field("email") String email,
                                        @Field("alamat") String alamat,
                                        @Field("no_telp") String no_telp,
                                        @Field("username") String username,
                                        @Field("password") String password,
                                        @Field("verifikasi_pengguna")String verifikasi_pengguna,
                                        @Field("disabled_key")String disabled_key);

    @FormUrlEncoded
    @POST("login")
    Call<ListLogin> loginRequest(@Field("username") String username,
                                 @Field("password")String password);


}
