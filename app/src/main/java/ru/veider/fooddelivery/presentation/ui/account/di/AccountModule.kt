package ru.veider.fooddelivery.presentation.ui.account.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.veider.fooddelivery.presentation.ui.account.vm.AccountViewModel

val accountModule = module {
	singleOf(::AccountViewModel)
}