package ru.veider.fooddelivery

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.veider.data.di.dataModule
import ru.veider.fooddelivery.presentation.account.di.accountModule
import ru.veider.fooddelivery.presentation.basket.di.basketModule
import ru.veider.fooddelivery.presentation.category.di.categoryModule
import ru.veider.fooddelivery.presentation.main.di.mainModule
import ru.veider.usecases.di.useCasesModule

class MainApp : Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidLogger()
			androidContext(this@MainApp)
			modules(useCasesModule, dataModule, basketModule, categoryModule, mainModule, accountModule)
		}
	}
}