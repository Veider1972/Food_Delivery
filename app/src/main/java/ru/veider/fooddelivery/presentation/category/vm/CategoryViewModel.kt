package ru.veider.fooddelivery.presentation.category.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.veider.usecases.UseCases
import ru.veider.fooddelivery.domain.model.Product

class CategoryViewModel(
	private val useCases: UseCases
) : ViewModel() {

	private val _productList = MutableLiveData<ru.veider.core.datatype.ScreenState<List<Product>>>()
	val dishesDataList get() = _productList
	fun getDishes(): LiveData<ru.veider.core.datatype.ScreenState<List<Product>>> {
		viewModelScope.launch {
			_productList.postValue(ru.veider.core.datatype.ScreenState.Loading())
			when (val categories = useCases.getDishes()) {
				is ru.veider.core.datatype.Transport.Success -> _productList.postValue(ru.veider.core.datatype.ScreenState.Success(categories.data))
				is ru.veider.core.datatype.Transport.Error -> _productList.postValue(ru.veider.core.datatype.ScreenState.Error(categories.error))
			}
		}
		return _productList
	}

	private val _tagList = MutableLiveData<ru.veider.core.datatype.ScreenState<List<String>>>()
	fun getTags(): LiveData<ru.veider.core.datatype.ScreenState<List<String>>> {
		viewModelScope.launch {
			_tagList.postValue(ru.veider.core.datatype.ScreenState.Loading())
			when (val categories = useCases.getDishes()) {
				is ru.veider.core.datatype.Transport.Success -> {
					val array = mutableSetOf<String>()
					categories.data.forEach {
						it.tags.forEach { tag->
							array.add(tag)
						}
					}
					_tagList.postValue(ru.veider.core.datatype.ScreenState.Success(array.toList()))
				}
				is ru.veider.core.datatype.Transport.Error -> _tagList.postValue(ru.veider.core.datatype.ScreenState.Error(categories.error))
			}
		}
		return _tagList
	}

}