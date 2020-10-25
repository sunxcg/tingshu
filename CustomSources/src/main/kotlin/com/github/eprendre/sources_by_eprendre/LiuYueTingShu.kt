package com.github.eprendre.sources_by_eprendre

import com.github.eprendre.tingshu.extensions.config
import com.github.eprendre.tingshu.sources.*
import com.github.eprendre.tingshu.utils.*
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import org.jsoup.Jsoup
import java.net.URI
import java.net.URLEncoder

object LiuYueTingShu : TingShu(), IAd, AudioUrlExtraHeaders {
    override fun getSourceId(): String {
        return "7afbfd733f0b4be7abc80cfed5d66a36"
    }

    override fun getUrl(): String {
        return "http://m.6yueting.com"
    }

    override fun getName(): String {
        return "6月听书"
    }

    override fun getDesc(): String {
        return "服务器位于: 广州\n有广告，点击广告就是对网站的支持。"
    }

    override fun adUrl(): String {
        return ""
    }

    override fun showAdByDefault(): Boolean {
        return true
    }

    override fun search(keywords: String, page: Int): Pair<List<Book>, Int> {
        val encodedKeywords = URLEncoder.encode(keywords, "utf-8")
        val url = "http://m.6yueting.com/web/index/search?content=$encodedKeywords&type=1&pageNum=$page&pageSize=20"
        val list = ArrayList<Book>()
        val jsonObject = Fuel.get(url).responseJson().third.get().obj()
        val data = jsonObject.getJSONObject("data")
        val totalPage = data.optInt("pages", 1)

        val jsonArray = data.getJSONArray("list")
        (0 until jsonArray.length()).forEach {
            val book = jsonArray.getJSONObject(it)
            val coverUrl = "http://img.6yueting.com:20001/${book.getString("coverUrlLocal")}"
            val bookUrl = "http://m.6yueting.com/list/${book.getString("code")}"
            val title = book.getString("name")
            val author = "原著: " + if (book.isNull("author")) "未知" else book.getString("author")
            val artist = "主播: " + if (book.isNull("broadcaster")) "未知" else book.getString("broadcaster")
            val intro = book.getString("descXx").trim()
            val state = book.getInt("state")
            var status = if (state == 2) {
                "完结|"
            } else {
                "更新到"
            }
            status += "${book.getInt("trackTotalCount")}集"
            list.add(Book(coverUrl, bookUrl, title, author, artist).apply {
                this.intro = intro
                this.status = status
                this.sourceId = getSourceId()
            })
        }
        return Pair(list, totalPage)
    }

    override fun getBookDetailInfo(bookUrl: String, loadEpisodes: Boolean, loadFullPages: Boolean): BookDetail {
        val list = ArrayList<Episode>()
        if (loadEpisodes) {
            val doc = Jsoup.connect(bookUrl).config(false).get()
            val episodes = doc.select(".book-list > .list-item").map {
                Episode(it.text(), it.absUrl("href"))
            }
            list.addAll(episodes)
        }
        return BookDetail(list)
    }

    override fun getAudioUrlExtractor(): AudioUrlExtractor {
        AudioUrlWebViewExtractor.setUp(isDeskTop = false) { html ->
            val doc = Jsoup.parse(html)
            val audioElement = doc.selectFirst("#audio")
            return@setUp audioElement?.attr("src")
        }
        return AudioUrlWebViewExtractor
    }

