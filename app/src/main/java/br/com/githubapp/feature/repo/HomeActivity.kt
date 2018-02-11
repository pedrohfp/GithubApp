package br.com.githubapp.feature.repo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.githubapp.R
import br.com.githubapp.data.model.Repo
import br.com.githubapp.data.model.Resource
import br.com.githubapp.data.model.Status
import br.com.githubapp.feature.repo.viewmodel.RepoViewModel
import br.com.githubapp.feature.repo.viewmodel.RepoViewModelFactory
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val kodein = LazyKodein(appKodein)
    private lateinit var repoViewModel: RepoViewModel
    private lateinit var repoViewModelFactory: RepoViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        repoViewModelFactory = kodein.value.instance()

        repoViewModel = ViewModelProviders.of(this, repoViewModelFactory)
                .get(RepoViewModel::class.java)

        repoViewModel.getRepositories().observe(this, Observer { resRepo: Resource<Repo>? ->
            when(resRepo!!.status){
                Status.SUCCESS -> name.text = resRepo.data!!.items[0].name
                Status.ERROR -> name.text = resRepo.data!!.items[0].name
                Status.LOADING -> name.text = resRepo.data!!.items[0].name
            }
        })

        repoViewModel.getRepositories()
    }
}
