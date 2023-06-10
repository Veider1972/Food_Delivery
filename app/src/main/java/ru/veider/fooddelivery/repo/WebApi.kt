package ru.veider.fooddelivery.repo

import retrofit2.http.GET
import ru.veider.fooddelivery.repo.dto.ResponseCategory
import ru.veider.fooddelivery.repo.dto.ResponseProducts

interface WebApi {
    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategoryData(): ResponseCategory

    @GET("aba7ecaa-0a70-453b-b62d-0e326c859b3b")
    suspend fun getDishesData(): ResponseProducts
}