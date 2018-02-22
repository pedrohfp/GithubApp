package br.com.githubapp.data.repositories.repo.source

import android.arch.lifecycle.MutableLiveData
import br.com.githubapp.data.api.RepoApi
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.model.Status
import br.com.githubapp.data.repositories.repo.contract.RepoDataSource
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class RepoRemoteDataSource(private val repoApi: RepoApi): RepoDataSource {
    override fun getRepositories(page: Int): Single<Repo> {
        return Single.create { e ->
            val callBody = repoApi.loadRepositories(page)

            val responseBody = callBody.execute()

            if(responseBody.isSuccessful){
                e.onSuccess(responseBody.body()!!)
            }else{
                e.onError(HttpException(responseBody))
            }
        }
    }
}
