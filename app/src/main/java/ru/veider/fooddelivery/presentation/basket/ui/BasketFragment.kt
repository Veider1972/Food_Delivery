package ru.veider.fooddelivery.presentation.basket.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.veider.fooddelivery.R
import ru.veider.fooddelivery.databinding.FragmentBasketBinding
import ru.veider.core.datatype.ScreenState
import ru.veider.fooddelivery.domain.model.Basket
import ru.veider.fooddelivery.presentation.account.vm.AccountViewModel
import ru.veider.fooddelivery.presentation.basket.vm.BasketViewModel
import java.util.Locale

class BasketFragment : Fragment(R.layout.fragment_basket) {

	val binding by viewBinding(FragmentBasketBinding::bind)

	private val basketViewModel: BasketViewModel by inject()
	private val accountViewModel: AccountViewModel by inject()

	private lateinit var adapter: BasketAdapter

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		workToAccount()
		workToAdapter()
		workToBasket()
		workToPaidButton()
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

	private fun workToAdapter() {
		adapter = BasketAdapter(object : BasketAdapter.OnClick {
			override fun increaseCounter(id: Long) {
				basketViewModel.plusProduct(id)
			}

			override fun decreaseCounter(id: Long) {
				basketViewModel.minusProduct(id)
			}
		})
		binding.basketList.adapter = adapter
	}

	private fun workToBasket() {
		basketViewModel.basket.observe(viewLifecycleOwner) { productList ->
			if (productList.isNotEmpty()) {
				basketShimmer(false)
				val list = mutableListOf<Basket>()
				productList.forEach { product ->
					list.add(product.copy())
				}
				adapter.submitList(list)
				val sum = productList.sumOf { it.price * it.counter }
				binding.paidButton.text = getString(R.string.basket_pay_button_title, "%,d".format(Locale("RU"), sum))
			} else {
				basketShimmer(true)
			}
		}
	}

	private fun workToPaidButton(){
		binding.paidButton.setOnClickListener {
			basketViewModel.clearBasket()
			binding.emptyBasketText.text = getString(R.string.basket_paid_message)
			CoroutineScope(Dispatchers.IO).launch {
				delay(2000)
				withContext(Dispatchers.Main){
					binding.emptyBasketText.text = getString(R.string.basket_empty)
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

	private fun basketShimmer(state: Boolean) {
		with(binding) {
			basketLoading.isVisible = state
			basketContainer.isVisible = !state
		}
	}

}