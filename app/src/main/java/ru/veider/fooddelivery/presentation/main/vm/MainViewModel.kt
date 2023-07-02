package ru.veider.fooddelivery.presentation.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.veider.domain.model.Category
import ru.veider.usecases.UseCases

class MainViewModel(
	private val useCases: UseCases
) : ViewModel() {

	private val _categoryList = MutableLiveData<ru.veider.core.datatype.ScreenState<List<Category>>>()
	fun getCategories(): LiveData<ru.veider.core.datatype.ScreenState<List<Category>>> {
		viewModelScope.launch {
			_categoryList.postValue(ru.veider.core.datatype.ScreenState.Loading())
			when (val categories = useCases.getCategories()) {
				is ru.veider.core.datatype.Transport.Success -> _categoryList.postValue(ru.veider.core.datatype.ScreenState.Success(categories.data))
				is ru.veider.core.datatype.Transport.Error -> _categoryList.postValue(ru.veider.core.datatype.ScreenState.Error(categories.error))
			}
		}
		return _categoryList
	}
}