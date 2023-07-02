package ru.veider.fooddelivery.presentation.category.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import ru.veider.fooddelivery.R
import ru.veider.fooddelivery.databinding.CustomTabBinding
import ru.veider.fooddelivery.databinding.FragmentCategoryBinding
import ru.veider.fooddelivery.domain.model.Product
import ru.veider.core.datatype.ScreenState
import ru.veider.fooddelivery.presentation.product.ProductFragment
import ru.veider.fooddelivery.presentation.account.vm.AccountViewModel
import ru.veider.fooddelivery.presentation.category.vm.CategoryViewModel

class CategoryFragment : Fragment(R.layout.fragment_category) {

	private val binding by viewBinding(FragmentCategoryBinding::bind)
	private lateinit var adapter: CategoryAdapter

	private val categoryViewModel: CategoryViewModel by inject()
	private val accountViewModel: AccountViewModel by inject()

	private val categoryName by lazy {
		arguments?.getString(CATEGORY_NAME)
	}

	private var selectedTag: String? = null

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		workToToolbar()
		workToAdapter()
		workToTabs()
		workToDishes()
		workToAccount()

		savedInstanceState?.getString(STORED_TAG)?.run {
			selectedTag = this
		}
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		selectedTag?.run {
			outState.putString(STORED_TAG,this)
		}
	}

	private fun workToToolbar() {
		binding.backButton.setOnClickListener {
			findNavController().navigateUp()
		}
	}

	private fun workToAdapter() {
		adapter = CategoryAdapter(
			object : CategoryAdapter.OnClick {
				override fun showDishInfo(product: Product) {
					findNavController().navigate(
						R.id.action_categoryFragment_to_productFragment,
						bundleOf(
							ProductFragment.ID to product.id,
							ProductFragment.IMAGE to product.imageUrl,
							ProductFragment.TITLE to product.name,
							ProductFragment.PRICE to product.price,
							ProductFragment.WEIGHT to product.weight,
							ProductFragment.DESC to product.description
						)
					)
				}
			})

		binding.dishesList.adapter = adapter
	}

	private fun workToTabs() {
		categoryViewModel.getTags().observe(viewLifecycleOwner) {
			when (it) {
				is ScreenState.Loading -> {
					tabsShimmer(true)
				}

				is ScreenState.Success -> {
					binding.radioGroup.removeAllViews()
					for (i in it.data.indices) {
						addButton(
							text = it.data[i],
							isChecked = i == 0
						)
					}
					selectedTag?.run {
						clickButtonBySelectedTag()
					}
					tabsShimmer(false)
				}

				is ScreenState.Error -> {
					Toast.makeText(requireContext(), getString(R.string.load_tags_error), Toast.LENGTH_LONG).show()
					tabsShimmer(false)
				}
			}
		}
	}

	private fun clickButtonBySelectedTag(){
		binding.radioGroup.children.forEach { buttonContainer->
			buttonContainer.findViewById<RadioButton>(R.id.button)?.run {
				if (text == selectedTag){
					performClick()
				}
			}
		}
	}

	private fun addButton(text:String, isChecked:Boolean){
		val button = CustomTabBinding.inflate(LayoutInflater.from(requireContext())).apply {
			button.text = text
			button.check(isChecked)
			button.setOnClickListener {
				selectedTag = text
				binding.radioGroup.children.forEach { radioButton ->
					radioButton.findViewById<RadioButton>(R.id.button)?.run {
						check(this.text == text)
					}
				}
				filterDishesByTag(text)
			}
		}
		binding.radioGroup.addView(button.root)
	}

	private fun RadioButton.check(state: Boolean) {
		isChecked = state
		if (isChecked)
			setTextAppearance(R.style.TabItemNameSelected)
		else
			setTextAppearance(R.style.TabItemNameUnselected)
	}

	private fun workToDishes() {
		categoryViewModel.getDishes().observe(viewLifecycleOwner) {
			when (it) {
				is ScreenState.Loading -> {
					dishesShimmer(true)
				}

				is ScreenState.Success -> {
					dishesShimmer(false)
					val tag = selectedTag
					if (tag != null){
						filterDishesByTag(tag)
					} else {
						adapter.submitList(it.data)
					}
				}

				is ScreenState.Error -> {
					dishesShimmer(false)
					it.error.printStackTrace()
					Toast.makeText(requireContext(), getString(R.string.load_dishes_error), Toast.LENGTH_LONG).show()
				}
			}
		}
	}

	private fun filterDishesByTag(tag: String) {
		if (categoryViewModel.dishesDataList.value is ScreenState.Success) {
			val list = (categoryViewModel.dishesDataList.value as ScreenState.Success).data
			val filteredList = list.filter { tag in it.tags }
			adapter.submitList(filteredList)
			if (filteredList.isNotEmpty()) {
				binding.dishesList.post {
					binding.dishesList.smoothScrollToPosition(0)
				}
			}
		}
	}

	private fun workToAccount() {
		categoryName?.run {
			binding.categoryTitle.text = this
		}
		accountViewModel.getAccountData().observe(viewLifecycleOwner) {
			when (it) {
				is ScreenState.Loading -> {
					accountShimmer(true)
				}

				is ScreenState.Success -> {
					accountShimmer(false)
					with(binding) {
						val decodedString: ByteArray = Base64.decode(it.data.imageString, Base64.DEFAULT)
						userImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size))
					}
				}

				is ScreenState.Error -> {
					accountShimmer(false)
					it.error.printStackTrace()
					Toast.makeText(requireContext(), getString(R.string.load_category_error), Toast.LENGTH_LONG).show()
				}
			}
		}
	}

	private fun tabsShimmer(state: Boolean) {
		with(binding) {
			tabsLoading.isVisible = state
			tabLayout.isVisible = !state
			if (state) {
				if (!tabsShimmer.isShimmerStarted) {
					tabsShimmer.startShimmer()
				}
			} else {
				if (tabsShimmer.isShimmerStarted) {
					tabsShimmer.stopShimmer()
				}
			}
		}
	}

	private fun dishesShimmer(state: Boolean) {
		with(binding) {
			dishesLoading.isVisible = state
			dishesList.isVisible = !state
			if (state) {
				if (!dishesShimmer.isShimmerStarted) {
					dishesShimmer.startShimmer()
				}
			} else {
				if (dishesShimmer.isShimmerStarted) {
					dishesShimmer.stopShimmer()
				}
			}
		}
	}

	private fun accountShimmer(state: Boolean) {
		with(binding) {
			accountLoading.isVisible = state
			accountContainer.isVisible = !state
			if (state) {
				if (!accountShimmer.isShimmerStarted) {
					accountShimmer.startShimmer()
				}
			} else {
				if (accountShimmer.isShimmerStarted) {
					accountShimmer.stopShimmer()
				}
			}
		}
	}

	companion object {
		const val CATEGORY_NAME = "categoryName"
		const val STORED_TAG = "storedTag"
	}
}