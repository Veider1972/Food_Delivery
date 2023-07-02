package ru.veider.fooddelivery.presentation.basket.ui

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import ru.veider.domain.model.IBasket
import ru.veider.fooddelivery.ru.veider.fooddelivery.presentation.basket.ui.basketDelegateAdapter

class BasketAdapter(
	onClick: OnClick
) : ListDelegationAdapter<List<IBasket>>(basketDelegateAdapter(onClick)) {

	interface OnClick {
		fun increaseCounter(id: Long)
		fun decreaseCounter(id: Long)
	}

	init {
		items = emptyList()
	}
}