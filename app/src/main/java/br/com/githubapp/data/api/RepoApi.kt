package br.com.githubapp.data.api

import br.com.githubapp.data.model.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by pedrohenrique on 10/02/2018.
 */
interface RepoApi {
    @GET("search/repositories?q=language:Java&sort=stars")
    fun loadRepositories(@Query("page") page: Int): Call<Repo>
}