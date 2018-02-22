package br.com.githubapp.feature.repo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.githubapp.R
import br.com.githubapp.data.model.RepoItem
import br.com.githubapp.util.CircleTransform
import com.squareup.picasso.Picasso

/**
 * Created by pedrohenrique on 19/02/2018.
 */
class RepoListAdapter(private val repoList: List<RepoItem>, private val context: Context) : RecyclerView.Adapter<RepoListAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bindItens(repoList[position], context)

    override fun getItemCount(): Int = repoList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.repo_item, parent, false)
        return ViewHolder(v)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindItens(repoItem: RepoItem, context: Context) {
            val repositoryNameTextView =
                    itemView.findViewById<TextView>(R.id.repositoryNameTextView)
            val descriptionTextView =
                    itemView.findViewById<TextView>(R.id.descriptionTextView)
            val forkTextView =
                    itemView.findViewById<TextView>(R.id.forkTextView)
            val starTextView =
                    itemView.findViewById<TextView>(R.id.starTextView)
            val userImageView =
                    itemView.findViewById<ImageView>(R.id.avatar)
            val usernameTextView = itemView.findViewById<TextView>(R.id.username)

            repositoryNameTextView.text = repoItem.name
            descriptionTextView.text = repoItem.description
            forkTextView.text = repoItem.forksCount
            starTextView.text = repoItem.starsCount
            usernameTextView.text = repoItem.owner.login

            Picasso.with(context)
                    .load(repoItem.owner.avatarUrl)
                    .transform(CircleTransform())
                    .into(userImageView)
        }
    }

}