package com.example.twittude

import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.SavedStateHandle
import com.example.twittude.api.TwitApiService
import com.example.twittude.api.TwitMainRepository
import com.example.twittude.api.TwitMainRepositoryImpl
import com.example.twittude.ui.TwitHistoryViewModel
import com.example.twittude.ui.TwitMainViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
val appDi = module {

    single<TwitMainRepository> {
        TwitMainRepositoryImpl(
            service = buildTwitApiService(okHttpClient = get())
        )
    }

    single<OkHttpClient> {
        //5MB
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(androidContext().cacheDir, cacheSize)

        val tokenStore = get<DataStore<Preferences>>(qualifier = named("bearer"))
        val TOKEN_KEY = preferencesKey<String>("bearer_key")
        var bearerToken: String? = null
        GlobalScope.launch {
            tokenStore.data.collect {
                bearerToken = it[TOKEN_KEY]
            }
        }

        val encodedAuthToken = BuildConfig.TWIT_ENCODED_TOKEN


        val httpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .cache(myCache)
            .readTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                if (request.url.queryParameterNames.contains("query")) {
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer $bearerToken").build()
                    //.header("Cache-Control", "public, only-if-cached, max-stale=604800").build()
                }
                if (request.url.queryParameterNames.contains("grant_type")) {
                    request = request.newBuilder()
                        .addHeader("Authorization", "Basic $encodedAuthToken").build()
                }
                chain.proceed(request)
            }
            .build()
    }


    //Add DataStore for bearer token
    single<DataStore<Preferences>>(
        qualifier = named(name = "bearer")
    ) {
        androidContext().createDataStore(
            name = "bearer_token"
        )
    }

    single<DataStore<Preferences>>(
        qualifier = named(name = "tweets")
    ) {
        androidContext().createDataStore(
            name = "tweets"
        )
    }

    viewModel { (handle: SavedStateHandle) ->
        TwitMainViewModel(
            savedStateHandle = handle,
            repository = get()
        )
    }

    viewModel {
        TwitHistoryViewModel(
            repository = get()
        )
    }

}


fun buildTwitApiService(okHttpClient: OkHttpClient): TwitApiService =
    Retrofit.Builder()
        //Set [strictMode = false] to escape unmapped json keys and avoid `UNKNOWN KEY` error
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .build()
        .create(TwitApiService::class.java)