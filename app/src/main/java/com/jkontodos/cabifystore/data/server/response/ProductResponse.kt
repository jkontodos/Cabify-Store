package com.jkontodos.cabifystore.data.server.response

import com.google.gson.annotations.SerializedName

data class ProductListResponse (
    @SerializedName("products")
    val products: List<ProductResponse>
)

data class ProductResponse (
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Double
)
