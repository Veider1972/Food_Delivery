package ru.veider.fooddelivery.usecases

import ru.veider.fooddelivery.domain.CategoryData
import ru.veider.fooddelivery.domain.Transport
import ru.veider.fooddelivery.repo.WebSource
import ru.veider.fooddelivery.repo.dto.toCategory
import ru.veider.fooddelivery.repo.dto.toDishes
import ru.veider.fooddelivery.domain.ProductData

class UseCasesImpl(
	private val webSource: WebSource
) : UseCases {
	override suspend fun getCategories(): Transport<List<CategoryData>> =
		try {
			when (val categoryList = webSource.getCategories()) {
				is Transport.Success -> Transport.Success(categoryList.data.categories.map { it.toCategory() })
				is Transport.Error -> Transport.Error(categoryList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun getDishes(): Transport<List<ProductData>> =
		try {
			when (val dishesList = webSource.getDishes()) {
				is Transport.Success -> Transport.Success(dishesList.data.products.map { it.toDishes() })
				is Transport.Error -> Transport.Error(dishesList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}
}