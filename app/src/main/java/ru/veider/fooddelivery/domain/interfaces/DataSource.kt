package ru.veider.fooddelivery.domain.interfaces

import ru.veider.fooddelivery.core.datatype.Transport
import ru.veider.fooddelivery.domain.model.Category
import ru.veider.fooddelivery.domain.model.Product

interface DataSource {
	suspend fun getCategories(): Transport<List<Category>>
	suspend fun getDishes(): Transport<List<Product>>
}