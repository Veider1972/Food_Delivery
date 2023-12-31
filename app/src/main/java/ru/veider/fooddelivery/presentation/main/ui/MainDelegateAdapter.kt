package ru.veider.fooddelivery.ru.veider.fooddelivery.presentation.main.ui

import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.veider.domain.model.ICategory
import ru.veider.fooddelivery.databinding.ItemMainBinding
import ru.veider.domain.model.Category

fun mainDelegateAdapter(showCategoryInfo: (Category)->Unit) = adapterDelegateViewBinding<Category, ICategory, ItemMainBinding>(
	viewBinding = {layoutInflater, root -> ItemMainBinding.inflate(layoutInflater, root, false) }
){
	binding.container.setOnClickListener {
		showCategoryInfo(item)
	}
	bind{
		binding.apply {
			name.text = item.name
			Glide.with(image.context).asBitmap().load(item.imageUrl).fitCenter().into(image)
		}
	}

}