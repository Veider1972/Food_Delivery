package ru.veider.domain.model

data class Basket(
	val id: Long,
	val name: String,
	val imageUrl: String,
	val price: Int,
	val weight: Int,
	var counter: Int
):IBasket