    override fun getCategoryMenus(): List<CategoryMenu> {
        val menu1 = CategoryMenu(
            "小说", listOf(
                CategoryTab("玄幻奇幻", "http://m.6yueting.com/ys/t1/o1/p1"),
                CategoryTab("修真武侠", "http://m.6yueting.com/ys/t2/o1/p1"),
                CategoryTab("恐怖灵异", "http://m.6yueting.com/ys/t3/o1/p1"),
                CategoryTab("古今言情", "http://m.6yueting.com/ys/t4/o1/p1"),
                CategoryTab("都市言情", "http://m.6yueting.com/ys/t28/o1/p1"),
                CategoryTab("穿越有声", "http://m.6yueting.com/ys/t5/o1/p1"),
                CategoryTab("粤语古仔", "http://m.6yueting.com/ys/t6/o1/p1"),
                CategoryTab("网游小说", "http://m.6yueting.com/ys/t7/o1/p1"),
                CategoryTab("通俗文学", "http://m.6yueting.com/ys/t11/o1/p1"),
                CategoryTab("历史纪实", "http://m.6yueting.com/ys/t12/o1/p1"),
                CategoryTab("军事", "http://m.6yueting.com/ys/t13/o1/p1"),
                CategoryTab("悬疑推理", "http://m.6yueting.com/ys/t14/o1/p1")
            )
        )

        val menu2 = CategoryMenu(
            "其它", listOf(
                CategoryTab("评书大全", "http://m.6yueting.com/ys/t8/o1/p1"),
                CategoryTab("相声小品", "http://m.6yueting.com/ys/t9/o1/p1"),
                CategoryTab("百家讲坛", "http://m.6yueting.com/ys/t10/o1/p1"),
                CategoryTab("官场商战", "http://m.6yueting.com/ys/t15/o1/p1"),
                CategoryTab("儿童读物", "http://m.6yueting.com/ys/t16/o1/p1"),
                CategoryTab("广播剧", "http://m.6yueting.com/ys/t17/o1/p1"),
                CategoryTab("ebc5系列", "http://m.6yueting.com/ys/t18/o1/p1"),
                CategoryTab("商业", "http://m.6yueting.com/ys/t19/o1/p1"),
                CategoryTab("生活", "http://m.6yueting.com/ys/t20/o1/p1"),
                CategoryTab("教材", "http://m.6yueting.com/ys/t21/o1/p1"),
                CategoryTab("外文原版", "http://m.6yueting.com/ys/t22/o1/p1"),
                CategoryTab("期刊杂志", "http://m.6yueting.com/ys/t23/o1/p1"),
                CategoryTab("脱口秀", "http://m.6yueting.com/ys/t27/o1/p1"),
                CategoryTab("戏曲", "http://m.6yueting.com/ys/t24/o1/p1")
            )
        )
        return listOf(menu1, menu2)

    }

    override fun getCategoryList(url: String): Category {
        val doc = Jsoup.connect(url).config(false).get()
        val nextUrl = doc.selectFirst(".pagination > .next")?.absUrl("href") ?: ""
        val currentPage = url.split("/p")[1].toInt()
        val totalPage = doc.selectFirst(".pagination > span").text().split("/")[1].toInt()

        val list = ArrayList<Book>()

        val elementList = doc.select(".home-list > .list-wrapper > ul > a")
        elementList.forEach { element ->
            val bookUrl = element.absUrl("href")
            val coverUrl = element.selectFirst(".item > .icon > img").absUrl("src")
            val title = element.selectFirst(".item > .text > .name").ownText()
            var status = element.selectFirst(".item > .text > .name > .status").text()
            status += " ${element.selectFirst(".item").attr("data-num")} 集"
            val author = "原著: " + element.selectFirst(".item").attr("data-author")
            val artist = "主播: " + element.selectFirst(".broadcaster")
                .html().split("<i class=\"icon-broad\"></i>")[1]
                .split("<span class=\"split\">")[0]
            val intro = element.select(".desc").text().trim()
            list.add(Book(coverUrl, bookUrl, title, author, artist).apply {
                this.intro = intro
                this.status = status
                this.sourceId = getSourceId()
            })
        }

        return Category(list, currentPage, totalPage, url, nextUrl)
    }

    override fun headers(audioUrl: String): Map<String, String> {
        val hashMap = hashMapOf<String, String>()
        val headersRequired = listOf("audio.ting985.com", "6yueting.com", "5txs.net").any {
            audioUrl.contains(it)
        }
        if (headersRequired) {
            hashMap["Host"] = URI(audioUrl).host
            hashMap["Referer"] = "http://m.6yueting.com/"
        }
        return hashMap
    }
}