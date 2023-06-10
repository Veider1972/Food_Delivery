package ru.veider.fooddelivery.repo

import ru.veider.fooddelivery.domain.Transport
import ru.veider.fooddelivery.repo.dto.ResponseCategory
import ru.veider.fooddelivery.repo.dto.ResponseProducts

class WebSource(
	private val webApi: WebApi
) {
	suspend fun getCategories(): Transport<ResponseCategory> =
		try {
			Transport.Success(webApi.getCategoryData())
		} catch (e: Exception) {
			Transport.Error(e)
		}

	suspend fun getDishes(): Transport<ResponseProducts> =
		try {
			Transport.Success(webApi.getDishesData())
		} catch (e: Exception) {
			Transport.Error(e)
		}
}