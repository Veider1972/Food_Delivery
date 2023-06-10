package ru.veider.fooddelivery.ru.veider.fooddelivery.di

import org.koin.dsl.module

val netModule = module {
	single { Api.webAPI }
}