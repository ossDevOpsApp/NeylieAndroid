package com.slt.devops.neylie.api;

import com.slt.devops.neylie.models.gpsupdate.EquipmentResponse;
import com.slt.devops.neylie.models.gpsupdate.LoginResponse;
import com.slt.devops.neylie.models.gpsupdate.UpdateResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
            @Field("sid") String sid,
            @Field("password") String password,
            @Field("appversion") String appversion

    );

    @FormUrlEncoded
    @POST("allList")
    Call<EquipmentResponse> getEquipment(
            @Field("rtom") String rtom,
            @Field("eqtype") String eqtype
    );

    @FormUrlEncoded
    @POST("updateGps")
    Call<UpdateResponse> updateGps(
            @Field("sid") String sid,
            @Field("location") String location,
            @Field("lat") String lat,
            @Field("lon") String lon
    );


}


