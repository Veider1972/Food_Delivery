package ru.veider.fooddelivery.ru.veider.fooddelivery.domain.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.veider.fooddelivery.domain.usecases.UseCases
import ru.veider.fooddelivery.domain.usecases.UseCasesImpl

val domainModule = module {

	singleOf(::UseCasesImpl) { bind<UseCases>() }

}