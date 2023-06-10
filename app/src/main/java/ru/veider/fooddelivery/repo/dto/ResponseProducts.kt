package ru.veider.fooddelivery.repo.dto

import com.google.gson.annotations.SerializedName

data class ResponseProducts(
	@SerializedName("dishes")
	val products: List<ResponseProductsItem>
)
