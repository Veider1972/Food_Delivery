package ru.veider.data.datasources.product_datasource.api

import retrofit2.http.GET
import ru.veider.data.datasources.product_datasource.model.ResponseProducts

interface ProductWebApi {

    @GET("aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun getProductData(): ResponseProducts
}