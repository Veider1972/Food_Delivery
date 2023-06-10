package ru.veider.fooddelivery.presentation.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.veider.fooddelivery.databinding.ItemMainBinding
import ru.veider.fooddelivery.domain.CategoryData

class MainAdapter(
	private val onClick: OnClick
) : ListAdapter<CategoryData, MainAdapter.MainHolder>(DiffCallback) {

	interface OnClick {
		fun showCategoryInfo(category: CategoryData)
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
		fun bind(categoryData: CategoryData, onClick: OnClick) {
			binding.apply {
				name.text = categoryData.name
				container.setOnClickListener {
					onClick.showCategoryInfo(categoryData)
				}
				Glide.with(image.context).asBitmap().load(categoryData.imageUrl).fitCenter().into(image)
			}
		}
	}

	companion object {
		private val DiffCallback = object : DiffUtil.ItemCallback<CategoryData>() {
			override fun areItemsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean =
				(oldItem.id == newItem.id)

			override fun areContentsTheSame(oldItem: CategoryData, newItem: CategoryData): Boolean =
				oldItem.name == newItem.name &&
						oldItem.id == newItem.id &&
						oldItem.imageUrl == newItem.imageUrl

		}
	}
}