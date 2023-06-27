package ru.veider.data.repository.mapper

import ru.veider.data.datasource.model.ResponseCategoryItem
import ru.veider.data.datasource.model.ResponseProductsItem
import ru.veider.fooddelivery.domain.model.Category
import ru.veider.fooddelivery.domain.model.Product

fun ResponseProductsItem.toDishes() =
	Product(
		id = id,
		name = name,
		price = price,
		weight = weight,
		description = description,
		imageUrl = imageUrl,
		tags = tags
	)

fun ResponseCategoryItem.toCategory() =
	Category(
		id = id,
		name = name,
		imageUrl = imageUrl
	)