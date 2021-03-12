package com.github.eprendre.sources_by_eprendre

import com.github.eprendre.tingshu.extensions.config
import com.github.eprendre.tingshu.extensions.getMobileUA
import com.github.eprendre.tingshu.sources.AudioUrlExtractor
import com.github.eprendre.tingshu.sources.AudioUrlWebViewSniffExtractor
import com.github.eprendre.tingshu.sources.TingShu
import com.github.eprendre.tingshu.utils.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import org.jsoup.Jsoup
import java.net.URLEncoder

object BiliBili: TingShu() {
    private val headers = mapOf(
        "User-Agent" to getMobileUA()
    )
    override fun getSourceId(): String {
        return "c893546d95f84db194046bd8de5dbcbb"
    }

    override fun getUrl(): String {
        return "https://m.bilibili.com"
    }

    override fun getName(): String {
        return "哔哩哔哩"
    }

    override fun getDesc(): String {
        return "推荐指数:3星 ⭐⭐⭐\n b站也有一些有声小说，此源只做了搜索，不含发现。"
    }

    override fun isDiscoverable(): Boolean {
        return false
    }

    override fun search(keywords: String, page: Int): Pair<List<Book>, Int> {
        val encodedKeywords = URLEncoder.encode(keywords, "utf-8")

        val url = "https://api.bilibili.com/x/web-interface/search/all/v2?keyword=${encodedKeywords}&page=${page}&pagesize=20"
        val data = Fuel.get(url).header(headers).responseJson().third.get().obj().getJSONObject("data")
        val numPages = data.getInt("numPages")
        val list = ArrayList<Book>()
        val results = data.getJSONArray("result")
        run loop@{
            (0 until results.length()).forEach {
                val result = results.getJSONObject(it)
                if (result.getString("result_type") == "video") {
                    val videos = result.getJSONArray("data")
                    (0 until videos.length()).forEach { index ->
                        val videoObj = videos.getJSONObject(index)
                        val coverUrl = "https:" + videoObj.getString("pic")
                        val title = videoObj.getString("title")
                            .replace("<em class=\"keyword\">", "")
                            .replace("</em>", "")
                        val author = ""
                        val artist = videoObj.getString("author")
                        val bookUrl = "https://m.bilibili.com/video/" + videoObj.getString("bvid")
                        val status = "播放次数: " + videoObj.getInt("play")
                        val intro = videoObj.getString("description")

                        list.add(Book(coverUrl, bookUrl, title, author, artist).apply {
                            this.status = status
                            this.intro = intro
                            this.sourceId = getSourceId()
                        })
                    }
                    return@loop
                }
            }
        }
        return Pair(list, numPages)
    }

    override fun getAudioUrlExtractor(): AudioUrlExtractor {
        return AudioUrlWebViewSniffExtractor
    }

    override fun getCategoryMenus(): List<CategoryMenu> {
        return emptyList()
    }

    override fun getCategoryList(url: String): Category {
        return Category(emptyList(), 1, 1, "", "")
    }

    override fun getBookDetailInfo(bookUrl: String, loadEpisodes: Boolean, loadFullPages: Boolean): BookDetail {
        val episodes = ArrayList<Episode>()
        if (loadEpisodes) {
            val doc = Jsoup.connect(bookUrl).config().get()
            val elements = doc.select(".m-video-part-new > ul > li")
            if (elements.isEmpty()) {
                episodes.add(Episode("1p", bookUrl))
            } else {
                elements.forEachIndexed { index, element ->
                    val title = element.text()
                    val page = index + 1
                    episodes.add(Episode(title, "$bookUrl?p=$page"))
                }
            }
        }
        return BookDetail(episodes)
    }
}