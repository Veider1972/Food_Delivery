package ru.veider.fooddelivery.data.datasource.model

import com.google.gson.annotations.SerializedName

data class ResponseCategory(
	@SerializedName("сategories")
	val categories: List<ResponseCategoryItem>
)
