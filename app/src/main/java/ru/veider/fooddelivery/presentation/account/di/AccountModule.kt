package ru.veider.fooddelivery.presentation.account.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.veider.fooddelivery.presentation.account.vm.AccountViewModel

val accountModule = module {
	singleOf(::AccountViewModel)
}