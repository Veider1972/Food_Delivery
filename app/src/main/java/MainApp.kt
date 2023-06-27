package ru.veider.fooddelivery

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.veider.fooddelivery.ru.veider.fooddelivery.domain.di.domainModule
import ru.veider.fooddelivery.data.di.dataModule
import ru.veider.fooddelivery.presentation.ui.account.di.accountModule
import ru.veider.fooddelivery.presentation.ui.basket.di.basketModule
import ru.veider.fooddelivery.presentation.ui.category.di.categoryModule
import ru.veider.fooddelivery.presentation.ui.main.di.mainModule

class MainApp : Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidLogger()
			androidContext(this@MainApp)
			modules(domainModule, dataModule, basketModule, categoryModule, mainModule, accountModule)
		}
	}
}