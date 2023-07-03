package ru.veider.fooddelivery.ru.veider.fooddelivery.presentation.category.ui

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.veider.domain.model.IProduct
import ru.veider.domain.model.Product
import ru.veider.fooddelivery.databinding.ItemCategoryBinding

fun categoryDelegateAdapter(showDishInfo: (Product)->Unit) = adapterDelegateViewBinding<Product, IProduct, ItemCategoryBinding>(
	viewBinding = {layoutInflater, root -> ItemCategoryBinding.inflate(layoutInflater, root, false) }
){
	binding.container.setOnClickListener {
		showDishInfo(item)
	}
	bind{
		binding.apply {
			dishName.text = item.name
			container.setOnClickListener {
				showDishInfo(item)
			}
			Glide.with(dishImage.context).asBitmap().load(item.imageUrl).fitCenter().into(dishImage)
		}
	}

}