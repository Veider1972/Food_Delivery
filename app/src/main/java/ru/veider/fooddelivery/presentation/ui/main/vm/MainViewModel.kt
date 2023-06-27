package ru.veider.fooddelivery.presentation.ui.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.veider.fooddelivery.domain.model.Category
import ru.veider.fooddelivery.core.datatype.ScreenState
import ru.veider.fooddelivery.core.datatype.Transport
import ru.veider.fooddelivery.domain.usecases.UseCases

class MainViewModel(
	private val useCases: UseCases
) : ViewModel() {

	private val _categoryList = MutableLiveData<ScreenState<List<Category>>>()
	fun getCategories(): LiveData<ScreenState<List<Category>>> {
		viewModelScope.launch {
			_categoryList.postValue(ScreenState.Loading())
			when (val categories = useCases.getCategories()) {
				is Transport.Success -> _categoryList.postValue(ScreenState.Success(categories.data))
				is Transport.Error -> _categoryList.postValue(ScreenState.Error(categories.error))
			}
		}
		return _categoryList
	}
}