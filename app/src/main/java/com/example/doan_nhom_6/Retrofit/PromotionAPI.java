package com.example.doan_nhom_6.Retrofit;

import com.example.doan_nhom_6.Model.Promotion;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PromotionAPI {
    RetrofitService retrofitService = new RetrofitService();
    PromotionAPI promotionAPI = retrofitService.getRetrofit().create(PromotionAPI.class);
    @GET("/promotion")
    Call<List<Promotion>> getAllPromotion();
    @GET("/promotion/checkProduct/{productId}")
    Call<Promotion> checkProDuctInPromotion(@Path("productId") int id);
    @Multipart
    @POST("/promotion/new")
    Call<Promotion> newPromotion(@Part("name") RequestBody PromoName,
                                 @Part("description") RequestBody Description,
                                 @Part("start") RequestBody StartDate,
                                 @Part("end") RequestBody EndDate,
                                 @Part("discount") RequestBody Discount,
                                 @Part("status") RequestBody Status);

    @PUT("/promotion/update")
    Call<Promotion> updatePromotion(@Query("id") String PromoId,
                                    @Query("name") String PromoName,
                                    @Query("description") String Description,
                                    @Query("start") String StartDate,
                                    @Query("end") String EndDate,
                                    @Query("discount") String Discount,
                                    @Query("status") String Status);

    @DELETE("/promotion/delete/{id}")
    Call<Promotion> DeletePromotion(@Path("id") int id);

}
