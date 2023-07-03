package ru.veider.data.datasources.category_datasource

import ru.veider.core.datatype.Transport
import ru.veider.data.datasources.category_datasource.api.CategoryWebApi
import ru.veider.data.datasources.category_datasource.model.ResponseCategory

class CategoryDataSourceImpl(
	private val webApi: CategoryWebApi
): CategoryDataSource {
	override suspend fun getCategories(): Transport<ResponseCategory> =
		try {
			Transport.Success(webApi.getCategoryData())
		} catch (e: Exception) {
			Transport.Error(e)
		}
}