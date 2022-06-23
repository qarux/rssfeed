package com.example.rssfeed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rssfeed.database.entities.Subscription
import com.example.rssfeed.databinding.SubscritpionItemBinding

class SubscriptionAdapter : RecyclerView.Adapter<SubscriptionAdapter.ViewHolder>() {
    private var items: List<Subscription> = listOf()
    private var onLongClickListener: ((View, Subscription) -> Unit)? = null

    fun replace(subscriptions: List<Subscription>) {
        items = subscriptions
        notifyDataSetChanged()
    }

    fun setOnLongClickListener(listener: (View, Subscription) -> Unit) {
        onLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SubscritpionItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: SubscritpionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subscription: Subscription) {
            binding.subscriptionTextView.text = subscription.name
            binding.root.setOnLongClickListener {
                onLongClickListener?.invoke(it, subscription)
                true
            }
        }
    }
}