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
    @GET("/revenue-statistic")
    Call<List<ReportTotal>> RevenueStatistic(@Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo);
    @GET("/quantity-statistic")
    Call<List<ReportTotal>> QuantityStatistic(@Query("dateFrom") String dateFrom, @Query("dateTo") String dateTo);
    @GET("/product-statistic")
    Call<List<ReportTotal>> ProductStatistic();
    @GET("/unit-of-product-statistic")
    Call<List<ReportTotal>> UnitOfProductStatistic();
}
