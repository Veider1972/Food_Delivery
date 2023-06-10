package ru.veider.fooddelivery.presentation.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.veider.fooddelivery.databinding.ItemCategoryBinding
import ru.veider.fooddelivery.domain.ProductData

class CategoryAdapter(
	private val onClick: OnClick
) : ListAdapter<ProductData, CategoryAdapter.DishesHolder>(DiffCallback) {

	interface OnClick {
		fun showDishInfo(product: ProductData)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishesHolder =
		DishesHolder(
			ItemCategoryBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)

	override fun onBindViewHolder(holder: DishesHolder, position: Int) {
		holder.bind(getItem(position), onClick)
	}

	inner class DishesHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("CheckResult")
		fun bind(dish: ProductData, onClick: OnClick) {
			binding.apply {
				dishName.text = dish.name
				container.setOnClickListener {
					onClick.showDishInfo(dish)
				}
				Glide.with(dishImage.context).asBitmap().load(dish.imageUrl).fitCenter().into(dishImage)
			}
		}
	}

	companion object {
		private val DiffCallback = object : DiffUtil.ItemCallback<ProductData>() {
			override fun areItemsTheSame(oldItem: ProductData, newItem: ProductData): Boolean =
				(oldItem.id == newItem.id)

			override fun areContentsTheSame(oldItem: ProductData, newItem: ProductData): Boolean =
				oldItem.name == newItem.name &&
						oldItem.id == newItem.id &&
						oldItem.imageUrl == newItem.imageUrl &&
						oldItem.description == newItem.description &&
						oldItem.price == newItem.price &&
						oldItem.weight == newItem.weight &&
						oldItem.tags == newItem.tags

		}
	}
}