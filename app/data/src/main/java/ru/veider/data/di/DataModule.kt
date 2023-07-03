package ru.veider.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.veider.data.datasources.basket_datasource.BasketDataSource
import ru.veider.data.datasources.basket_datasource.BasketDataSourceImpl
import ru.veider.data.datasources.category_datasource.CategoryDataSource
import ru.veider.data.datasources.category_datasource.CategoryDataSourceImpl
import ru.veider.data.datasources.category_datasource.api.CategoryWebApi
import ru.veider.data.datasources.product_datasource.ProductDataSource
import ru.veider.data.datasources.product_datasource.ProductDataSourceImpl
import ru.veider.data.datasources.product_datasource.api.ProductWebApi
import ru.veider.data.repository.Repo
import ru.veider.data.repository.RepoImpl


val dataModule = module {

	val baseUrl = "https://run.mocky.io/v3/"
	val delivery = "deliveryPreferences"

	fun provideGson(): Gson = Gson()

	fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
		val okHttpClient = OkHttpClient.Builder().apply {
			addInterceptor(interceptor)
			addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
		}
		return okHttpClient.build()
	}

	class WebInterceptor : Interceptor {
		@Throws(IOException::class)
		override fun intercept(chain: Interceptor.Chain): Response {
			return chain.proceed(chain.request())
		}
	}

	fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(baseUrl)
		.addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
		.client(createOkHttpClient(WebInterceptor())).build()

	fun provideCategoryWebAPI(): CategoryWebApi = provideRetrofit().create(CategoryWebApi::class.java)

	fun provideProductWebAPI(): ProductWebApi = provideRetrofit().create(ProductWebApi::class.java)

	fun providePreferences(application: Application): SharedPreferences =
		application.getSharedPreferences(delivery, Context.MODE_PRIVATE)

	single { providePreferences(get()) }
	single { provideCategoryWebAPI() }
	single { provideProductWebAPI() }
	singleOf(::RepoImpl) { bind<Repo>() }
	singleOf(::CategoryDataSourceImpl) { bind<CategoryDataSource>() }
	singleOf(::ProductDataSourceImpl) { bind<ProductDataSource>() }
	singleOf(::BasketDataSourceImpl) { bind<BasketDataSource>() }
	single { provideGson() }
}