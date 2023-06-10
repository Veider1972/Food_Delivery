package ru.veider.fooddelivery.ru.veider.fooddelivery.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.veider.fooddelivery.repo.WebApi

object Api {

	private const val BASE_URL = "https://run.mocky.io/v3/"

	val gson = Gson()

	val webAPI: WebApi = Retrofit.Builder().baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
		.client(createOkHttpClient(WebInterceptor())).build().create(WebApi::class.java)

	private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
		val okHttpClient = OkHttpClient.Builder().apply {
			addInterceptor(interceptor)
			addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
		}
		return okHttpClient.build()
	}

	private class WebInterceptor : Interceptor {
		@Throws(IOException::class)
		override fun intercept(chain: Interceptor.Chain): Response {
			return chain.proceed(chain.request())
		}
	}
}