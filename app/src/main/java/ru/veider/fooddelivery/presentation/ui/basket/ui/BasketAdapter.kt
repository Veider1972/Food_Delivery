package ru.veider.fooddelivery.presentation.ui.basket.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.veider.fooddelivery.R
import ru.veider.fooddelivery.databinding.ItemBasketBinding
import ru.veider.fooddelivery.domain.model.Basket

class BasketAdapter(
	private val onClick: OnClick
) : ListAdapter<Basket, BasketAdapter.BasketHolder>(DiffCallback) {

	interface OnClick {
		fun increaseCounter(id: Long)
		fun decreaseCounter(id: Long)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketHolder =
		BasketHolder(
			ItemBasketBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)

	override fun onBindViewHolder(holder: BasketHolder, position: Int) {
		holder.bind(getItem(position))
	}

	inner class BasketHolder(private val binding: ItemBasketBinding) : RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("CheckResult")
		fun bind(product: Basket) {
			binding.apply {
				productTitle.text = product.name
				productPrice.text = productPrice.context.getString(R.string.product_price, product.price)
				productWeight.text = productWeight.context.getString(R.string.product_weight, product.weight)
				counterPlus.setOnClickListener {
					onClick.increaseCounter(product.id)
				}
				counterMinus.setOnClickListener {
					onClick.decreaseCounter(product.id)
				}
				counter.text = product.counter.toString()
				Glide.with(productImage.context).asBitmap().load(product.imageUrl).fitCenter().into(productImage)
			}
		}
	}

	companion object {
		private val DiffCallback = object : DiffUtil.ItemCallback<Basket>() {
			override fun areItemsTheSame(oldItem: Basket, newItem: Basket): Boolean =
				oldItem.id == newItem.id

			override fun areContentsTheSame(oldItem: Basket, newItem: Basket): Boolean =
				oldItem.id == newItem.id &&
						oldItem.counter == newItem.counter

		}
	}
}