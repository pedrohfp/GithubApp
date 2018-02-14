package br.com.githubapp.feature.repo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.repositories.repo.RepoRepository

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class RepoViewModel(private val repoRepo: RepoRepository): ViewModel() {

    fun getRepositories(): LiveData<Resource<Repo>>{
        return repoRepo.getRepositories()
    }
}