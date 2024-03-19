package com.example.doan_nhom_6.Retrofit;

import com.example.doan_nhom_6.Model.ReportTotal;
import com.example.doan_nhom_6.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AdminAPI {
    RetrofitService retrofitService = new RetrofitService();
    AdminAPI adminApi = retrofitService.getRetrofit().create(AdminAPI.class);

    @GET("/loginAdmin")
    Call<User> Login(@Query("id") String id, @Query("password") String password);
    @GET("/ReportTotal")
    Call<List<ReportTotal>> ReportTotal(@Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo);
}
