package com.example.shopapp.data.service

import com.example.shopapp.data.dto.response.CategoriesResponse
import com.example.shopapp.data.dto.response.ProductResponse
import com.example.shopapp.data.dto.response.TokenResponse
import com.example.shopapp.domain.model.LoginRequest
import com.example.shopapp.domain.model.OrdersRequest
import com.example.shopapp.domain.model.RefreshTokenRequest
import retrofit2.Call
import retrofit2.http.*

interface ShopAPI {

    @POST("api/Auth/Login")
    fun logIn(
        @Body body: LoginRequest,
    ): Call<TokenResponse>

    @POST("/api/Auth/RefreshTokenLogin")
    fun refreshToken(
        @Body body: RefreshTokenRequest
    ): Call<TokenResponse>

    @GET("api/Category/GetAll")
    fun getAllCategories(
        @Header("Authorization") accessToken: String
    ): Call<CategoriesResponse>

    @GET("api/Product/GetRandomProducts")
    fun getRandomProduct(
        @Header("Authorization") accessToken: String
    ): Call<ProductResponse>

    //@GET("api/Product/GetRandomProducts")
    @GET("api/Product/GetAll")
    fun getAllProduct(
        @Header("Authorization") accessToken: String
    ): Call<ProductResponse>

    @POST("api/Order")
    fun requestOrder(
        @Header("Authorization") accessToken: String,
        @Body body: OrdersRequest
    ): Call<Int>
}