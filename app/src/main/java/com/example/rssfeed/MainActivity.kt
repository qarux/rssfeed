package com.example.rssfeed

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssfeed.adapters.ArticleAdapter
import com.example.rssfeed.databinding.ActivityMainBinding
import com.example.rssfeed.models.MainModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.rssRecyclerView
        val adapter = ArticleAdapter()
        adapter.setOnClickListener {
            startActivity(DetailsActivity.createStartActivityIntent(this, it))
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.articles.observe(this) {
            adapter.replace(it)
        }
        viewModel.isLoading.observe(this) {
            binding.refreshLayout.isRefreshing = it
        }
        viewModel.fetchFeeds()

        binding.refreshLayout.setOnRefreshListener {
            viewModel.fetchFeeds()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.manage_subscriptions -> {
                launchSubscriptionsActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun launchSubscriptionsActivity() {
        startActivity(SubscriptionActivity.createStartActivityIntent(this))
    }
}