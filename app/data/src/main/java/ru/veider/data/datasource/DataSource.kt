package ru.veider.data.datasource

import ru.veider.core.datatype.Transport
import ru.veider.domain.model.Category
import ru.veider.domain.model.Product

interface DataSource {
	suspend fun getCategories(): Transport<List<Category>>
	suspend fun getDishes(): Transport<List<Product>>
}