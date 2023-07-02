package ru.veider.usecases

import ru.veider.domain.model.Category
import ru.veider.core.datatype.Transport
import ru.veider.domain.model.Product

interface UseCases{
	suspend fun getCategories(): Transport<List<Category>>
	suspend fun getDishes(): Transport<List<Product>>
}