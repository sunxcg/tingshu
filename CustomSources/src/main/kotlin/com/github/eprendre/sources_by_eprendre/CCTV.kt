package com.github.eprendre.sources_by_eprendre

import com.github.eprendre.tingshu.sources.AudioUrlDirectExtractor
import com.github.eprendre.tingshu.sources.AudioUrlExtractor
import com.github.eprendre.tingshu.sources.TingShu
import com.github.eprendre.tingshu.utils.*

object CCTV : TingShu(){
    private val liveList = listOf (
        "CCTV-1 综合" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv1_1/index.m3u8",
        "CCTV-2 财经" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv2_1/index.m3u8",
        "CCTV-3 综艺" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv3_1/index.m3u8",
        "CCTV-4 中文国际（亚）" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv4_1/index.m3u8",
        "CCTV-5 体育" to "https://cctv5txyh5c.liveplay.myqcloud.com/live/cdrmcctv5_1/index.m3u8",
        "CCTV-5+ 体育赛事" to "https://cctv5txyh5c.liveplay.myqcloud.com/live/cdrmcctv5plus_1/index.m3u8",
        "CCTV-6 电影" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv6_1/index.m3u8",
        "CCTV-7 国防军事" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv7_1/index.m3u8",
        "CCTV-8 电视剧" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv8_1/index.m3u8",
        "CCTV-9 纪录" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctvjilu_1/index.m3u8",
        "CCTV-10 科教" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv10_1/index.m3u8",
        "CCTV-11 戏曲" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv11_1/index.m3u8",
        "CCTV-12 社会与法" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv12_1/index.m3u8",
        "CCTV-13 新闻" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv13_1/index.m3u8",
        "CCTV-14 少儿" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctvchild_1/index.m3u8",
        "CCTV-15 音乐" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv15_1/index.m3u8",
        "CCTV-17 农业农村" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctv17_1/index.m3u8",
        "CCTV-4 中文国际（欧）" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctveurope_1/index.m3u8",
        "CCTV-4 中文国际（美）" to "https://cctvtxyh5c.liveplay.myqcloud.com/live/cdrmcctvamerica_1/index.m3u8"
    )

    private val list2 = listOf(
        "CCTV1" to "http://39.134.66.66/PLTV/88888888/224/3221225601/index.m3u8",
        "CCTV2" to "http://39.134.66.66/PLTV/88888888/224/3221225691/index.m3u8",
        "CCTV3" to "http://39.134.66.66/PLTV/88888888/224/3221225490/index.m3u8",
        "CCTV4" to "http://39.134.66.66/PLTV/88888888/224/3221225487/index.m3u8",
        "CCTV5" to "http://39.134.66.66/PLTV/88888888/224/3221225606/index.m3u8",
        "CCTV5+" to "http://39.134.66.66/PLTV/88888888/224/3221225590/index.m3u8",
        "CCTV6" to "http://39.134.66.66/PLTV/88888888/224/3221225488/index.m3u8",
        "CCTV7" to "http://39.134.66.66/PLTV/88888888/224/3221225671/index.m3u8",
        "CCTV8" to "http://39.134.66.66/PLTV/88888888/224/3221225485/index.m3u8",
        "CCTV9" to "http://39.134.66.66/PLTV/88888888/224/3221225676/index.m3u8",
        "CCTV10" to "http://39.134.66.66/PLTV/88888888/224/3221225677/index.m3u8",
        "CCTV11" to "http://39.134.66.66/PLTV/88888888/224/3221225517/index.m3u8",
        "CCTV12" to "http://39.134.66.66/PLTV/88888888/224/3221225669/index.m3u8",
        "CCTV13" to "http://39.134.66.66/PLTV/88888888/224/3221225515/index.m3u8",
        "CCTV14" to "http://39.134.66.66/PLTV/88888888/224/3221225674/index.m3u8",
        "CCTV15" to "http://39.134.66.66/PLTV/88888888/224/3221225513/index.m3u8",
        "CCTV17" to "http://39.134.66.66/PLTV/88888888/224/3221225708/index.m3u8"
    )

    override fun getSourceId(): String {
        return "150173b652964f71806264c07245587d"
    }

    override fun getUrl(): String {
        return "https://tv.cctv.com/"
    }

    override fun getName(): String {
        return "央视网"
    }

    override fun getDesc(): String {
        return "推荐指数:2星 ⭐⭐\n直播，请不要开加速播放。"
    }

    override fun search(keywords: String, page: Int): Pair<List<Book>, Int> {
        return Pair(emptyList(), 1)
    }

    override fun isSearchable(): Boolean {
        return false
    }

    override fun isCacheable(): Boolean {
        return false
    }

    override fun getAudioUrlExtractor(): AudioUrlExtractor {
        return AudioUrlDirectExtractor
    }

    override fun getCategoryMenus(): List<CategoryMenu> {
        val menu1 = CategoryMenu("央视直播", listOf(
            CategoryTab("直播", "liveplay")
        ))
        return listOf(menu1)
    }

    override fun getCategoryList(url: String): Category {
        val currentPage = 1
        val totalPage = 1
        val list = ArrayList<Book>()
        list.add(Book("", "cctv", "央视直播", "", "").apply {
            this.sourceId = getSourceId()
        })
        list.add(Book("", "cctv2", "央视(带视频)", "", "").apply {
            this.sourceId = getSourceId()
        })
        return Category(list, currentPage, totalPage, url, "")
    }

    override fun getBookDetailInfo(bookUrl: String, loadEpisodes: Boolean, loadFullPages: Boolean): BookDetail {
        val episodes = mutableListOf<Episode>()
        if (loadEpisodes) {
            if (bookUrl == "cctv") {
                liveList.forEach {
                    episodes.add(Episode(it.first, it.second))
                }
            } else if (bookUrl == "cctv2") {
                list2.forEach {
                    episodes.add(Episode(it.first, it.second))
                }
            }
        }
        return BookDetail(episodes)
    }
}