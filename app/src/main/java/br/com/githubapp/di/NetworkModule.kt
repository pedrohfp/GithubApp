package br.com.githubapp.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class NetworkModule(private val context: Context, private val baseUrl: String){
    val networkModule = Kodein.Module{
        bind<Cache>() with singleton {
            val cacheSize = 10 * 1024 * 1024
            Cache(context.cacheDir, cacheSize.toLong())
        }

        bind<HttpLoggingInterceptor>() with singleton {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        }

        bind<OkHttpClient>() with singleton {
            OkHttpClient.Builder()
                    .cache(instance())
                    .addInterceptor(instance())
                    .build()
        }

        bind<Gson>() with singleton {
            GsonBuilder().create()
        }

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(instance()))
                    .baseUrl(baseUrl)
                    .client(instance())
                    .build()
        }
    }
}