package com.github.eprendre.tingshu.utils

data class Book(
    val coverUrl: String,//封面链接
    val bookUrl: String,//书籍链接
    val title: String,//标题
    val author: String,//作者
    val artist: String//演播
) {
    var sourceId: String? = null//源id，代表这本书属于某个源
    var id: Int? = null
    var intro: String = ""
    var currentEpisodeUrl: String? = null
    var currentEpisodeName: String? = null
    var currentEpisodePosition: Long = 0
    var skipBeginning: Long = 0
    var skipEnd: Long = 0
    var volumeBoostLevel: Int = 0
    var playOrderType: Int = 0
    var playSpeed: Float = 1f
    var isFree: Boolean = true
    var isEpisodesReversed: Boolean = false
    var episodeList: List<Episode>? = null
    var hasFullEpisodes: Boolean = false//只有 source 的 isMultipleEpisodePages 为 true 时，这个属性才起作用。
    var isShowBriefChapterTitle: Boolean = false
    var status: String = ""

}

data class Episode(val title: String, val url: String) {
    var isFree: Boolean = true
    var isCached: Boolean = false
    var progress: Int = 0
}

interface IMenu {
    fun getType(): Int
}

data class CategoryTab(
    val title: String,
    val url: String
) : IMenu {

    override fun getType(): Int {
        return 1
    }
}

data class BookDetail(
    val playList: List<Episode>,
    val intro: String? = "",
    val artist: String = "",
    val author: String = "",
    val episodesCount: Int = 0,
    val coverUrl: String = ""
)


/**
 * 大分类
 */
data class CategoryMenu(
    val title: String, //大分类标题
    val tabs: List<CategoryTab>//子分类
) : IMenu {

    override fun getType(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is CategoryMenu) {
            return false
        }

        return tabs == other.tabs
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + tabs.hashCode()
        return result
    }
}

data class Category(
    val list: List<Book>,
    val currentPage: Int,
    val totalPage: Int,
    val currentUrl: String,
    val nextUrl: String
)