package com.example.rssfeed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.ConfigurationCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rssfeed.R
import com.example.rssfeed.RssEntry
import com.example.rssfeed.databinding.ArticleItemBinding
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    private var items: List<RssEntry> = listOf()
    private var onClickListener: ((String) -> Unit)? = null

    fun replace(articles: List<RssEntry>) {
        items = articles
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: (String) -> Unit) {
        onClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ArticleItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: RssEntry) {
            Glide.with(binding.root.context)
                .load(entry.imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.articleImage)

            binding.feedName.text = entry.feedName
            binding.title.text = entry.title
            binding.description.text = entry.description
            binding.time.text = formatDate(entry.date)
            binding.root.setOnClickListener {
                val link = entry.link
                if (link != null) {
                    onClickListener?.invoke(link)
                }
            }
        }

        private fun formatDate(date: Date?): String? {
            if (date != null) {
                return SimpleDateFormat(
                    "EEE, d MMM yyyy HH:mm:ss",
                    ConfigurationCompat.getLocales(binding.root.resources.configuration).get(0)
                ).format(date)
            }
            return null
        }
    }
}