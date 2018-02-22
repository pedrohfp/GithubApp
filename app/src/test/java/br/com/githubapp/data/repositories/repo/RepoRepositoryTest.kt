package br.com.githubapp.data.repositories.repo

import br.com.githubapp.BuildConfig
import br.com.githubapp.data.api.RepoApi
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.repositories.repo.source.RepoLocalDataSource
import br.com.githubapp.data.repositories.repo.source.RepoRemoteDataSource
import com.example.mocks.JsonPaths
import com.example.mocks.getJson
import com.google.gson.GsonBuilder
import com.nhaarman.mockito_kotlin.mock
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
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

    @Rule
    @JvmField
    val exceptionExpected: ExpectedException = ExpectedException.none()

    private lateinit var repoRepository: RepoRepository
    private lateinit var repoRemoteDataSource: RepoRemoteDataSource
    private lateinit var repoLocalDataSource: RepoLocalDataSource
    private lateinit var server: MockWebServer
    private lateinit var repo: Repo
    private lateinit var exception: Throwable

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
    fun whenGetRepositoriesReturnedRepo_verifyRepoIsNotNull(){

        server.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(getJson(this, JsonPaths.GET_REPOSITORIES)))

        repoRepository.getRepositories(0).subscribe{ repo ->
            this.repo = repo
        }

        assertNotNull(repo)
    }

    @Test
    fun whenGetRepositoriesReturnedException_verifyExceptionIsNotNull(){
        server.enqueue(MockResponse().setResponseCode(400))

        repoRepository.getRepositories(0).subscribe{ _, exception: Throwable ->
            this.exception = exception
        }

        assertNotNull(exception)
    }
}