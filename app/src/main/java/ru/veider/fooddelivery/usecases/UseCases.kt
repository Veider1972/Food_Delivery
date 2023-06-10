package ru.veider.fooddelivery.usecases

import ru.veider.fooddelivery.domain.CategoryData
import ru.veider.fooddelivery.domain.Transport
import ru.veider.fooddelivery.domain.ProductData

interface UseCases{
	suspend fun getCategories(): Transport<List<CategoryData>>
	suspend fun getDishes(): Transport<List<ProductData>>
}