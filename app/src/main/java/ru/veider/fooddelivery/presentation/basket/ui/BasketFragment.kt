package ru.veider.fooddelivery.presentation.basket.ui

import android.annotation.SuppressLint
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
import ru.veider.domain.model.Basket
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
					Toast.makeText(requireContext(), getString(R.string.load_account_error), Toast.LENGTH_LONG).show()
				}
			}
		}
	}

	@SuppressLint("NotifyDataSetChanged")
	private fun workToAdapter() {
		adapter = BasketAdapter(
			{ id ->
				basketViewModel.plusProduct(id)
				adapter.notifyDataSetChanged()
			},
			{ id ->
				basketViewModel.minusProduct(id)
				adapter.notifyDataSetChanged()
			}
		)
		binding.basketList.adapter = adapter
	}

	@SuppressLint("NotifyDataSetChanged")
	private fun workToBasket() {
		basketViewModel.basket.observe(viewLifecycleOwner) {
			when (it) {
				is ScreenState.Loading -> {
					basketShimmer(true)
				}

				is ScreenState.Success -> {
					val list = mutableListOf<Basket>()
					it.data.forEach { product ->
						list.add(product.copy())
					}
					adapter.items = list
					adapter.notifyDataSetChanged()
					binding.paidButton.isVisible = list.isNotEmpty()
					binding.emptyBasketText.isVisible = list.isEmpty()
					val sum = it.data.sumOf { it.price * it.counter }
					binding.paidButton.text = getString(R.string.basket_pay_button_title, "%,d".format(Locale("RU"), sum))
					basketEmpty(list.isEmpty())
				}

				is ScreenState.Error -> {
					basketShimmer(false)
					it.error.printStackTrace()
					Toast.makeText(requireContext(), getString(R.string.basket_load_error), Toast.LENGTH_LONG).show()
				}
			}

		}
	}

	private fun workToPaidButton() {
		binding.paidButton.setOnClickListener {
			basketViewModel.payBasket().observe(viewLifecycleOwner){
				when (it){
					is ScreenState.Loading -> {}
					is ScreenState.Success -> {
						binding.emptyBasketText.text = getString(R.string.basket_paid_message)
						CoroutineScope(Dispatchers.IO).launch {
							delay(2000)
							withContext(Dispatchers.Main) {
								binding.emptyBasketText.text = getString(R.string.basket_empty)
							}
						}
					}
					is ScreenState.Error -> {}
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

	private fun basketEmpty(state:Boolean){
		basketShimmer(state)
	}

}