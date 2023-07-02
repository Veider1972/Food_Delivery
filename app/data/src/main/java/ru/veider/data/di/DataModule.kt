package ru.veider.data.di

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
import ru.veider.data.datasource.DataSource
import ru.veider.data.datasource.DataSourceImpl
import ru.veider.data.datasource.api.WebApi
import ru.veider.data.repository.Repo
import ru.veider.data.repository.RepoImpl


val dataModule = module {

	val BASE_URL = "https://run.mocky.io/v3/"

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

	fun provideWebAPI(): WebApi = Retrofit.Builder().baseUrl(BASE_URL)
		.addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
		.client(createOkHttpClient(WebInterceptor())).build().create(WebApi::class.java)

	single { provideWebAPI() }
	singleOf(::RepoImpl) { bind<Repo>() }
	singleOf(::DataSourceImpl) { bind<DataSource>() }
	single { provideGson() }
}