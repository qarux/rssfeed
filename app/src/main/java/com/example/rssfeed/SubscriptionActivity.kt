package com.example.rssfeed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssfeed.adapters.SubscriptionAdapter
import com.example.rssfeed.database.entities.Subscription
import com.example.rssfeed.databinding.ActivitySubscriptionBinding
import com.example.rssfeed.dialogs.AddSubscriptionDialogFragment
import com.example.rssfeed.models.SubscriptionsModel

class SubscriptionActivity : AppCompatActivity() {
    private val viewModel: SubscriptionsModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = SubscriptionAdapter()
        binding.subscriptionsRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }
        binding.fab.setOnClickListener {
            val dialog = AddSubscriptionDialogFragment()
            dialog.setOnSubscriptionAddedListener { name, url ->
                viewModel.addFeed(name, url)
            }
            dialog.show(supportFragmentManager, "SubscriptionFragment")
        }

        viewModel.subscriptions.observe(this) {
            adapter.replace(it)
            adapter.setOnLongClickListener { view, subscription ->
                showPopup(view, subscription)
            }
        }
        viewModel.loadSubscriptions()
    }

    private fun showPopup(view: View, subscription: Subscription) {
        PopupMenu(this, view).apply {
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete_subscription -> {
                        viewModel.deleteSubscription(subscription)
                        true
                    }
                    else -> false
                }
            }
            inflate(R.menu.subscription)
            show()
        }
    }

    companion object {
        fun createStartActivityIntent(context: Context) =
            Intent(context, SubscriptionActivity::class.java)
    }
}