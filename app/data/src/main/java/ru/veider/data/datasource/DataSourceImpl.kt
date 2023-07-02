package ru.veider.data.datasource

import ru.veider.core.datatype.Transport
import ru.veider.data.datasource.api.WebApi
import ru.veider.data.repository.mapper.toCategory
import ru.veider.data.repository.mapper.toDishes
import ru.veider.fooddelivery.domain.model.Category
import ru.veider.fooddelivery.domain.model.Product

class DataSourceImpl(
	private val webApi: WebApi
): DataSource {
	override suspend fun getCategories(): Transport<List<Category>> =
		try {
			Transport.Success(webApi.getCategoryData().categories.map { it.toCategory() })
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun getDishes(): Transport<List<Product>> =
		try {
			Transport.Success(webApi.getDishesData().products.map { it.toDishes() })
		} catch (e: Exception) {
			Transport.Error(e)
		}
}