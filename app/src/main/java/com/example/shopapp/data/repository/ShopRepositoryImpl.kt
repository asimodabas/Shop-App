package com.example.shopapp.data.repository

import com.example.shopapp.common.Resource
import com.example.shopapp.data.dto.Token
import com.example.shopapp.data.dto.response.CategoriesResponse
import com.example.shopapp.data.dto.response.ProductResponse
import com.example.shopapp.data.dto.response.TokenResponse
import com.example.shopapp.data.service.ShopAPI
import com.example.shopapp.domain.model.LoginRequest
import com.example.shopapp.domain.model.OrdersRequest
import com.example.shopapp.domain.model.RefreshTokenRequest
import com.example.shopapp.domain.repository.ShopRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ShopRepositoryImpl @Inject constructor(
    private val service: ShopAPI
) : ShopRepository {

    override fun logIn(
        usernameOrEmail: String, password: String, result: (Resource<Token?>) -> Unit
    ) {
        try {
            service.logIn(LoginRequest(usernameOrEmail, password))
                .enqueue(object : Callback<TokenResponse> {
                    override fun onResponse(
                        call: Call<TokenResponse>, response: Response<TokenResponse>
                    ) {
                        response.body()?.token?.let {
                            result(Resource.Success(response.body()?.token))
                        } ?: kotlin.run {
                            result(Resource.Error("${response.raw().code}: ${response.raw().message}"))
                        }
                    }

                    override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                        result(Resource.Error(t.message.orEmpty()))
                    }
                })

        } catch (e: HttpException) {
            result(Resource.Error(e.message()))
        } catch (e: Exception) {
            result(Resource.Error(e.message.orEmpty()))
        }
    }

    override fun refreshToken(refreshToken: String, result: (Resource<Token?>) -> Unit) {
        try {
            service.refreshToken(RefreshTokenRequest(refreshToken))
                .enqueue(object : Callback<TokenResponse> {
                    override fun onResponse(
                        call: Call<TokenResponse>, response: Response<TokenResponse>
                    ) {
                        response.body()?.token?.let { token ->
                            result(Resource.Success(token))
                        } ?: kotlin.run {
                            result(Resource.Error("${response.raw().code}: ${response.raw().message}"))
                        }
                    }

                    override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                        result(Resource.Error(t.message.orEmpty()))
                    }
                })
        } catch (e: HttpException) {
            result(Resource.Error(e.message()))
        } catch (e: Exception) {
            result(Resource.Error(e.message.orEmpty()))
        }
    }

    override fun getCategories(
        accessToken: String, result: (Resource<CategoriesResponse?>) -> Unit
    ) {
        try {
            service.getAllCategories("Bearer $accessToken")
                .enqueue(object : Callback<CategoriesResponse> {
                    override fun onResponse(
                        call: Call<CategoriesResponse>, response: Response<CategoriesResponse>
                    ) {
                        response.body()?.let { categoriesResponse ->
                            result(Resource.Success(categoriesResponse))
                        } ?: kotlin.run {
                            result(Resource.Error("${response.raw().code}: ${response.raw().message}"))
                        }
                    }

                    override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                        result(Resource.Error(t.message.orEmpty()))
                    }
                })
        } catch (e: HttpException) {
            result(Resource.Error(e.message()))
        } catch (e: Exception) {
            result(Resource.Error(e.message.orEmpty()))
        }
    }

    override fun getRandomProduct(
        accessToken: String, result: (Resource<ProductResponse?>) -> Unit
    ) {
        try {
            service.getRandomProduct("Bearer $accessToken")
                .enqueue(object : Callback<ProductResponse> {
                    override fun onResponse(
                        call: Call<ProductResponse>, response: Response<ProductResponse>
                    ) {
                        response.body()?.let { productResponse ->
                            result(Resource.Success(productResponse))
                        } ?: kotlin.run {
                            result(Resource.Error("${response.raw().code}: ${response.raw().message}"))
                        }
                    }

                    override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                        result(Resource.Error(t.message.orEmpty()))
                    }
                })
        } catch (e: HttpException) {
            result(Resource.Error(e.message()))
        } catch (e: Exception) {
            result(Resource.Error(e.message.orEmpty()))
        }
    }

    override fun getAllProduct(accessToken: String, result: (Resource<ProductResponse?>) -> Unit) {
        try {
            service.getAllProduct("Bearer $accessToken")
                .enqueue(object : Callback<ProductResponse> {
                    override fun onResponse(
                        call: Call<ProductResponse>, response: Response<ProductResponse>
                    ) {
                        response.body()?.let { productResponse ->
                            result(Resource.Success(productResponse))
                        } ?: kotlin.run {
                            result(Resource.Error("${response.raw().code}: ${response.raw().message}"))
                        }
                    }

                    override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                        result(Resource.Error(t.message.orEmpty()))
                    }
                })
        } catch (e: HttpException) {
            result(Resource.Error(e.message()))
        } catch (e: Exception) {
            result(Resource.Error(e.message.orEmpty()))
        }
    }

    override fun sendAllProduct(
        accessToken: String, body: OrdersRequest, result: (Resource<Int?>) -> Unit
    ) {
        try {
            service.requestOrder("Bearer $accessToken", body).enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    response.body()?.let { requestOrderResponse ->
                        result(Resource.Success(requestOrderResponse))
                    } ?: kotlin.run {
                        result(Resource.Error("${response.raw().code}: ${response.raw().message}"))
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    result(Resource.Error(t.message.orEmpty()))
                }
            })
        } catch (e: HttpException) {
            result(Resource.Error(e.message()))
        } catch (e: Exception) {
            result(Resource.Error(e.message.orEmpty()))
        }
    }
}