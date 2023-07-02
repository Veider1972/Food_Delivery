package ru.veider.data.repository

import ru.veider.core.datatype.Transport
import ru.veider.domain.model.Category
import ru.veider.domain.model.Product

interface Repo {
	suspend fun getCategories(): Transport<List<Category>>
	suspend fun getDishes(): Transport<List<Product>>
}