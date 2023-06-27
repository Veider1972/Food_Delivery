package ru.veider.fooddelivery.presentation.ui.category.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.veider.fooddelivery.domain.usecases.UseCases
import ru.veider.fooddelivery.core.datatype.ScreenState
import ru.veider.fooddelivery.core.datatype.Transport
import ru.veider.fooddelivery.domain.model.Product

class CategoryViewModel(
	private val useCases: UseCases
) : ViewModel() {

	private val _productList = MutableLiveData<ScreenState<List<Product>>>()
	val dishesDataList get() = _productList
	fun getDishes(): LiveData<ScreenState<List<Product>>> {
		viewModelScope.launch {
			_productList.postValue(ScreenState.Loading())
			when (val categories = useCases.getDishes()) {
				is Transport.Success -> _productList.postValue(ScreenState.Success(categories.data))
				is Transport.Error -> _productList.postValue(ScreenState.Error(categories.error))
			}
		}
		return _productList
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