package br.com.githubapp.data.repositories.repo

import android.arch.lifecycle.LiveData
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.repositories.repo.contract.RepoDataSource
import br.com.githubapp.data.repositories.repo.source.RepoLocalDataSource
import br.com.githubapp.data.repositories.repo.source.RepoRemoteDataSource

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class RepoRepository(private val repoRemoteDataSource: RepoRemoteDataSource,
                     private val repoLocalDataSource: RepoLocalDataSource
): RepoDataSource {
    override fun getRepositories(): LiveData<Resource<Repo>> {
        return repoRemoteDataSource.getRepositories()
    }
}