package ru.veider.fooddelivery.presentation.basket.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.veider.core.datatype.ScreenState
import ru.veider.core.datatype.Transport
import ru.veider.domain.model.Basket
import ru.veider.usecases.UseCases
import java.lang.Exception

class BasketViewModel(
	private val useCases: UseCases
) : ViewModel() {

	private var basketData = mutableListOf<Basket>()
	private val _basket = MutableLiveData<ScreenState<List<Basket>>>()
	val basket: LiveData<ScreenState<List<Basket>>> get() = _basket

	private val _paidBasket = MutableLiveData<ScreenState<Boolean>>()

	init {
		loadBasket()
	}

	fun addProduct(product: Basket) {
		viewModelScope.launch {
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
			storeBasket(basketData)
		}
	}

	fun plusProduct(id: Long) {
		viewModelScope.launch {
			try {
				val index = basketData.getIndex(id)
				basketData[index].counter++
				storeBasket(basketData)
			} catch (_: Exception) {
			}
		}
	}

	fun minusProduct(id: Long) {
		viewModelScope.launch {
			try {
				val index = basketData.getIndex(id)
				basketData[index].counter--
				if (basketData[index].counter == 0)
					basketData.removeAt(index)
				storeBasket(basketData)
			} catch (_: Exception) {
			}
		}
	}

	fun payBasket():LiveData<ScreenState<List<Basket>>>{
		_paidBasket.value = ScreenState.Loading()
		viewModelScope.launch {
			when (val result = useCases.payBasket(basketData)){
				is Transport.Success -> {
					basketData.clear()
					storeBasket(basketData)
					_paidBasket.postValue(ScreenState.Success(true))
				}
				is Transport.Error -> {
					_paidBasket.postValue(ScreenState.Error(result.error))
				}
			}
		}
		return basket
	}

	private fun MutableList<Basket>.getIndex(productData: Basket): Int = getIndex(productData.id)

	private fun MutableList<Basket>.getIndex(id: Long): Int {
		for (i in this.indices) {
			if (this[i].id == id)
				return i
		}
		throw ArrayIndexOutOfBoundsException()
	}

	private fun storeBasket(data: List<Basket>) {
		_basket.value = ScreenState.Loading()
		viewModelScope.launch {
			when (val basketList = useCases.storeBasket(data)) {
				is Transport.Success -> {
					_basket.postValue(ScreenState.Success(basketList.data))
				}

				is Transport.Error -> {
					_basket.postValue(ScreenState.Error(basketList.error))
				}
			}
		}
	}

	private fun loadBasket() {
		_basket.value = ScreenState.Loading()
		viewModelScope.launch {
			when (val basketList = useCases.loadBasket()) {
				is Transport.Success -> {
					basketData = basketList.data.toMutableList()
					_basket.postValue(ScreenState.Success(basketData))
				}

				is Transport.Error -> {
					_basket.postValue(ScreenState.Error(basketList.error))
				}
			}
		}
	}
}