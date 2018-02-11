package br.com.githubapp.di

import android.content.Context
import br.com.githubapp.data.api.RepoApi
import br.com.githubapp.data.repositories.repo.RepoRepository
import br.com.githubapp.data.repositories.repo.source.RepoLocalDataSource
import br.com.githubapp.data.repositories.repo.source.RepoRemoteDataSource
import br.com.githubapp.feature.repo.viewmodel.RepoViewModelFactory
import com.github.salomonbrys.kodein.*
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
class Injection(private val context: Context, private val baseUrl: String){

    private val networkModule = Kodein.Module{
        bind<Cache>() with singleton {
            val cacheSize = 10 * 1024 * 1024
            Cache(context.cacheDir, cacheSize.toLong())
        }

        bind<HttpLoggingInterceptor>() with singleton {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        }

        bind<OkHttpClient>() with singleton {
            val interceptor: HttpLoggingInterceptor = instance()

            OkHttpClient.Builder()
                    .cache(instance())
                    .addInterceptor(interceptor)
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

    private val repoModule = Kodein.Module{
        bind<RepoApi>() with provider {
            val retrofit:Retrofit = instance()
            retrofit.create(RepoApi::class.java) as RepoApi
        }

        bind<RepoRemoteDataSource>() with singleton {
            RepoRemoteDataSource(instance())
        }

        bind<RepoLocalDataSource>() with singleton {
            RepoLocalDataSource()
        }

        bind<RepoRepository>() with singleton {
            RepoRepository(instance(), instance())
        }

        bind<RepoViewModelFactory>() with provider {
            RepoViewModelFactory(instance())
        }
    }

    val graph = Kodein.lazy {
        import(networkModule)
        import(repoModule)
    }
}