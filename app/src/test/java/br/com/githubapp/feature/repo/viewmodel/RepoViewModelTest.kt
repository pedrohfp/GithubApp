package br.com.githubapp.feature.repo.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.NonNull
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.model.Status
import br.com.githubapp.data.repositories.repo.RepoRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

/**
 * Created by pedrohenrique on 10/02/2018.
 */
class RepoViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repoRepository: RepoRepository
    private lateinit var repoViewModel: RepoViewModel
    private lateinit var observer: Observer<Resource<Repo>?>

    @Before
    fun setUp() {
        repoRepository = mock()
        observer = mock()
        repoViewModel = RepoViewModel(repoRepository)

        val immediate = object : Scheduler() {
            override fun scheduleDirect(@NonNull run: Runnable, delay: Long, @NonNull unit: TimeUnit): Disposable {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @Test
    fun whenRepositoryReturnedSuccess_verifyLiveDataObserverIsCalled(){
        val repo: Repo = mock()

        val repoLiveData: MutableLiveData<Resource<Repo>> = MutableLiveData()
        repoLiveData.value = Resource(Status.SUCCESS, repo)

        whenever(repoRepository.getRepositories(0)).thenReturn(Single.just(repo))
        repoViewModel.getRepositories().observeForever(observer)
        repoViewModel.loadRepositories(0)

        verify(observer, times(1)).onChanged(repoLiveData.value)
    }

    @Test
    fun whenRepositoryReturnedFailed_verifyLiveDataObserverIsCalled(){
        val repoLiveData: MutableLiveData<Resource<Repo>> = MutableLiveData()
        repoLiveData.value = Resource(Status.ERROR)

        whenever(repoRepository.getRepositories(0)).thenReturn(Single.error(Exception()))
        repoViewModel.getRepositories().observeForever(observer)
        repoViewModel.loadRepositories(0)

        verify(observer, times(1)).onChanged(repoLiveData.value)
    }
}