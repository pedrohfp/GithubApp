package br.com.githubapp.feature.repo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import br.com.githubapp.R
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.RepoItem
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.model.Status
import br.com.githubapp.feature.repo.viewmodel.RepoViewModel
import br.com.githubapp.feature.repo.viewmodel.RepoViewModelFactory
import br.com.githubapp.util.EndlessRecyclerViewScrollListener
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_home.*

class RepoActivity : AppCompatActivity() {

    private val kodein = LazyKodein(appKodein)
    private lateinit var repoViewModel: RepoViewModel
    private lateinit var repoViewModelFactory: RepoViewModelFactory

    private val repoList: MutableList<RepoItem> = arrayListOf()

    private val adapter: RepoListAdapter by lazy { RepoListAdapter(repoList, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        repoViewModelFactory = kodein.value.instance()

        repoViewModel = ViewModelProviders.of(this, repoViewModelFactory)
                .get(RepoViewModel::class.java)

        repoViewModel.getRepositories().observe(this, Observer { resRepo: Resource<Repo>? ->
            resRepo?.let {
                when (resRepo.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        repoList.addAll(resRepo.data!!.items)
                        adapter.notifyDataSetChanged()
                    }
                    Status.ERROR -> Log.e("Error:", "error when called loadRepositories")
                    Status.LOADING -> TODO()
                }
            }
        })

        setupRecyclerView()

        repoViewModel.loadRepositories()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,
                false)

        repoRecyclerView.adapter = adapter
        repoRecyclerView.layoutManager = layoutManager

        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                repoViewModel.loadRepositories()
            }
        }

        repoRecyclerView.addOnScrollListener(scrollListener)
    }
}
