package ru.veider.usecases.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.veider.usecases.UseCases
import ru.veider.usecases.UseCasesImpl

val useCasesModule = module {

	singleOf(::UseCasesImpl) { bind<UseCases>() }

}