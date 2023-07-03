package ru.veider.usecases

import ru.veider.core.datatype.Transport
import ru.veider.data.repository.Repo
import ru.veider.domain.model.Basket
import ru.veider.domain.model.Category
import ru.veider.domain.model.Product
import ru.veider.usecases.mapper.toBasket
import ru.veider.usecases.mapper.toCategory
import ru.veider.usecases.mapper.toProducts
import ru.veider.usecases.mapper.toResponseBasket

class UseCasesImpl(
	private val repo: Repo
) : UseCases {
	override suspend fun getCategories(): Transport<List<Category>> =
		try {
			when (val categoryList = repo.getCategories()) {
				is Transport.Success -> Transport.Success(categoryList.data.categories.map { it.toCategory() })
				is Transport.Error -> Transport.Error(categoryList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun getProducts(): Transport<List<Product>> =
		try {
			when (val dishesList = repo.getProducts()) {
				is Transport.Success -> Transport.Success(dishesList.data.products.map { it.toProducts() })
				is Transport.Error -> Transport.Error(dishesList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun loadBasket(): Transport<List<Basket>> =
		try {
			when (val basketList = repo.loadBasket()) {
				is Transport.Success -> Transport.Success(basketList.data.map { it.toBasket() })
				is Transport.Error -> Transport.Error(basketList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun storeBasket(basket: List<Basket>): Transport<List<Basket>> =
		try {
			when (val basketList = repo.storeBasket(basket.map { it.toResponseBasket() })) {
				is Transport.Success -> Transport.Success(basketList.data.map { it.toBasket() })
				is Transport.Error -> Transport.Error(basketList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun payBasket(basket: List<Basket>): Transport<Boolean> =
		try {
			when (val basketList = repo.payBasket(basket.map { it.toResponseBasket() })) {
				is Transport.Success -> Transport.Success(basketList.data)
				is Transport.Error -> Transport.Error(basketList.error)
			}
		} catch (e: Exception) {
			Transport.Error(e)
		}
}