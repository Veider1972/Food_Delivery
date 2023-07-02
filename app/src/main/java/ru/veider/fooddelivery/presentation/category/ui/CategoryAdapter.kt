package ru.veider.fooddelivery.presentation.category.ui

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import ru.veider.domain.model.IProduct
import ru.veider.domain.model.Product
import ru.veider.fooddelivery.ru.veider.fooddelivery.presentation.category.ui.categoryDelegateAdapter

class CategoryAdapter(onClick: OnClick) : ListDelegationAdapter<List<IProduct>>(categoryDelegateAdapter(onClick)) {
	interface OnClick {
		fun showDishInfo(product: Product)
	}
	init {
		items = emptyList()
	}
}