package br.com.githubapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by pedrohenrique on 10/02/2018.
 */
data class Repo(@SerializedName("total_count") val totalCount: Int,
                @SerializedName("items") val items: List<RepoItem>)