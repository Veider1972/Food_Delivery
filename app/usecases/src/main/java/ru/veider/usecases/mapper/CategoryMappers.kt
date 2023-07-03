package ru.veider.usecases.mapper

import ru.veider.data.datasources.category_datasource.model.ResponseCategoryItem
import ru.veider.domain.model.Category

fun ResponseCategoryItem.toCategory() =
	Category(
		id = id,
		name = name,
		imageUrl = imageUrl
	)