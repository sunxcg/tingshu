package com.github.eprendre.sources_by_eprendre

import com.github.eprendre.tingshu.extensions.config
import com.github.eprendre.tingshu.sources.AudioUrlExtractor
import com.github.eprendre.tingshu.sources.AudioUrlWebViewExtractor
import com.github.eprendre.tingshu.sources.TingShu
import com.github.eprendre.tingshu.utils.*
import org.jsoup.Jsoup
import java.net.URLEncoder

object I275 : TingShu() {
    override fun getSourceId(): String {
        return "74124d55c3b541788551204d7f893e89"
    }

    override fun getUrl(): String {
        return "http://www.i275.com/"
    }

    override fun getName(): String {
        return "275听书网"
    }

    override fun getDesc(): String {
        return "推荐指数:4星 ⭐⭐⭐⭐"
    }

    override fun search(keywords: String, page: Int): Pair<List<Book>, Int> {
        val encodedKeywords = URLEncoder.encode(keywords, "utf-8")
        val url = "http://www.i275.com/pc/index/search.html?keyword=${encodedKeywords}&submit="
        val doc = Jsoup.connect(url).config(false).get()
        val totalPage = 1

        val list = ArrayList<Book>()
        val elementList = doc.select(".yun-list > li > a")
        elementList.forEach { element ->
            val coverUrl = element.selectFirst(".img > img").attr("data-original")
            val bookUrl = element.absUrl("href")
            val title = element.selectFirst(".text > .name").text()
            val actor = element.selectFirst(".text > .actor").text().split(",")
            val author = actor[0]
            val artist = actor[1]
            val intro = element.selectFirst(".img > .bgb > .other").text()
            list.add(Book(coverUrl, bookUrl, title, author, artist).apply {
                this.intro = intro
                this.sourceId = getSourceId()
            })
        }

        return Pair(list, totalPage)
    }

    override fun getAudioUrlExtractor(): AudioUrlExtractor {
        AudioUrlWebViewExtractor.setUp(script = "audio.src", parse = { s ->
            return@setUp s.replace("\"", "")
        })
        return AudioUrlWebViewExtractor
    }

    override fun getCategoryMenus(): List<CategoryMenu> {
        val menu1 = CategoryMenu(
            "有声小说", listOf(
                CategoryTab("玄幻奇幻", "http://www.i275.com/category/6.html"),
                CategoryTab("都市言情", "http://www.i275.com/category/7.html"),
                CategoryTab("宫斗女频", "http://www.i275.com/category/8.html"),
                CategoryTab("官场商战", "http://www.i275.com/category/9.html"),
                CategoryTab("武侠仙侠", "http://www.i275.com/category/10.html"),
                CategoryTab("刑侦推理", "http://www.i275.com/category/11.html"),
                CategoryTab("探险科幻", "http://www.i275.com/category/12.html"),
                CategoryTab("重生穿越", "http://www.i275.com/category/13.html"),
                CategoryTab("恐怖惊悚", "http://www.i275.com/category/14.html"),
                CategoryTab("文学历史", "http://www.i275.com/category/15.html"),
                CategoryTab("两性情感", "http://www.i275.com/category/49.html"),
                CategoryTab("网游竞技", "http://www.i275.com/category/50.html")
            )
        )
        return listOf(menu1)
    }

    override fun getCategoryList(url: String): Category {
        val doc = Jsoup.connect(url).config(false).get()
        var totalPage = 1
        var nextUrl = ""

        val pages = doc.select(".pagination > li")
        val currentPage = pages.first { it.hasClass("active") }.text().toInt()
        if (!pages.last().hasClass("disabled")) {
            nextUrl = pages.last().child(0).absUrl("href")
            totalPage = currentPage + 1
        }

        val list = ArrayList<Book>()
        val elementList = doc.select(".yun-list > li > a")
        elementList.forEach { element ->
            val coverUrl = element.selectFirst(".img > img").attr("data-original")
            val bookUrl = element.absUrl("href")
            val title = element.selectFirst(".text > .name").text()
            val actor = element.selectFirst(".text > .actor").text().split(",")
            val author = actor[0]
            val artist = actor[1]
            val intro = element.selectFirst(".img > .bgb > .other").text()
            list.add(Book(coverUrl, bookUrl, title, author, artist).apply {
                this.intro = intro
                this.sourceId = getSourceId()
            })
        }
        return Category(list, currentPage, totalPage, url, nextUrl)
    }

    override fun getBookDetailInfo(bookUrl: String, loadEpisodes: Boolean, loadFullPages: Boolean): BookDetail {
        val episodes = ArrayList<Episode>()
        if (loadEpisodes) {
            val doc = Jsoup.connect(bookUrl).config(false).get()
            doc.select(".playul > li > a").forEach {
                episodes.add(Episode(it.text(), it.absUrl("href")))
            }
        }
        return BookDetail(episodes)
    }
}