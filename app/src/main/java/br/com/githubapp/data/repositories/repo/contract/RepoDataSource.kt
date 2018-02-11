package br.com.githubapp.data.repositories.repo.contract

import android.arch.lifecycle.LiveData
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource

/**
 * Created by pedrohenrique on 10/02/2018.
 */
interface RepoDataSource{
    fun getRepositories(): LiveData<Resource<Repo>>
}