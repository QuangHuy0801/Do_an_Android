package com.example.doan_nhom_6.Retrofit;

import com.example.doan_nhom_6.Model.Promotion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PromotionAPI {
    RetrofitService retrofitService = new RetrofitService();
    PromotionAPI promotionAPI = retrofitService.getRetrofit().create(PromotionAPI.class);
    @GET("/promotion/checkProduct/{productId}")
    Call<Promotion> checkProDuctInPromotion(@Path("productId") int id);
}
