package ru.veider.fooddelivery.repo.dto

import com.google.gson.annotations.SerializedName
import ru.veider.fooddelivery.domain.ProductData

data class ResponseProductsItem(
	@SerializedName("id")
	val id: Long,
	@SerializedName("name")
	val name: String,
	@SerializedName("price")
	val price: Int,
	@SerializedName("weight")
	val weight: Int,
	@SerializedName("description")
	val description: String,
	@SerializedName("image_url")
	val imageUrl: String,
	@SerializedName("tegs")
	val tags: List<String>
)

fun ResponseProductsItem.toDishes() =
	ProductData(
		id = id,
		name = name,
		price = price,
		weight = weight,
		description = description,
		imageUrl = imageUrl,
		tags = tags
	)
