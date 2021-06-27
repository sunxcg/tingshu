package com.github.eprendre.sources_by_eprendre

import com.github.eprendre.tingshu.extensions.config
import com.github.eprendre.tingshu.sources.*
import com.github.eprendre.tingshu.utils.*
import org.jsoup.Jsoup

object IShuYinTingShu : TingShu(), IAd {
    var isFirst = false

    override fun getSourceId(): String {
        return "704900fe3c7c43f6860859ac0ae32739"
    }

    override fun getUrl(): String {
        return "https://m.ishuyin.com"
    }

    override fun getName(): String {
        return "爱书音"
    }

    override fun getDesc(): String {
        return "推荐指数:4星 ⭐⭐⭐⭐"
    }

    override fun adUrl(): String {
        return ""
    }

    override fun showAdByDefault(): Boolean {
        return true
    }

    override fun isDiscoverable(): Boolean {
        return false
    }

    override fun search(keywords: String, page: Int): Pair<List<Book>, Int> {
        val url = "https://m.ishuyin.com/e/search/index.php"
        val map = mapOf("keyboard" to keywords, "show" to "title,newstext,player,playadmin",
            "submit" to "搜索")
        val doc = Jsoup.connect(url).config().data(map).post()

        val totalPage = page

        val list = ArrayList<Book>()
        val elementList = doc.select("#showajaxnews > li > a")
        elementList.forEach { element ->
            val coverUrl = element.selectFirst("img").absUrl("src")
            val bookUrl = element.absUrl("href")
            val bookInfo = element.selectFirst(".info")
            val title = bookInfo.selectFirst("h3").text()
            val author = ""
            val artist = bookInfo.selectFirst(".box > .author").text()
            val status = bookInfo.selectFirst(".box > .popu").text()
            val intro = bookInfo.selectFirst("p").text()
            list.add(Book(coverUrl, bookUrl, title, author, artist).apply {
                this.intro = intro
                this.status = status
                this.sourceId = getSourceId()
            })
        }
        return Pair(list, totalPage)
    }

    override fun getBookDetailInfo(bookUrl: String, loadEpisodes: Boolean, loadFullPages: Boolean): BookDetail {
        val episodes = ArrayList<Episode>()
        if (loadEpisodes) {
            val doc = Jsoup.connect(bookUrl).config().get()

            val l = doc.select("#playlist > ul > li a").map {
                Episode(it.text(), it.absUrl("href"))
            }
            episodes.addAll(l)
        }

        return BookDetail(episodes)
    }

    override fun getAudioUrlExtractor(): AudioUrlExtractor {
        isFirst = true
        AudioUrlWebViewExtractor.setUp { str ->
            if (isFirst) {
                Thread.sleep(3000)
                isFirst = false
            }
            val doc = Jsoup.parse(str)
            val audioElement = doc.selectFirst("#jp_audio_0")
            val url = audioElement?.attr("src")?: ""
            return@setUp url.replace("https://mp3.aikeu", "http://mp3.aikeu")
        }
        return AudioUrlWebViewExtractor
    }

