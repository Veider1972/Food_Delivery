package ru.veider.fooddelivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.veider.fooddelivery.usecases.UseCases
import ru.veider.fooddelivery.domain.ScreenState
import ru.veider.fooddelivery.domain.Transport
import ru.veider.fooddelivery.domain.ProductData

class CategoryViewModel(
	private val useCases: UseCases
) : ViewModel() {

	private val _productDataList = MutableLiveData<ScreenState<List<ProductData>>>()
	val dishesDataList get() = _productDataList
	fun getDishes(): LiveData<ScreenState<List<ProductData>>> {
		viewModelScope.launch {
			_productDataList.postValue(ScreenState.Loading())
			when (val categories = useCases.getDishes()) {
				is Transport.Success -> _productDataList.postValue(ScreenState.Success(categories.data))
				is Transport.Error -> _productDataList.postValue(ScreenState.Error(categories.error))
			}
		}
		return _productDataList
	}

	private val _tagList = MutableLiveData<ScreenState<List<String>>>()
	fun getTags(): LiveData<ScreenState<List<String>>> {
		viewModelScope.launch {
			_tagList.postValue(ScreenState.Loading())
			when (val categories = useCases.getDishes()) {
				is Transport.Success -> {
					val array = mutableSetOf<String>()
					categories.data.forEach {
						it.tags.forEach { tag->
							array.add(tag)
						}
					}
					_tagList.postValue(ScreenState.Success(array.toList()))
				}
				is Transport.Error -> _tagList.postValue(ScreenState.Error(categories.error))
			}
		}
		return _tagList
	}

}