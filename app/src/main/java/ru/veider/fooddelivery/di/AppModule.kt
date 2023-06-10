package ru.veider.fooddelivery.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.veider.fooddelivery.repo.WebSource
import ru.veider.fooddelivery.ru.veider.fooddelivery.di.Api
import ru.veider.fooddelivery.usecases.UseCases
import ru.veider.fooddelivery.usecases.UseCasesImpl
import ru.veider.fooddelivery.viewmodels.MainViewModel
import ru.veider.fooddelivery.viewmodels.AccountViewModel
import ru.veider.fooddelivery.viewmodels.CategoryViewModel
import ru.veider.fooddelivery.viewmodels.BasketViewModel

val appModule = module {

	single { Api.gson }

	single { WebSource(get()) }
	singleOf(::UseCasesImpl) { bind<UseCases>() }

	singleOf(::MainViewModel)
	singleOf(::AccountViewModel)
	singleOf(::CategoryViewModel)
	singleOf(::BasketViewModel)
}