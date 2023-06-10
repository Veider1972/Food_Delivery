package ru.veider.fooddelivery.ru.veider.fooddelivery.domain

data class BasketData(
	val id: Long,
	val name: String,
	val imageUrl: String,
	val price: Int,
	val weight: Int,
	var counter: Int
)
