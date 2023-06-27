package ru.veider.fooddelivery.presentation.ui.main.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.veider.fooddelivery.databinding.ItemMainBinding
import ru.veider.fooddelivery.domain.model.Category

class MainAdapter(
	private val onClick: OnClick
) : ListAdapter<Category, MainAdapter.MainHolder>(DiffCallback) {

	interface OnClick {
		fun showCategoryInfo(category: Category)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder =
		MainHolder(
			ItemMainBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)

	override fun onBindViewHolder(holder: MainHolder, position: Int) {
		holder.bind(getItem(position), onClick)
	}

	inner class MainHolder(private val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root) {
		@SuppressLint("CheckResult")
		fun bind(category: Category, onClick: OnClick) {
			binding.apply {
				name.text = category.name
				container.setOnClickListener {
					onClick.showCategoryInfo(category)
				}
				Glide.with(image.context).asBitmap().load(category.imageUrl).fitCenter().into(image)
			}
		}
	}

	companion object {
		private val DiffCallback = object : DiffUtil.ItemCallback<Category>() {
			override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
				(oldItem.id == newItem.id)

			override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
				oldItem.name == newItem.name &&
						oldItem.id == newItem.id &&
						oldItem.imageUrl == newItem.imageUrl

		}
	}
}