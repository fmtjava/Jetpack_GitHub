package com.fmt.github.ext

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fmt.github.AppContext
import com.fmt.github.R
import com.lxj.xpopup.XPopup

fun createSortReposPopup(context: Context): LiveData<SortOption> {
    val liveData = MutableLiveData<SortOption>()//LiveData代替回调接口
    val sortOption = SortOption()
    XPopup.Builder(context)
        .asBottomList(
            context.getString(R.string.sort_options), arrayOf(
                context.getString(R.string.best_match),
                context.getString(R.string.most_stars),
                context.getString(R.string.fewest_stars),
                context.getString(R.string.most_forks),
                context.getString(R.string.fewest_forks),
                context.getString(R.string.recently_updated),
                context.getString(R.string.least_recently_updated)
            )
        ) { position, _ ->
            run {
                when (position) {
                    0 -> {
                        sortOption.sort = ""
                        sortOption.order = context.getString(R.string.desc)
                    }
                    1 -> {
                        sortOption.sort = context.getString(R.string.stars)
                        sortOption.order = context.getString(R.string.desc)
                    }
                    2 -> {
                        sortOption.sort = context.getString(R.string.stars)
                        sortOption.order = context.getString(R.string.asc)
                    }

                    3 -> {
                        sortOption.sort = context.getString(R.string.forks)
                        sortOption.order = context.getString(R.string.desc)
                    }
                    4 -> {
                        sortOption.sort = context.getString(R.string.forks)
                        sortOption.order = context.getString(R.string.asc)
                    }
                    5 -> {
                        sortOption.sort = context.getString(R.string.updated)
                        sortOption.order = context.getString(R.string.desc)
                    }
                    6 -> {
                        sortOption.sort = context.getString(R.string.updated)
                        sortOption.order = context.getString(R.string.asc)
                    }
                }
                liveData.postValue(sortOption)
            }

        }.show()
    return liveData
}

fun createSortUsesPopup(context: Context): LiveData<SortOption> {
    val liveData = MutableLiveData<SortOption>()
    val sortOption = SortOption()
    XPopup.Builder(context)
        .asBottomList(
            context.getString(R.string.sort_options), arrayOf(
                context.getString(R.string.best_match), context.getString(R.string.most_followers),
                context.getString(R.string.fewest_followers), context.getString(R.string.most_recently_joined),
                context.getString(R.string.least_recently_joined), context.getString(R.string.most_repositories),
                context.getString(R.string.fewest_repositories)
            )
        ) { position, _ ->
            run {
                when (position) {
                    0 -> {
                        sortOption.sort = ""
                        sortOption.order = context.getString(R.string.desc)
                    }
                    1 -> {
                        sortOption.sort = context.getString(R.string.followers)
                        sortOption.order = context.getString(R.string.desc)
                    }
                    2 -> {
                        sortOption.sort = context.getString(R.string.followers)
                        sortOption.order = context.getString(R.string.asc)
                    }

                    3 -> {
                        sortOption.sort = context.getString(R.string.joined)
                        sortOption.order = context.getString(R.string.desc)
                    }
                    4 -> {
                        sortOption.sort = context.getString(R.string.joined)
                        sortOption.order = context.getString(R.string.asc)
                    }
                    5 -> {
                        sortOption.sort = context.getString(R.string.repositories)
                        sortOption.order = context.getString(R.string.desc)
                    }
                    6 -> {
                        sortOption.sort = context.getString(R.string.repositories)
                        sortOption.order = context.getString(R.string.asc)
                    }
                }
                liveData.postValue(sortOption)
            }

        }.show()
    return liveData
}

class SortOption(var sort: String = "", var order: String = AppContext.getString(R.string.desc))