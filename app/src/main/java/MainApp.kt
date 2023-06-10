package ru.veider.fooddelivery

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.veider.fooddelivery.di.appModule
import ru.veider.fooddelivery.ru.veider.fooddelivery.di.netModule

class MainApp: Application() {
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidLogger()
			androidContext(this@MainApp)
			modules(appModule, netModule)
		}
	}
}