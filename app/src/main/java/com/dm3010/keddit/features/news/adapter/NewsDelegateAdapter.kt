package com.dm3010.keddit.features.news.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dm3010.keddit.R
import com.dm3010.keddit.commons.RedditNewsItem
import com.dm3010.keddit.commons.adapter.ViewType
import com.dm3010.keddit.commons.adapter.ViewTypeDelegateAdapter
import com.dm3010.keddit.commons.extensions.getFriendlyTime
import com.dm3010.keddit.commons.extensions.inflate
import com.dm3010.keddit.commons.extensions.loadImg
import kotlinx.android.synthetic.main.news_item.view.*

class NewsDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TurnsViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as TurnsViewHolder
        holder.bind(item as RedditNewsItem)
    }

    class TurnsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        parent.inflate(R.layout.news_item)
    ) {
        fun bind(item: RedditNewsItem) {
            with(itemView) {
                img_thumbnail.loadImg(item.thumbnail)
                description.text = item.title
                author.text = item.author
                comments.text = "${item.numComments} comments"
                time.text = item.created.getFriendlyTime()
            }
        }
    }
}
