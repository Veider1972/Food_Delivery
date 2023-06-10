package ru.veider.fooddelivery.repo.dto

import com.google.gson.annotations.SerializedName
import ru.veider.fooddelivery.domain.CategoryData

data class ResponseCategoryItem(
	@SerializedName("id")
	val id: Long,
	@SerializedName("name")
	val name: String,
	@SerializedName("image_url")
	val imageUrl: String
)

fun ResponseCategoryItem.toCategory() =
	CategoryData(
		id = id,
		name = name,
		imageUrl = imageUrl
	)
