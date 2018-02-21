package br.com.githubapp.feature.repo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.model.Status
import br.com.githubapp.data.repositories.repo.RepoRepository

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class RepoViewModel(private val repoRepo: RepoRepository): ViewModel() {

    private val repoMutableLiveData: MutableLiveData<Resource<Repo>?> by lazy{
        MutableLiveData<Resource<Repo>?>()
    }

    fun getRepositories(): MutableLiveData<Resource<Repo>?> {
        return repoMutableLiveData
    }

    fun loadRepositories(page: Int = 0){
        repoMutableLiveData.postValue(Resource(Status.ERROR))
    }
}