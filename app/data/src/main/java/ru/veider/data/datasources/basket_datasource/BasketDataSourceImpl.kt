package ru.veider.data.datasources.basket_datasource

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.veider.core.datatype.Transport
import ru.veider.data.datasources.basket_datasource.model.ResponseBasket
import java.lang.Exception

class BasketDataSourceImpl(
	private val gson: Gson,
	private val prefs: SharedPreferences
) : BasketDataSource {



	override suspend fun loadBasket(): Transport<List<ResponseBasket>> =
		try {
			val list = prefs.getString(key, null)
			Transport.Success(gson.fromJson(list, Array<ResponseBasket>::class.java).toList())
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun storeBasket(basket: List<ResponseBasket>): Transport<List<ResponseBasket>> =
		try {
			val list = gson.toJson(basket)
			prefs.edit().putString(key, list).apply()
			Transport.Success(basket)
		} catch (e: Exception) {
			Transport.Error(e)
		}

	override suspend fun payBasket(basket: List<ResponseBasket>): Transport<Boolean> {
		return Transport.Success(true)
	}

	companion object{
		private const val key = "basket"
	}

}