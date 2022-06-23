package com.example.rssfeed

import com.prof.rssparser.Article
import java.text.SimpleDateFormat
import java.util.*

data class RssEntry(
    val feedName: String,
    val title: String?,
    val description: String?,
    val imageUrl: String?,
    val date: Date?,
    val categories: String,
    val link: String?
)

fun Article.to(feedName: String): RssEntry {
    val sourceSdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
    val date = if (pubDate != null) sourceSdf.parse(pubDate!!) else null
    val categories = categories.joinToString()
    return RssEntry(feedName, title, description, image, date, categories, link)
}