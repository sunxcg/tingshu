package com.github.eprendre.sources_by_eprendre

import com.github.eprendre.tingshu.extensions.config
import com.github.eprendre.tingshu.extensions.getMobileUA
import com.github.eprendre.tingshu.sources.*
import com.github.eprendre.tingshu.utils.*
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.URLEncoder

object WoTingPingShu : TingShu() {
    override fun getSourceId(): String {
        return "70865122f88048abaf77582242dcccce"
    }

    override fun getUrl(): String {
        return "https://m.5tps.me"
    }

    override fun getName(): String {
        return "我听评书网"
    }

    override fun getDesc(): String {
        return "推荐指数:1星 ⭐\n目前已无法解析"
    }

//    override fun isDiscoverable(): Boolean {
//        return false
//    }
//
//    override fun isSearchable(): Boolean {
//        return false
//    }

    override fun isWebViewNotRequired(): Boolean {
        return true
    }

    override fun search(keywords: String, page: Int): Pair<List<Book>, Int> {
        val encodedKeywords = URLEncoder.encode(keywords, "gb2312")
        val url = "https://m.5tps.me/so.asp?keyword=$encodedKeywords&page=$page"
        val doc = Jsoup.connect(url).config().get()

        var totalPage = 1
        val list = ArrayList<Book>()
        try {
            totalPage = doc.selectFirst(".booksite > .bookbutton").text().split("/")[1].toInt()
            val elementList = doc.select(".top_list > a")
            elementList.forEach { element ->
                val bookUrl = element.absUrl("href")
                val coverUrl = ""
                val title = element.ownText()
                val author = ""
                val (artist, status) = element.selectFirst(".peo").text().split("／").let {
                    Pair("播音: ${it[0]}", it[1])
                }
                list.add((Book(coverUrl, bookUrl, title, author, artist).apply {
                    this.status = status
                    this.sourceId = getSourceId()
                }))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Pair(list, totalPage)
    }

    override fun getBookDetailInfo(bookUrl: String, loadEpisodes: Boolean, loadFullPages: Boolean): BookDetail {
        val doc = Jsoup.connect(bookUrl).config().get()
        val episodes = doc.select("#playlist > ul > li > a").map {
            Episode(it.text(), it.attr("abs:href"))
        }
        val currentIntro = doc.selectFirst(".book_intro").text()
        return BookDetail(episodes, currentIntro)
    }

    override fun getAudioUrlExtractor(): AudioUrlExtractor {
        AudioUrlJsoupExtractor.setUp { doc ->
            val iUrl = doc.selectFirst("iframe").attr("src")
            val script = Jsoup.connect(iUrl).headers(mapOf(
                "accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
                "accept-encoding" to "gzip, deflate, br",
                "referer" to "https://m.5tps.com/",
                "sec-fetch-dest" to "iframe",
                "sec-fetch-mode" to "navigate",
                "sec-fetch-site" to "cross-site",
                "upgrade-insecure-requests" to "1",
                "user-agent" to getMobileUA()
            )).get().select("script").first { !it.hasAttr("src") }.toString()

            val regex = Regex("'\\+(.*?)\\+'")
            val list = regex.findAll(script).map {
                it.groupValues[1]
            }.toMutableList()
            list.remove("x")
            val map = mutableMapOf<String, String>()
            for (s in list) {
                val value = "$s = '(.+)';".toRegex().find(script)!!.groupValues[1]
                map[s] = value
            }
            var audioUrl = Regex("mp3:(.+),").find(script)!!.groupValues[1]
            map.forEach { (t, u) ->
                audioUrl = audioUrl.replace(t, u)
            }
            val sign = decode(Regex("u = \"(.+)\";").find(script)!!.groupValues[1])

            audioUrl = audioUrl.replace("'+x+'", sign)
                .replace("'", "")
                .replace("+", "")
            return@setUp audioUrl
        }
        return AudioUrlJsoupExtractor
    }

    private fun decode(str: String): String {
        return str.split("*").joinToString(separator = "") {
            if (it.isNotEmpty()) {
                it.toInt().toChar().toString()
            } else it
        }
    }

    override fun getCategoryMenus(): List<CategoryMenu> {
        val menu1 =
            CategoryMenu("评书分类", listOf(
                CategoryTab("单田芳", "https://m.5tps.me/m_l_hot/1_1.html"),
                CategoryTab("刘兰芳", "https://m.5tps.me/m_l_hot/2_1.html"),
                CategoryTab("田连元", "https://m.5tps.me/m_l_hot/3_1.html"),
                CategoryTab("袁阔成", "https://m.5tps.me/m_l_hot/4_1.html"),
                CategoryTab("连丽如", "https://m.5tps.me/m_l_hot/5_1.html"),
                CategoryTab("张少佐", "https://m.5tps.me/m_l_hot/6_1.html"),
                CategoryTab("田战义", "https://m.5tps.me/m_l_hot/7_1.html"),
                CategoryTab("孙一", "https://m.5tps.me/m_l_hot/8_1.html"),
                CategoryTab("石连君", "https://m.5tps.me/m_l_hot/29_1.html"),
                CategoryTab("马长辉", "https://m.5tps.me/m_l_hot/25_1.html"),
                CategoryTab("王军", "https://m.5tps.me/m_l_hot/27_1.html"),
                CategoryTab("王玥波", "https://m.5tps.me/m_l_hot/28_1.html"),
                CategoryTab("王封臣", "https://m.5tps.me/m_l_hot/30_1.html"),
                CategoryTab("关永超", "https://m.5tps.me/m_l_hot/35_1.html"),
                CategoryTab("昊儒", "https://m.5tps.me/m_l_hot/26_1.html"),
                CategoryTab("粤语评书", "https://m.5tps.me/m_l_hot/12_1.html"),
                CategoryTab("其他评书", "https://m.5tps.me/m_l/13_1.html"))
            )

        val menu2 = CategoryMenu("有声小说", listOf(
            CategoryTab("玄幻奇幻", "https://m.5tps.me/m_l/46_1.html"),
            CategoryTab("恐怖惊悚", "https://m.5tps.me/m_l/14_1.html"),
            CategoryTab("言情通俗", "https://m.5tps.me/m_l/19_1.html"),
            CategoryTab("武侠小说", "https://m.5tps.me/m_l/11_1.html"),
            CategoryTab("历史军事", "https://m.5tps.me/m_l/15_1.html"),
            CategoryTab("刑侦反腐", "https://m.5tps.me/m_l/16_1.html"),
            CategoryTab("官场商战", "https://m.5tps.me/m_l/17_1.html"),
            CategoryTab("人物纪实", "https://m.5tps.me/m_l/18_1.html"),
            CategoryTab("有声文学", "https://m.5tps.me/m_l/10_1.html"),
            CategoryTab("童话寓言", "https://m.5tps.me/m_l/20_1.html"),
            CategoryTab("广播剧", "https://m.5tps.me/m_l/36_1.html"),
            CategoryTab("英文读物", "https://m.5tps.me/m_l/22_1.html"))
        )

        val menu3 = CategoryMenu("综艺节目", listOf(
            CategoryTab("今日头条", "https://m.5tps.me/m_l/40_1.html"),
            CategoryTab("商业财经", "https://m.5tps.me/m_l/42_1.html"),
            CategoryTab("脱口秀", "https://m.5tps.me/m_l/41_1.html"),
            CategoryTab("亲子教育", "https://m.5tps.me/m_l/43_1.html"),
            CategoryTab("教育培训", "https://m.5tps.me/m_l/44_1.html"),
            CategoryTab("百家讲坛", "https://m.5tps.me/m_l/9_1.html"),
            CategoryTab("综艺娱乐", "https://m.5tps.me/m_l/34_1.html"),
            CategoryTab("相声小品", "https://m.5tps.me/m_l/21_1.html"),
            CategoryTab("汽车音乐", "https://m.5tps.me/m_l/23_1.html"),
            CategoryTab("时尚生活", "https://m.5tps.me/m_l/45_1.html"),
            CategoryTab("戏曲", "https://m.5tps.me/m_l/38_1.html"),
            CategoryTab("二人转", "https://m.5tps.me/m_l/31_1.html"))
        )
        return listOf(menu1, menu2, menu3)
    }

    override fun getCategoryList(url: String): Category {
        val doc = Jsoup.connect(url).config().get()
        val nextUrl = doc.select(".page > a").firstOrNull { it.text().contains("下页") }?.attr("abs:href") ?: ""
        val (currentPage, totalPage) = doc.selectFirst(".booksite > .bookbutton > a").text().let {
            val pages = it.split(" ")[1].split("/")
            Pair(pages[0].toInt(), pages[1].toInt())
        }

        val list = ArrayList<Book>()
        val elementList = doc.select(".top_list > a")
        elementList.forEach { element ->
            val bookUrl = element.absUrl("href")
            val coverUrl = ""
            val title = element.ownText()
            val author = ""
            val (artist, status) = element.selectFirst(".peo").text().split("／").let {
                Pair("播音: ${it[0]}", it[1])
            }
            list.add((Book(coverUrl, bookUrl, title, author, artist).apply {
                this.status = status
                this.sourceId = getSourceId()
            }))
        }

        return Category(list, currentPage, totalPage, url, nextUrl)
    }

}