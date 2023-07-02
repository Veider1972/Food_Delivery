package ru.veider.fooddelivery.presentation.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.badge.BadgeDrawable
import org.koin.android.ext.android.inject
import ru.veider.fooddelivery.R
import ru.veider.fooddelivery.databinding.ActivityMainBinding
import ru.veider.fooddelivery.presentation.ui.basket.vm.BasketViewModel


class MainActivity : AppCompatActivity(R.layout.activity_main) {

	private val binding by viewBinding(ActivityMainBinding::bind, R.id.container)

	private lateinit var navController: NavController

	private lateinit var badge: BadgeDrawable

	private val basketViewModel: BasketViewModel by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		installSplashScreen()
		super.onCreate(savedInstanceState)
		setupBottomNavigation()
		workToBasket()
	}

	override fun onSupportNavigateUp(): Boolean {
		return navController.navigateUp()
	}

	private fun workToBasket() {
		badge = binding.bottomNav.getOrCreateBadge(R.id.basketFragment)
		badge.isVisible = false
		basketViewModel.basket.observe(this) {
			if (it.isNotEmpty()) {
				badge.number = it.size
				badge.isVisible = true
			} else {
				badge.isVisible = false
			}
		}
	}

	private fun setupBottomNavigation() {

		val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

		navController = navHostFragment.navController

		binding.bottomNav.setupWithNavController(navController)

		navController.addOnDestinationChangedListener { _, destination, _ ->
			when (destination.id) {
				R.id.mainFragment,
				R.id.searchFragment,
				R.id.basketFragment,
				R.id.accountFragment,
				R.id.categoryFragment,
				R.id.productFragment -> {
					binding.bottomNav.isVisible = true

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
						window.decorView.windowInsetsController?.setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS)
					} else
						window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
				}
			}
		}
	}
}