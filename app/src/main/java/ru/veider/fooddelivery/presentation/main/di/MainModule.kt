package ru.veider.fooddelivery.presentation.main.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.veider.fooddelivery.presentation.main.vm.MainViewModel

val mainModule = module {
	singleOf(::MainViewModel)
}