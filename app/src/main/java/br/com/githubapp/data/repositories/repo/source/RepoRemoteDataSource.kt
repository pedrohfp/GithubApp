package br.com.githubapp.data.repositories.repo.source

import android.arch.lifecycle.MutableLiveData
import br.com.githubapp.data.api.RepoApi
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.model.Status
import br.com.githubapp.data.repositories.repo.contract.RepoDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class RepoRemoteDataSource(private val repoApi: RepoApi): RepoDataSource {
    override fun getRepositories(page: Int): MutableLiveData<Resource<Repo>> {
        val mutableLiveData = MutableLiveData<Resource<Repo>>()

        repoApi.loadRepositories(page).enqueue(object : Callback<Repo> {
            override fun onFailure(call: Call<Repo>?, t: Throwable?) {
                mutableLiveData.value = Resource(Status.ERROR)
            }

            override fun onResponse(call: Call<Repo>?, response: Response<Repo>?) {
                mutableLiveData.value = Resource(Status.SUCCESS, response?.body())
            }
        })

        return mutableLiveData
    }
}
