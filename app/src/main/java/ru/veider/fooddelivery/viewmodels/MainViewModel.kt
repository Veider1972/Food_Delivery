package ru.veider.fooddelivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.veider.fooddelivery.domain.CategoryData
import ru.veider.fooddelivery.domain.ScreenState
import ru.veider.fooddelivery.domain.Transport
import ru.veider.fooddelivery.usecases.UseCases

class MainViewModel(
	private val useCases: UseCases
) : ViewModel() {

	private val _categoryDataList = MutableLiveData<ScreenState<List<CategoryData>>>()
	fun getCategories(): LiveData<ScreenState<List<CategoryData>>> {
		viewModelScope.launch {
			_categoryDataList.postValue(ScreenState.Loading())
			when (val categories = useCases.getCategories()) {
				is Transport.Success -> _categoryDataList.postValue(ScreenState.Success(categories.data))
				is Transport.Error -> _categoryDataList.postValue(ScreenState.Error(categories.error))
			}
		}
		return _categoryDataList
	}
}