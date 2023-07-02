package ru.veider.fooddelivery.presentation.basket.vm

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import ru.veider.fooddelivery.domain.model.Basket
import java.lang.Exception

class BasketViewModel(
	private val application: Application,
	private val gson: Gson
) : AndroidViewModel(application) {

	private var basketData = mutableListOf<Basket>()
	private val _basket = MutableLiveData<List<Basket>>()
	val basket: LiveData<List<Basket>> get() = _basket

	private val prefs by lazy { application.getSharedPreferences(DELIVERY, Context.MODE_PRIVATE) }

	init {
		loadBasket()
	}

	fun addProduct(product: Basket) {
		try {
			val index = basketData.getIndex(product)
			basketData[index].counter++
		} catch (e: Exception) {
			basketData.add(
				Basket(
					id = product.id,
					name = product.name,
					imageUrl = product.imageUrl,
					price = product.price,
					weight = product.weight,
					counter = 1
				)
			)
		}
		_basket.value = basketData
		storeBasket()
	}

	fun plusProduct(id: Long) {
		try {
			val index = basketData.getIndex(id)
			basketData[index].counter++
		} catch (_: Exception) {
		}
		_basket.value = basketData
		storeBasket()
	}

	fun minusProduct(id: Long) {
		try {
			val index = basketData.getIndex(id)
			basketData[index].counter--
			if (basketData[index].counter == 0)
				basketData.removeAt(index)
		} catch (_: Exception) {
		}
		_basket.value = basketData
		storeBasket()
	}

	fun clearBasket(){
		basketData.clear()
		_basket.value = basketData
		storeBasket()
	}

	private fun MutableList<Basket>.getIndex(productData: Basket): Int = getIndex(productData.id)

	private fun MutableList<Basket>.getIndex(id: Long): Int {
		for (i in this.indices) {
			if (this[i].id == id)
				return i
		}
		throw ArrayIndexOutOfBoundsException()
	}

	private fun storeBasket() {
		prefs?.run {
			viewModelScope.launch {
				val list = gson.toJson(basketData)
				edit().putString(BASKET, list).apply()
			}
		}
	}

	private fun loadBasket() {
		prefs?.run {
			val list = getString(BASKET, null)
			list?.run {
				val basketList = gson.fromJson(list, Array<Basket>::class.java)
				basketData = basketList.toMutableList()
				_basket.value = basketData
			}
		}
	}

	companion object {
		private const val DELIVERY = "deliveryPreferences"
		private const val BASKET = "basket"
	}
}