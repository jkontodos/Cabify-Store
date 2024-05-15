package com.jkontodos.cabifystore.data.server

import com.jkontodos.cabifystore.data.server.response.ProductListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface StoreService {
    @GET("palcalde/{palcalde}/raw/{raw}/Products.json")
    @Headers("Accept: application/json")
    suspend fun getProducts(
        @Path("palcalde") palcalde: String,
        @Path("raw") raw: String
    ): Response<ProductListResponse>
}