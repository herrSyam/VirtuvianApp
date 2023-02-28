package com.example.virtuvianapplication.app;

import android.util.Log;

import com.example.virtuvianapplication.model.AktivitasAnswerModel;
import com.example.virtuvianapplication.model.DietModel;
import com.example.virtuvianapplication.model.ObatSaveModel;
import com.example.virtuvianapplication.model.PersonObatModel;
import com.example.virtuvianapplication.model.QuestionModelAnswer;
import com.example.virtuvianapplication.response.AccessToken;
import com.example.virtuvianapplication.response.AktivitasResponse;
import com.example.virtuvianapplication.response.EventAktivitasResponse;
import com.example.virtuvianapplication.response.EventDietResponse;
import com.example.virtuvianapplication.response.FileResponse;
import com.example.virtuvianapplication.response.GdsResponse;
import com.example.virtuvianapplication.response.LookupResponse;
import com.example.virtuvianapplication.response.NotifResponse;
import com.example.virtuvianapplication.response.NotificationResponse;
import com.example.virtuvianapplication.response.ObatNotifResponse;
import com.example.virtuvianapplication.response.ObatResponse;
import com.example.virtuvianapplication.response.PenkesResponse;
import com.example.virtuvianapplication.response.PersonObatResponse;
import com.example.virtuvianapplication.response.PostResponse;
import com.example.virtuvianapplication.response.QuestionResponse;
import com.example.virtuvianapplication.response.UserResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })

    @POST("getToken/{userId}")
    Call<AccessToken> accessToken(
            @Path("userId") String userId
    );

    @POST("login")
    Call<UserResponse> getUser(
            @Query("name") String name,
            @Query("password") String password
    );

    @POST("updateTokeDevice")
    Call<PostResponse> updateTokenDevice(
            @Query("userId") String userId,
            @Query("device_token") String device_token
    );

    @POST("register")
    Call<UserResponse> register(
            @Query("password") String password,
            @Query("name") String name,
            @Query("age") Integer age,
            @Query("gender") Integer gender,
            @Query("school_id") String idSchool,
            @Query("employment_id") String idEmploy
    );

    @POST("logout")
    Call<PostResponse> logOut(
            @Header("Authorization") String token
    );

    @GET("diet/getdietByEvent")
    Call<QuestionResponse> question();

    @POST("dietuser/store")
    Call<PostResponse> addQues(@Body QuestionModelAnswer questionModelAnswer);

    @GET("dietuser/event")
    Call<EventDietResponse> getEventDiet(
            @Query("id") String id,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate
    );

    @GET("penkes")
    Call<PenkesResponse> getPenkes();

    @GET("activitas/getAll")
    Call<AktivitasResponse> aktivitas();

    @POST("personActivitas/action")
    Call<PostResponse> addPersonAktivitas(@Body AktivitasAnswerModel aktivitasAnswerModel);

    @GET("personActivitas/event")
    Call<EventAktivitasResponse> getEventAktivitas(
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("id") String id
    );

    @GET("Obat/GetAll")
    Call<ObatResponse> getObat();

    @POST("personNotification/action")
    Call<ObatNotifResponse> addPersonNotification(@Body ObatSaveModel obatSaveModel);

    @GET("eventNotification/getNotificationByObatId")
    Call<NotificationResponse> getNotification(
            @Query("obat_id") String obatId
    );

    @GET("personNotification/notificationList")
    Call<PersonObatResponse> getPersonObat(
            @Query("user_id") String user_id
    );

    @POST("Gds/action")
    Call<PostResponse> addGds(
            @Query("type") String type,
            @Query("user_id") String user_id,
            @Query("value_gds") Double value_gds
    );

    @GET("Gds/getGdsByUserId/{id}")
    Call<GdsResponse> getGdsByUserId(
            @Path("id") String userId
    );

    @GET("penkesUploadFile/getAll")
    Call<FileResponse> getFile();

    @POST("userApp/schoolLookup")
    Call<LookupResponse> lookupSchool(
            @Query("search") String search
    );

    @POST("userApp/employmentLookup")
    Call<List<LookupResponse>> lookupEmployment(
            @Query("search") String search
    );

    @POST("userApp/schoolLookup")
    Call<List<LookupResponse>> lookupschool(
            @Query("search") String search
    );
}
