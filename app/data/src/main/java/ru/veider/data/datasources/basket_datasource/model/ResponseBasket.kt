package ru.veider.data.datasources.basket_datasource.model

data class ResponseBasket(
	val id: Long,
	val name: String,
	val imageUrl: String,
	val price: Int,
	val weight: Int,
	var counter: Int
)
