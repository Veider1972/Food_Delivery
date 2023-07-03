package ru.veider.usecases.mapper

import ru.veider.data.datasources.product_datasource.model.ResponseProductsItem
import ru.veider.domain.model.Product

fun ResponseProductsItem.toProducts() =
	Product(
		id = id,
		name = name,
		price = price,
		weight = weight,
		description = description,
		imageUrl = imageUrl,
		tags = tags
	)