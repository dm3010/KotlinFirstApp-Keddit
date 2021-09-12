package com.dm3010.keddit.commons

import android.os.Parcel
import android.os.Parcelable
import com.dm3010.keddit.commons.adapter.AdapterConstants
import com.dm3010.keddit.commons.adapter.ViewType

data class RedditNews(
    val before: String,
    val after: String,
    val news: List<RedditNewsItem>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.createTypedArrayList(RedditNewsItem.CREATOR)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(before)
        parcel.writeString(after)
        parcel.writeTypedList(news)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<RedditNews> {
        override fun createFromParcel(parcel: Parcel): RedditNews = RedditNews(parcel)
        override fun newArray(size: Int): Array<RedditNews?> = arrayOfNulls(size)
    }

}

data class RedditNewsItem(
    val author: String,
    val title: String,
    val numComments: Int,
    val created: Long,
    val thumbnail: String,
    val url: String?
) : ViewType, Parcelable {

    override fun getViewType() = AdapterConstants.NEWS

    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeInt(numComments)
        parcel.writeLong(created)
        parcel.writeString(thumbnail)
        parcel.writeString(url)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<RedditNewsItem> {
        override fun createFromParcel(parcel: Parcel): RedditNewsItem = RedditNewsItem(parcel)
        override fun newArray(size: Int): Array<RedditNewsItem?> = arrayOfNulls(size)
    }
}


