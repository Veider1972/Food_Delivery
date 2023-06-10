package ru.veider.fooddelivery.presentation.account

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import org.koin.android.ext.android.inject
import ru.veider.fooddelivery.R
import ru.veider.fooddelivery.databinding.FragmentAccountBinding
import ru.veider.fooddelivery.domain.ScreenState
import ru.veider.fooddelivery.viewmodels.AccountViewModel

class AccountFragment : Fragment(R.layout.fragment_account) {

	private val binding by viewBinding(FragmentAccountBinding::bind)

	private val viewModel: AccountViewModel by inject()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewModel.getAccountData().observe(viewLifecycleOwner){
			when (it){
				is ScreenState.Loading -> {
					showShimmer(true)
				}
				is ScreenState.Success -> {

					with (binding){
						val decodedString: ByteArray = Base64.decode(it.data.imageString, Base64.DEFAULT)
						image.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size))
						location.text = it.data.location
						date.text = it.data.date
					}
					showShimmer(false)
				}
				is ScreenState.Error -> {
					showShimmer(false)
					binding.accountContainer.isVisible = false
					Toast.makeText(requireContext(),getString(R.string.load_account_error), Toast.LENGTH_LONG).show()
				}
			}
		}
	}

	private fun showShimmer(state: Boolean) {
		with(binding) {
			loading.isVisible = state
			accountContainer.isVisible = !state
			if (state) {
				if (!shimmer.isShimmerStarted) {
					shimmer.startShimmer()
				}
			} else {
				if (shimmer.isShimmerStarted) {
					shimmer.stopShimmer()
				}
			}
		}
	}


}