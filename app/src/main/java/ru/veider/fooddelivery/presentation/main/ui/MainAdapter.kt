package ru.veider.fooddelivery.presentation.main.ui

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import ru.veider.domain.model.Category
import ru.veider.domain.model.ICategory
import ru.veider.fooddelivery.ru.veider.fooddelivery.presentation.main.ui.mainDelegateAdapter

class MainAdapter(onClick: OnClick) : ListDelegationAdapter<List<ICategory>>(mainDelegateAdapter(onClick)) {
	interface OnClick {
		fun showCategoryInfo(category: Category)
	}
	init {
		items = emptyList()
	}
}