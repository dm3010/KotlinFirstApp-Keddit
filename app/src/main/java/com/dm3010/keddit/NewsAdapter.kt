package com.dm3010.keddit

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import com.dm3010.keddit.commons.RedditNewsItem
import com.dm3010.keddit.commons.adapter.AdapterConstants
import com.dm3010.keddit.commons.adapter.ViewType
import com.dm3010.keddit.commons.adapter.ViewTypeDelegateAdapter
import com.dm3010.keddit.features.news.adapter.LoadingDelegateAdapter
import com.dm3010.keddit.features.news.adapter.NewsDelegateAdapter

import kotlin.collections.ArrayList


class NewsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int) = items.get(position).getViewType()

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.NEWS, NewsDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        delegateAdapters.get(viewType)!!.onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position))
            ?.onBindViewHolder(holder, this.items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addNews(news: List<RedditNewsItem>) {
        val initPosistion = items.size - 1
        items.removeAt(initPosistion)
        notifyItemRemoved(initPosistion)
        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosistion, items.size + 1)
    }

    fun clearAndAddNews(news: List<RedditNewsItem>) {
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        items.addAll(news)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    fun getNews(): List<RedditNewsItem> {
        return items
            .filter { it.getViewType() == AdapterConstants.NEWS }
            .map { it as RedditNewsItem }
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}

