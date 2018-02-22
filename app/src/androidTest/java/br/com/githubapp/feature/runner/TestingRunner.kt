package br.com.githubapp.feature.runner

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import br.com.githubapp.feature.TestApplication

/**
 * Created by pedro on 22/02/18.
 */
class TestingRunner: AndroidJUnitRunner(){
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}