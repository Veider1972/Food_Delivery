package ru.veider.data.datasources.product_datasource

import ru.veider.core.datatype.Transport
import ru.veider.data.datasources.product_datasource.api.ProductWebApi
import ru.veider.data.datasources.product_datasource.model.ResponseProducts

class ProductDataSourceImpl(
	private val webApi: ProductWebApi
): ProductDataSource {

	override suspend fun getProducts(): Transport<ResponseProducts> =
		try {
			Transport.Success(webApi.getProductData())
		} catch (e: Exception) {
			Transport.Error(e)
		}
}