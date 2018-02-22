package br.com.githubapp.feature.repo.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.model.Status
import br.com.githubapp.data.repositories.repo.RepoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class RepoViewModel(private val repoRepo: RepoRepository): ViewModel() {

    private val repoMutableLiveData: MutableLiveData<Resource<Repo>?> by lazy{ MutableLiveData<Resource<Repo>?>() }
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    fun getRepositories(): MutableLiveData<Resource<Repo>?> {
        return repoMutableLiveData
    }

    fun loadRepositories(page: Int = 0){
        val disposable = repoRepo.getRepositories(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ repo ->
                    repoMutableLiveData.value = Resource(Status.SUCCESS, repo)
                }, { e ->
                    repoMutableLiveData.value = Resource(Status.ERROR)
                })

        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}