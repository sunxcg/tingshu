package com.github.eprendre.sources_by_eprendre

import com.github.eprendre.tingshu.extensions.splitQuery
import com.github.eprendre.tingshu.sources.AudioUrlDirectExtractor
import com.github.eprendre.tingshu.sources.AudioUrlExtractor
import com.github.eprendre.tingshu.sources.TingShu
import com.github.eprendre.tingshu.utils.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder

/**
 * 米兔阅读
 */
object YouTuYueDu : TingShu() {
    private val headers = mapOf(
        "devicetype" to "3",
        "channelname" to "official",
        "origin" to "https://www.mituyuedu.com",
        "Referer" to "https://www.mituyuedu.com",
        "seq" to "11111111111111111111111111111111",
        "User-Agent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36",
        "version" to "1.9.0"
    )

    override fun getSourceId(): String {
        return "41aa5aff6ee54089a09ef74a4adb3a77"
    }

    override fun getUrl(): String {
        return "https://www.mituyuedu.com"
    }

    override fun getName(): String {
        return "有兔阅读"
    }

    override fun getDesc(): String {
        return "有兔阅读 - 文学魅力 不止于此"
    }

    override fun getCategoryMenus(): List<CategoryMenu> {
        val list = ArrayList<CategoryMenu>()

        val url = "https://app1.youzibank.com/audio/book/cls/list"
        val jsonObject = Fuel.get(url)
            .header(headers)
            .responseJson()
            .third.get().obj()

        val data = jsonObject.getJSONArray("data")
        (0 until data.length()).forEach { i ->
            val obj = data.getJSONObject(i)
            val name = obj.getString("clsName")
            val code = obj.getInt("code")
            val subCls = obj.getJSONArray("subCls")
            val subMenu =  (0 until subCls.length()).map { t ->
                val subObj = subCls.getJSONObject(t)
                val subName = subObj.getString("clsName")
                val subCode = subObj.getInt("code")
                val parentCode = subObj.getInt("parentCode")
                CategoryTab(subName, "https://app1.youzibank.com/audio/list?fullFlag=2&orderBy=play_cnt&clsIdFirst=${parentCode}&clsIdSecond=${subCode}&pageNo=1&pageSize=10&page=1&size=10")
            }
            list.add(CategoryMenu(name, subMenu))
        }
        return list
    }

    override fun getAudioUrlExtractor(): AudioUrlExtractor {
        return AudioUrlDirectExtractor
    }

    override fun getBookDetailInfo(bookUrl: String, loadEpisodes: Boolean, loadFullPages: Boolean): BookDetail {
        val episodes = ArrayList<Episode>()
        if (loadEpisodes) {
            val jsonObject = Fuel.get(bookUrl)
                .header(headers)
                .responseJson()
                .third.get().obj()
            val data = jsonObject.getJSONArray("data")
            (0 until data.length()).forEach {
                val item = data.getJSONObject(it)
                val name = item.getString("name")
                val musicPath = JSONObject(item.getString("musicPath")).getJSONObject("m").getString("addr")
                val url = "https://video1.jiuhew.com/klajdfiaoj/music_collect${musicPath}"
                episodes.add(Episode(name, url))
            }
        }
        return BookDetail(episodes)
    }

    override fun getCategoryList(url: String): Category {
        val jsonObject = Fuel.get(url)
            .header(headers)
            .responseJson()
            .third.get().obj()

        val currentPage = jsonObject.getInt("pageNo")
        val pageCount = jsonObject.getInt("pageCount")

        val list = ArrayList<Book>()
        val nextUrl = if (currentPage < pageCount) {
            val queryMap = splitQuery(URL(url))
            queryMap["pageNo"] = (currentPage + 1).toString()
            queryMap["page"] = (currentPage + 1).toString()
            "https://app1.youzibank.com/audio/list?" + queryMap.map { "${it.key}=${it.value}" }.joinToString("&")
        } else {
            ""
        }

        val data = jsonObject.getJSONArray("data")
        (0 until data.length()).forEach { i ->
            val item = data.getJSONObject(i)
            val coverUrl = "https://img.dayouzh.com/klajdfiaoj/music_collect${item.getString("photoPath")}"
            val bookUrl = "https://app1.youzibank.com/audio/chapter/listAll?audioId=${item.getInt("id")}"
            val title = item.getString("name")
            val author = ""
            val artist = item.getString("actorName")
            val status = "共 ${item.getInt("chapterCnt")}章"
            val intro = item.getString("intro")
            list.add(
                Book(coverUrl, bookUrl, title, author, artist ).apply {
                    this.status = status
                    this.intro = intro
                    this.sourceId = getSourceId()
                }
            )
        }

        return Category(list, currentPage, pageCount, url, nextUrl)
    }

    override fun search(keywords: String, page: Int): Pair<List<Book>, Int> {
        val encodedKeywords = URLEncoder.encode(keywords, "utf-8")
        val url = "https://app1.youzibank.com/es/search/audio?q=${encodedKeywords}&pageSize=10&pageNo=${page}&page=${page}&size=10"
        val jsonObject = Fuel.get(url)
            .header(headers)
            .responseJson()
            .third.get().obj()

        val pageCount = jsonObject.getInt("pageCount")
        val list = ArrayList<Book>()

        val data = jsonObject.getJSONArray("data")
        (0 until data.length()).forEach { i ->
            val item = data.getJSONObject(i)
            val coverUrl = "https://img.dayouzh.com/klajdfiaoj/music_collect${item.getString("photoPath")}"
            val bookUrl = "https://app1.youzibank.com/audio/chapter/listAll?audioId=${item.getInt("id")}"
            val title = item.getString("name")
            val author = ""
            val artist = item.getString("actorName")
            val status = "共 ${item.getInt("chapterCnt")}章"
            val intro = item.getString("intro")
            list.add(
                Book(coverUrl, bookUrl, title, author, artist).apply {
                    this.status = status
                    this.intro = intro
                    this.sourceId = getSourceId()
                }
            )
        }

        return Pair(list, pageCount)
    }
}
