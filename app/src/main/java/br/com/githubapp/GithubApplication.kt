package br.com.githubapp

import android.app.Application
import br.com.githubapp.di.Injection
import com.github.salomonbrys.kodein.KodeinAware

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class GithubApplication: Application(), KodeinAware{
    override val kodein by Injection(this, "https://api.github.com/").graph
}