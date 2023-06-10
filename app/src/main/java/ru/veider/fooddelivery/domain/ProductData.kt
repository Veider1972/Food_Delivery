package ru.veider.fooddelivery.domain

data class ProductData(
	val id: Long,
	val name: String,
	val price: Int,
	val weight: Int,
	val description: String,
	val imageUrl: String,
	val tags: List<String>
)
