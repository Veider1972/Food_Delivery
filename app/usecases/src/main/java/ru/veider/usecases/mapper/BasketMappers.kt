package ru.veider.usecases.mapper

import ru.veider.data.datasources.basket_datasource.model.ResponseBasket
import ru.veider.domain.model.Basket

fun ResponseBasket.toBasket()=
	Basket(
		id = id,
		name = name,
		imageUrl = imageUrl,
		price = price,
		weight = weight,
		counter = counter
	)

fun Basket.toResponseBasket() =
	ResponseBasket(
		id = id,
		name = name,
		imageUrl = imageUrl,
		price = price,
		weight = weight,
		counter = counter
	)