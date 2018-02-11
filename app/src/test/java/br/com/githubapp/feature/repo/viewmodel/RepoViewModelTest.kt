package br.com.githubapp.feature.repo.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.model.Status
import br.com.githubapp.data.repositories.repo.RepoRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before

import org.junit.Rule
import org.junit.Test

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class RepoViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repoRepository: RepoRepository
    private lateinit var repoViewModel: RepoViewModel
    private lateinit var observer: Observer<Resource<Repo>>

    @Before
    fun setUp() {
        repoRepository = mock()
        observer = mock()
        repoViewModel = RepoViewModel(repoRepository)
    }

    @Test
    fun testLoadRepositoriesSuccesful(){
        val repo: Repo = mock()

        val repoLiveData: MutableLiveData<Resource<Repo>> = MutableLiveData()
        repoLiveData.value = Resource(Status.SUCCESS, repo)

        whenever(repoRepository.getRepositories()).thenReturn(repoLiveData)
        repoViewModel.getRepositories().observeForever(observer)
        repoViewModel.getRepositories()

        verify(observer).onChanged(repoLiveData.value)
    }
}