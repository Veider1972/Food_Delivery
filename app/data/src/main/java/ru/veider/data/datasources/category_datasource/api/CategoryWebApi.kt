package ru.veider.data.datasources.category_datasource.api

import retrofit2.http.GET
import ru.veider.data.datasources.category_datasource.model.ResponseCategory

interface CategoryWebApi {
    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategoryData(): ResponseCategory
}