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
        return "推荐指数:2星 ⭐⭐\n央视直播，但是只有声音，适合用来听新闻联播。由于是直播，请不要开加速播放。"
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
        return Category(list, currentPage, totalPage, url, "")
    }

    override fun getBookDetailInfo(bookUrl: String, loadEpisodes: Boolean, loadFullPages: Boolean): BookDetail {
        val episodes = mutableListOf<Episode>()
        if (loadEpisodes) {
            liveList.forEach {
                episodes.add(Episode(it.first, it.second))
            }
        }
        return BookDetail(episodes)
    }
}