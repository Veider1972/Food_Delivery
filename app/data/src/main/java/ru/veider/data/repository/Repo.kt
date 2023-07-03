package ru.veider.data.repository

import ru.veider.data.datasources.basket_datasource.BasketDataSource
import ru.veider.data.datasources.category_datasource.CategoryDataSource
import ru.veider.data.datasources.product_datasource.ProductDataSource

interface Repo:CategoryDataSource, ProductDataSource, BasketDataSource {

}