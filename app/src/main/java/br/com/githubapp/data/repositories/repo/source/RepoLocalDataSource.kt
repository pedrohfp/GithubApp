package br.com.githubapp.data.repositories.repo.source

import android.arch.lifecycle.LiveData
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.repositories.repo.contract.RepoDataSource
import io.reactivex.Single

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class RepoLocalDataSource: RepoDataSource{
    override fun getRepositories(page: Int): Single<Repo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}