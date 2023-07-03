package ru.veider.data.datasources.product_datasource

import ru.veider.core.datatype.Transport
import ru.veider.data.datasources.product_datasource.model.ResponseProducts

interface ProductDataSource {
	suspend fun getProducts(): Transport<ResponseProducts>
}