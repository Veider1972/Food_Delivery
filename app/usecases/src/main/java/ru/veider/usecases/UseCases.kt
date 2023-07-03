package ru.veider.usecases

import ru.veider.domain.model.Category
import ru.veider.core.datatype.Transport
import ru.veider.domain.model.Basket
import ru.veider.domain.model.Product

interface UseCases{
	suspend fun getCategories(): Transport<List<Category>>
	suspend fun getProducts(): Transport<List<Product>>
	suspend fun loadBasket(): Transport<List<Basket>>
	suspend fun storeBasket(basket:List<Basket>): Transport<List<Basket>>
	suspend fun payBasket(basket:List<Basket>): Transport<Boolean>
}