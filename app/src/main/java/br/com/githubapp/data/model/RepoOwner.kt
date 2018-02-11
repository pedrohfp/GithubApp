package br.com.githubapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by pedrohenrique on 10/02/2018.
 */
data class RepoOwner(@SerializedName("id") val id: Int,
                     @SerializedName("login") val login: String,
                     @SerializedName("avatar_url") val avatarUrl: String)