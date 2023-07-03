package ru.veider.fooddelivery.presentation.basket.ui

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import ru.veider.domain.model.IBasket
import ru.veider.fooddelivery.ru.veider.fooddelivery.presentation.basket.ui.basketDelegateAdapter

class BasketAdapter(
	increaseCounter: (Long)->Unit,
	decreaseCounter: (Long)->Unit
) : ListDelegationAdapter<List<IBasket>>(basketDelegateAdapter(increaseCounter, decreaseCounter)) {

	init {
		items = emptyList()
	}
}