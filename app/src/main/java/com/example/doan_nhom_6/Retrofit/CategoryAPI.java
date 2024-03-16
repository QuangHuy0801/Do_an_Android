package com.example.doan_nhom_6.Retrofit;

import com.example.doan_nhom_6.Model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryAPI {
    RetrofitService retrofitService = new RetrofitService();
    CategoryAPI categoryAPI = retrofitService.getRetrofit().create(CategoryAPI.class);
    @GET("/category")
    Call<List<Category>> GetAllCategories();
}
