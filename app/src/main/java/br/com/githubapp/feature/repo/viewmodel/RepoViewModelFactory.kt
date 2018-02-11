package br.com.githubapp.feature.repo.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.com.githubapp.data.repositories.repo.RepoRepository

/**
 * Created by pedrohenrique on 10/02/2018.
 */
open class RepoViewModelFactory(
        private val repository: RepoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RepoViewModel::class.java))
            return RepoViewModel(repository) as T

        throw IllegalArgumentException("Unknow ViewModel class")
    }
}