package ru.veider.fooddelivery.presentation.main.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import ru.veider.fooddelivery.R
import ru.veider.fooddelivery.databinding.FragmentMainBinding
import ru.veider.fooddelivery.domain.model.Category
import ru.veider.core.datatype.ScreenState
import ru.veider.fooddelivery.presentation.category.ui.CategoryFragment
import ru.veider.fooddelivery.presentation.account.vm.AccountViewModel
import ru.veider.fooddelivery.presentation.main.vm.MainViewModel

class MainFragment : Fragment(R.layout.fragment_main) {

	private val binding by viewBinding(FragmentMainBinding::bind)

	private val mainViewModel: MainViewModel by inject()
	private val accountViewModel: AccountViewModel by inject()

	private lateinit var adapter: MainAdapter

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		workToAdapter()
		workToCategory()
		workToAccount()
	}

	override fun onPause() {
		accountShimmer(false)
		categoryShimmer(false)
		super.onPause()
	}

	private fun workToAdapter() {
		adapter = MainAdapter(
			object : MainAdapter.OnClick {
				override fun showCategoryInfo(category: Category) {
					findNavController().navigate(
						R.id.action_mainFragment_to_categoryFragment,
						bundleOf(
							CategoryFragment.CATEGORY_NAME to category.name
						)
					)
				}
			})
		binding.categoryList.adapter = adapter
	}

	private fun workToCategory() {
		mainViewModel.getCategories().observe(viewLifecycleOwner) {
			when (it) {
				is ScreenState.Loading -> {
					categoryShimmer(true)
				}

				is ScreenState.Success -> {
					categoryShimmer(false)
					adapter.submitList(it.data)
				}

				is ScreenState.Error -> {
					categoryShimmer(false)
					it.error.printStackTrace()
					Toast.makeText(requireContext(), getString(R.string.load_category_error), Toast.LENGTH_LONG).show()
				}
			}
		}
	}

	private fun workToAccount() {
		accountViewModel.getAccountData().observe(viewLifecycleOwner) {
			when (it) {
				is ScreenState.Loading -> {
					accountShimmer(true)
				}

				is ScreenState.Success -> {
					accountShimmer(false)
					with(binding) {
						locationTitle.text = it.data.location
						locationDate.text = it.data.date
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

	private fun accountShimmer(state: Boolean) {
		with(binding) {
			accountLoading.isVisible = state
			account.isVisible = !state
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

	private fun categoryShimmer(state: Boolean) {
		with(binding) {
			categoryLoading.isVisible = state
			categoryList.isVisible = !state
			if (state) {
				if (!categoryShimmer.isShimmerStarted) {
					categoryShimmer.startShimmer()
				}
			} else {
				if (categoryShimmer.isShimmerStarted) {
					categoryShimmer.stopShimmer()
				}
			}
		}
	}
}