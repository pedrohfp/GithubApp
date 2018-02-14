package br.com.githubapp.data.repositories.repo

import br.com.githubapp.BuildConfig
import br.com.githubapp.data.api.RepoApi
import br.com.githubapp.data.model.Status
import br.com.githubapp.data.repositories.repo.source.RepoLocalDataSource
import br.com.githubapp.data.repositories.repo.source.RepoRemoteDataSource
import br.com.githubapp.utils.getJson
import com.google.gson.GsonBuilder
import com.nhaarman.mockito_kotlin.mock
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



/**
 * Created by pedrohenrique on 14/02/2018.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(25))
class RepoRepositoryTest {

    private lateinit var repoRepository: RepoRepository
    private lateinit var repoRemoteDataSource: RepoRemoteDataSource
    private lateinit var repoLocalDataSource: RepoLocalDataSource
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        repoLocalDataSource = mock()

        server = MockWebServer()
        server.start()

        val retrofit = Retrofit.Builder()
                .baseUrl(server.url("/").toString())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

        val repoApi = retrofit.create(RepoApi::class.java)

        repoRemoteDataSource = RepoRemoteDataSource(repoApi)

        repoRepository = RepoRepository(repoRemoteDataSource, repoLocalDataSource)
    }

    @Test
    fun testLoadRepoSuccessful(){
        server.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(getJson(this, "json/GetRepositories.json")))

        val repoLiveData = repoRepository.getRepositories()

        assertEquals(Status.SUCCESS, repoLiveData.value!!.status)
    }
}