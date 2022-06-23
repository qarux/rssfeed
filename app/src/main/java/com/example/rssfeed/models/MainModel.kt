package com.example.rssfeed.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.RssEntry
import com.example.rssfeed.database.AppDatabase
import com.example.rssfeed.to
import com.prof.rssparser.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val CACHE_EXPIRATION_MILLIS: Long = 60 * 60 * 1000

class MainModel(application: Application) : AndroidViewModel(application) {
    val articles: LiveData<List<RssEntry>>
        get() = _articles
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _articles = MutableLiveData<List<RssEntry>>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val db = AppDatabase.newInstance(application.applicationContext)
    private val parser = Parser.Builder()
        .context(application.applicationContext)
        .cacheExpirationMillis(CACHE_EXPIRATION_MILLIS)
        .build()

    fun fetchFeeds() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val subscriptionDao = db.subscriptionDao()
            val articles = subscriptionDao.getAll().flatMap { subscription ->
                parser.getChannel(subscription.feedLink).articles.map {
                    it.to(subscription.name)
                }
            }.sortedByDescending { it.date }
            _articles.postValue(articles)
            _isLoading.postValue(false)
        }
    }
}