package br.com.githubapp.data.model

import com.google.gson.annotations.SerializedName

data class RepoItem(@SerializedName("id")  val id: Int,
                    @SerializedName("name")  val name: String,
                    @SerializedName("description") val description: String,
                    @SerializedName("stargazers_count") val starsCount: String,
                    @SerializedName("forks_count") val forksCount: String,
                    @SerializedName("owner") val owner: RepoOwner)