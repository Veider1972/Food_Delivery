package ru.veider.fooddelivery.presentation.ui.basket.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.veider.fooddelivery.presentation.ui.basket.vm.BasketViewModel

val basketModule = module {
	singleOf(::BasketViewModel)
}