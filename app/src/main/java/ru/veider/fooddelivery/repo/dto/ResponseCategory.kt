package ru.veider.fooddelivery.repo.dto

import com.google.gson.annotations.SerializedName

data class ResponseCategory(
	@SerializedName("сategories")
	val categories: List<ResponseCategoryItem>
)
