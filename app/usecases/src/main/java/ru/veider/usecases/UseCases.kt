package ru.veider.usecases

import ru.veider.fooddelivery.domain.model.Category
import ru.veider.core.datatype.Transport
import ru.veider.fooddelivery.domain.model.Product

interface UseCases{
	suspend fun getCategories(): Transport<List<Category>>
	suspend fun getDishes(): Transport<List<Product>>
}