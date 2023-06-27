package ru.veider.data.datasource.model

import com.google.gson.annotations.SerializedName

data class ResponseProducts(
	@SerializedName("dishes")
	val products: List<ResponseProductsItem>
)
