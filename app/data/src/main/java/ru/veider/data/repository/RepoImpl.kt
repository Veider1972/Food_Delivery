package ru.veider.data.repository

import ru.veider.core.datatype.Transport
import ru.veider.data.datasources.basket_datasource.BasketDataSource
import ru.veider.data.datasources.basket_datasource.model.ResponseBasket
import ru.veider.data.datasources.category_datasource.CategoryDataSource
import ru.veider.data.datasources.category_datasource.model.ResponseCategory
import ru.veider.data.datasources.product_datasource.ProductDataSource
import ru.veider.data.datasources.product_datasource.model.ResponseProducts

class RepoImpl(
	private val categoryDataSource: CategoryDataSource,
	private val productDataSource: ProductDataSource,
	private val basketDataSource: BasketDataSource
) : Repo {
	override suspend fun getCategories(): Transport<ResponseCategory> =
		try {
			categoryDataSource.getCategories()
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun getProducts(): Transport<ResponseProducts> =
		try {
			productDataSource.getProducts()
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun loadBasket(): Transport<List<ResponseBasket>> =
		try {
			basketDataSource.loadBasket()
		} catch (e:java.lang.Exception){
			Transport.Error(e)
		}

	override suspend fun storeBasket(basket: List<ResponseBasket>): Transport<List<ResponseBasket>> =
		try {
			basketDataSource.storeBasket(basket)
		} catch (e:Exception){
			Transport.Error(e)
		}

	override suspend fun payBasket(basket: List<ResponseBasket>): Transport<Boolean> =
		basketDataSource.payBasket(basket)
}