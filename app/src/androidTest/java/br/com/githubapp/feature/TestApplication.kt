package br.com.githubapp.feature

import android.app.Application
import android.support.test.InstrumentationRegistry
import br.com.githubapp.di.Injection
import br.com.githubapp.feature.di.InjectionTest
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import okhttp3.mockwebserver.MockWebServer

/**
 * Created by pedro on 22/02/18.
 */
class TestApplication: Application(), KodeinAware{
    override lateinit var kodein: Kodein

    fun setBaseUrl(url: String){
        kodein = InjectionTest(this, url).graph
    }

}