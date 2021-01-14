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
        "CCTV1" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226226/1.m3u8",
        "CCTV2" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226230/1.m3u8",
        "CCTV3" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226471/1.m3u8",
        "CCTV4" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226314/1.m3u8",
        "CCTV5" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226469/1.m3u8",
        "CCTV5+" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226225/1.m3u8",
        "CCTV6" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226468/1.m3u8",
        "CCTV7" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226234/1.m3u8",
        "CCTV8" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226485/1.m3u8",
        "CCTV9" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226236/1.m3u8",
        "CCTV10" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226227/1.m3u8",
        "CCTV11" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226334/1.m3u8",
        "CCTV12" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226228/1.m3u8",
        "CCTV13" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226316/1.m3u8",
        "CCTV14" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226229/1.m3u8",
        "CCTV15" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226460/1.m3u8",
        "CCTV17" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226318/1.m3u8",
        "CCTV1 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226432/1.m3u8",
        "CCTV2 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225934/1.m3u8",
        "CCTV3 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226456/1.m3u8",
        "CCTV4 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226156/1.m3u8",
        "CCTV5 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226454/1.m3u8",
        "CCTV5+ 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225938/1.m3u8",
        "CCTV6 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226453/1.m3u8",
        "CCTV7 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225863/1.m3u8",
        "CCTV8 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226451/1.m3u8",
        "CCTV9 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226450/1.m3u8",
        "CCTV10 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225868/1.m3u8",
        "CCTV11 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225869/1.m3u8",
        "CCTV12 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225870/1.m3u8",
        "CCTV13 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226446/1.m3u8",
        "CCTV14 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225872/1.m3u8",
        "CCTV15 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225940/1.m3u8",
        "CCTV17 标清" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226442/1.m3u8",
        "中国教育1 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226494/1.m3u8",
        "东南卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226406/1.m3u8",
        "东方卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226237/1.m3u8",
        "北京卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226224/1.m3u8",
        "天津卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226246/1.m3u8",
        "安徽卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226223/1.m3u8",
        "山东卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225894/1.m3u8",
        "广东卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225878/1.m3u8",
        "江苏卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226242/1.m3u8",
        "江西卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226243/1.m3u8",
        "河北卫视 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226507/1.m3u8",
        "浙江卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226247/1.m3u8",
        "深圳卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225897/1.m3u8",
        "湖北卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226240/1.m3u8",
        "湖南卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226241/1.m3u8",
        "贵州卫视 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226497/1.m3u8",
        "辽宁卫视 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226500/1.m3u8",
        "黑龙江卫视 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226239/1.m3u8",
        "北京冬奥纪实 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226438/1.m3u8",
        "北京影视 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226486/1.m3u8",
        "北京文艺 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226440/1.m3u8",
        "北京新闻 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226437/1.m3u8",
        "北京卡酷少儿 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226558/1.m3u8",
        "湖南快乐垂钓 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226549/1.m3u8",
        "湖南茶频道 CQ" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226548/1.m3u8",
        "黑莓动画 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225914/1.m3u8",
        "黑莓电影 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225927/1.m3u8",
        "黑莓电竞 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225931/1.m3u8",
        "NewTV中国功夫 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225951/1.m3u8",
        "NewTV怡伴健康 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225919/1.m3u8",
        "NewTV军事评论 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225926/1.m3u8",
        "NewTV军旅剧场 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225923/1.m3u8",
        "NewTV农业致富 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225930/1.m3u8",
        "NewTV动作电影 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225915/1.m3u8",
        "NewTV古装剧场 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225916/1.m3u8",
        "NewTV家庭剧场 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225918/1.m3u8",
        "NewTV惊悚悬疑 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225921/1.m3u8",
        "NewTV明星大片 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225929/1.m3u8",
        "NewTV武搏世界 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226171/1.m3u8",
        "NewTV海外剧场 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225917/1.m3u8",
        "NewTV潮妈辣婆 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225957/1.m3u8",
        "NewTV炫舞未来 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226248/1.m3u8",
        "NewTV爱情喜剧 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225913/1.m3u8",
        "NewTV精品体育 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226287/1.m3u8",
        "NewTV精品大剧 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225925/1.m3u8",
        "NewTV精品纪录 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226293/1.m3u8",
        "NewTV超级体育 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226157/1.m3u8",
        "NewTV超级电影 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226133/1.m3u8",
        "NewTV超级电视剧 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225864/1.m3u8",
        "NewTV超级综艺 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226127/1.m3u8",
        "NewTV金牌综艺 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221225920/1.m3u8",
        "咪咕视频 HD" to "http://221.179.217.9/otttv.bj.chinamobile.com/PLTV/88888888/224/3221226374/1.m3u8",
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