package ru.veider.fooddelivery.data.datasource.model

import com.google.gson.annotations.SerializedName

data class ResponseCategoryItem(
	@SerializedName("id")
	val id: Long,
	@SerializedName("name")
	val name: String,
	@SerializedName("image_url")
	val imageUrl: String
)


