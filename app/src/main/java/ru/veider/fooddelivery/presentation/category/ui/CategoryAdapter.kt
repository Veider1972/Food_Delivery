package ru.veider.fooddelivery.presentation.category.ui

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import ru.veider.domain.model.IProduct
import ru.veider.domain.model.Product
import ru.veider.fooddelivery.ru.veider.fooddelivery.presentation.category.ui.categoryDelegateAdapter

class CategoryAdapter(showDishInfo: (Product)->Unit) : ListDelegationAdapter<List<IProduct>>(categoryDelegateAdapter(showDishInfo)) {
	init {
		items = emptyList()
	}
}