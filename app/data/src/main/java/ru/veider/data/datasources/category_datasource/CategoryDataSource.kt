package ru.veider.data.datasources.category_datasource

import ru.veider.core.datatype.Transport
import ru.veider.data.datasources.category_datasource.model.ResponseCategory

interface CategoryDataSource {
	suspend fun getCategories(): Transport<ResponseCategory>
}