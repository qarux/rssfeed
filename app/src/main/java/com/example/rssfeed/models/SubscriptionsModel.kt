package com.example.rssfeed.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rssfeed.database.AppDatabase
import com.example.rssfeed.database.entities.Subscription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubscriptionsModel(application: Application) : AndroidViewModel(application) {
    val subscriptions: LiveData<List<Subscription>>
        get() = _subscriptions

    private val _subscriptions = MutableLiveData<List<Subscription>>()
    private val db = AppDatabase.newInstance(application.applicationContext)

    fun loadSubscriptions() {
        viewModelScope.launch(Dispatchers.IO) {
            val subscriptionDao = db.subscriptionDao()
            val subscriptions = subscriptionDao.getAll()
            _subscriptions.postValue(subscriptions)
        }
    }

    fun deleteSubscription(subscription: Subscription) {
        _subscriptions.postValue(_subscriptions.value?.filter { it.id != subscription.id })

        viewModelScope.launch(Dispatchers.IO) {
            val subscriptionDao = db.subscriptionDao()
            subscriptionDao.delete(subscription)
        }
    }

    fun addFeed(name: String, url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val subscriptionDao = db.subscriptionDao()
            subscriptionDao.insert(Subscription(name = name, feedLink = url))
            loadSubscriptions()
        }
    }
}