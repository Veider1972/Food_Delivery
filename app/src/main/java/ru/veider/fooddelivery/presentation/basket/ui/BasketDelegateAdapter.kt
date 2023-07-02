package ru.veider.fooddelivery.ru.veider.fooddelivery.presentation.basket.ui

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.veider.domain.model.Basket
import ru.veider.domain.model.IBasket
import ru.veider.fooddelivery.R
import ru.veider.fooddelivery.databinding.ItemBasketBinding
import ru.veider.fooddelivery.presentation.basket.ui.BasketAdapter

fun basketDelegateAdapter(onClick: BasketAdapter.OnClick) = adapterDelegateViewBinding<Basket, IBasket, ItemBasketBinding>(
	viewBinding = {layoutInflater, root -> ItemBasketBinding.inflate(layoutInflater, root, false) }
){
	binding.counterPlus.setOnClickListener {
		onClick.increaseCounter(item.id)
	}
	binding.counterMinus.setOnClickListener {
		onClick.decreaseCounter(item.id)
	}

	bind{
		binding.apply {
			productTitle.text = item.name
			productPrice.text = productPrice.context.getString(R.string.product_price, item.price)
			productWeight.text = productWeight.context.getString(R.string.product_weight, item.weight)

			counter.text = item.counter.toString()
			Glide.with(productImage.context).asBitmap().load(item.imageUrl).fitCenter().into(productImage)
		}
	}

}