package ru.veider.fooddelivery.presentation.product.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject
import ru.veider.fooddelivery.R
import ru.veider.fooddelivery.databinding.FragmentProductBinding
import ru.veider.domain.model.Basket
import ru.veider.fooddelivery.presentation.basket.vm.BasketViewModel

class ProductFragment : DialogFragment(R.layout.fragment_product) {

	private lateinit var binding: FragmentProductBinding

	private val basketViewModel: BasketViewModel by inject()

	private val id by lazy { arguments?.getLong(ID) }
	private val imageUrl by lazy { arguments?.getString(IMAGE) }
	private val title by lazy { arguments?.getString(TITLE) }
	private val price by lazy { arguments?.getInt(PRICE) }
	private val weight by lazy { arguments?.getInt(WEIGHT) }
	private val desc by lazy { arguments?.getString(DESC) }

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		binding = FragmentProductBinding.inflate(layoutInflater)

		initializeDialogData()
		workToCloseButton()
		workToFavoriteButton()
		workToAddToBasketButton()

		return AlertDialog.Builder(requireActivity())
			.setView(binding.root)
			.create()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		dialog?.apply {
			window?.setBackgroundDrawableResource(R.drawable.shape_dialog)
			setCanceledOnTouchOutside(false)
		}
	}

	private fun initializeDialogData() {
		with(binding) {
			productTitle.text = title
			productPrice.text = getString(R.string.product_price, price)
			productWeight.text = getString(R.string.product_weight, weight)
			productDescription.text = desc
			Glide.with(requireContext()).asBitmap().load(imageUrl).fitCenter().into(binding.dishImage)
		}
	}

	private fun workToAddToBasketButton() {
		binding.addToBasket.setOnClickListener {
			basketViewModel.addProduct(
				Basket(
					id = id ?: 0,
					name = title ?: "",
					imageUrl = imageUrl ?: "",
					price = price ?: 0,
					weight = weight ?: 0,
					counter = 1
				)
			)
			findNavController().navigateUp()
		}
	}

	private fun workToCloseButton() {
		binding.closeButton.setOnClickListener {
			findNavController().navigateUp()
		}
	}

	private fun workToFavoriteButton() {
		binding.favoriteButton.setOnClickListener { }
	}

	companion object {
		const val ID = "productId"
		const val IMAGE = "productImage"
		const val TITLE = "productTitle"
		const val PRICE = "productPrice"
		const val WEIGHT = "productWeight"
		const val DESC = "productDesc"
	}
}