    override fun getCategoryMenus(): List<CategoryMenu> {
        val menu1 = CategoryMenu(
            "有声小说", listOf(
                CategoryTab("玄幻", "https://m.ishuyin.com/list-2.html"),
                CategoryTab("都市", "https://m.ishuyin.com/list-8.html"),
                CategoryTab("文学", "https://m.ishuyin.com/list-38.html"),
                CategoryTab("武侠", "https://m.ishuyin.com/listinfo-92-0.html"),
                CategoryTab("言情", "https://m.ishuyin.com/list-1.html"),
                CategoryTab("穿越", "https://m.ishuyin.com/list-36.html"),
                CategoryTab("推理", "https://m.ishuyin.com/list-39.html"),
                CategoryTab("恐怖", "https://m.ishuyin.com/list-5.html"),
                CategoryTab("职场", "https://m.ishuyin.com/list-41.html"),
                CategoryTab("悬疑", "https://m.ishuyin.com/list-33.html"),
                CategoryTab("军事", "https://m.ishuyin.com/list-40.html"),
                CategoryTab("历史", "https://m.ishuyin.com/list-16.html")
            )
        )
        val menu2 = CategoryMenu(
            "评书分类", listOf(
                CategoryTab("单田芳", "https://m.ishuyin.com/list-42.html"),
                CategoryTab("刘兰芳", "https://m.ishuyin.com/list-43.html"),
                CategoryTab("田连元", "https://m.ishuyin.com/list-44.html"),
                CategoryTab("袁阔成", "https://m.ishuyin.com/list-45.html"),
                CategoryTab("连丽如", "https://m.ishuyin.com/list-46.html"),
                CategoryTab("张少佐", "https://m.ishuyin.com/list-47.html"),
                CategoryTab("田战义", "https://m.ishuyin.com/list-48.html"),
                CategoryTab("孙一", "https://m.ishuyin.com/list-49.html"),
                CategoryTab("袁田", "https://m.ishuyin.com/list-53.html"),
                CategoryTab("王玥波", "https://m.ishuyin.com/list-55.html"),
                CategoryTab("其他", "https://m.ishuyin.com/list-50.html")
            )
        )
        val menu3 = CategoryMenu(
            "广播剧", listOf(
                CategoryTab("耽美剧", "https://m.ishuyin.com/list-10.html"),
                CategoryTab("爱情剧", "https://m.ishuyin.com/list-12.html"),
                CategoryTab("BL广播剧", "https://m.ishuyin.com/list-14.html"),
                CategoryTab("古风剧", "https://m.ishuyin.com/list-51.html"),
                CategoryTab("校园剧", "https://m.ishuyin.com/list-52.html"),
                CategoryTab("现代剧", "https://m.ishuyin.com/list-54.html")
            )
        )
        val menu4 = CategoryMenu(
            "其他分类", listOf(
                CategoryTab("娱乐", "https://m.ishuyin.com/list-35.html"),
                CategoryTab("两性", "https://m.ishuyin.com/list-34.html"),
                CategoryTab("百家讲坛", "https://m.ishuyin.com/list-31.html"),
                CategoryTab("儿童", "https://m.ishuyin.com/list-37.html")
            )
        )
        return listOf(menu1, menu2, menu3, menu4)
    }

    override fun getCategoryList(url: String): Category {
        val doc = Jsoup.connect(url).config().get()
        val pages = doc.select(".page > a")

        var nextUrl = pages.first { it.text().contains("下一页") }.absUrl("href")
        if (nextUrl == url) {
            nextUrl = ""
        }

        //这两个只是用来判断有没有下一页，不一定要获取得到值，如果值不相等代表有下一页。
        var currentPage = 1
        var totalPage = 2
        if (nextUrl.isNullOrEmpty()) {
            currentPage = 2
            totalPage = 2
        }

        val list = ArrayList<Book>()
        val elementList = doc.select("#cateList_wap > .bookbox > a").filter { it.children().size > 0 }
        elementList.forEach { element ->
            val coverUrl = element.selectFirst(".bookimg > img").absUrl("orgsrc")
            val bookUrl = element.absUrl("href")
            val bookInfo = element.selectFirst(".bookinfo")
            val title = bookInfo.selectFirst(".bookname").text()
            var author = ""
            var artist = ""
            bookInfo.selectFirst(".author").text().split("|").let {
                if (it.size > 1) {
                    author = it[0].trim()
                    artist = it[1].trim()
                } else {
                    artist = it[0].trim()
                }
            }
            val status = bookInfo.selectFirst(".update").text()
            val intro = bookInfo.selectFirst(".intro_line").text()
            list.add(Book(coverUrl, bookUrl, title, author, artist).apply {
                this.intro = intro
                this.status = status
                this.sourceId = getSourceId()
            })
        }

        return Category(list, currentPage, totalPage, url, nextUrl)
    }
}