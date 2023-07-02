package ru.veider.fooddelivery.presentation.category.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.veider.fooddelivery.presentation.category.vm.CategoryViewModel

val categoryModule = module {
	singleOf(::CategoryViewModel)
}