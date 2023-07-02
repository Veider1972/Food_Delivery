package ru.veider.usecases

import ru.veider.core.datatype.Transport
import ru.veider.data.repository.Repo
import ru.veider.domain.model.Category
import ru.veider.domain.model.Product

class UseCasesImpl(
	private val repo: Repo
) : UseCases {
	override suspend fun getCategories(): Transport<List<Category>> =
		try {
			when (val categoryList = repo.getCategories()) {
				is Transport.Success -> Transport.Success(categoryList.data)
				is Transport.Error -> Transport.Error(categoryList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun getDishes(): Transport<List<Product>> =
		try {
			when (val dishesList = repo.getDishes()) {
				is Transport.Success -> Transport.Success(dishesList.data)
				is Transport.Error -> Transport.Error(dishesList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}
}