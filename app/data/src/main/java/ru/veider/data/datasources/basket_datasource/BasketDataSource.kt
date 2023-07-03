package ru.veider.data.datasources.basket_datasource

import ru.veider.core.datatype.Transport
import ru.veider.data.datasources.basket_datasource.model.ResponseBasket

interface BasketDataSource {
	suspend fun loadBasket(): Transport<List<ResponseBasket>>
	suspend fun storeBasket(basket:List<ResponseBasket>): Transport<List<ResponseBasket>>
	suspend fun payBasket(basket:List<ResponseBasket>): Transport<Boolean>
}