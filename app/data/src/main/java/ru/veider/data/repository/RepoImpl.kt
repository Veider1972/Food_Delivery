package ru.veider.data.repository

import ru.veider.core.datatype.Transport
import ru.veider.data.datasource.DataSource
import ru.veider.fooddelivery.domain.model.Category
import ru.veider.fooddelivery.domain.model.Product

class RepoImpl(
	private val dataSource: DataSource
): Repo {
	override suspend fun getCategories(): Transport<List<Category>> =
		try {
			when (val categoryList = dataSource.getCategories()) {
				is Transport.Success -> Transport.Success(categoryList.data)
				is Transport.Error -> Transport.Error(categoryList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun getDishes(): Transport<List<Product>> =
		try {
			when (val dishesList = dataSource.getDishes()) {
				is Transport.Success -> Transport.Success(dishesList.data)
				is Transport.Error -> Transport.Error(dishesList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}
}