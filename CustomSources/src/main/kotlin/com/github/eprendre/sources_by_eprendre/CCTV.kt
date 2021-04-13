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
            "CCTV-1高清" to "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8",
            "CCTV-2高清" to "http://ivi.bupt.edu.cn/hls/cctv2hd.m3u8",
            "CCTV-3高清" to "http://ivi.bupt.edu.cn/hls/cctv3hd.m3u8",
            "CCTV-4高清" to "http://ivi.bupt.edu.cn/hls/cctv4hd.m3u8",
            "CCTV-5+高清" to "http://ivi.bupt.edu.cn/hls/cctv5phd.m3u8",
            "CCTV-6高清" to "http://ivi.bupt.edu.cn/hls/cctv6hd.m3u8",
            "CCTV-7高清" to "http://ivi.bupt.edu.cn/hls/cctv7hd.m3u8",
            "CCTV-8高清" to "http://ivi.bupt.edu.cn/hls/cctv8hd.m3u8",
            "CCTV-9高清" to "http://ivi.bupt.edu.cn/hls/cctv9hd.m3u8",
            "CCTV-10高清" to "http://ivi.bupt.edu.cn/hls/cctv10hd.m3u8",
            "CCTV-12高清" to "http://ivi.bupt.edu.cn/hls/cctv12hd.m3u8",
            "CCTV-13高清" to "http://ivi.bupt.edu.cn/hls/cctv13hd.m3u8",
            "CCTV-14高清" to "http://ivi.bupt.edu.cn/hls/cctv14hd.m3u8",
            "CCTV-17高清" to "http://ivi.bupt.edu.cn/hls/cctv17hd.m3u8",
            "CGTN高清" to "http://ivi.bupt.edu.cn/hls/cgtnhd.m3u8",
            "CGTN DOC高清" to "http://ivi.bupt.edu.cn/hls/cgtndochd.m3u8",
            "CHC高清电影" to "http://ivi.bupt.edu.cn/hls/chchd.m3u8",
            "北京卫视高清" to "http://ivi.bupt.edu.cn/hls/btv1hd.m3u8",
            "北京文艺高清" to "http://ivi.bupt.edu.cn/hls/btv2hd.m3u8",
            "北京影视高清" to "http://ivi.bupt.edu.cn/hls/btv4hd.m3u8",
            "北京新闻高清" to "http://ivi.bupt.edu.cn/hls/btv9hd.m3u8",
            "北京卡酷少儿高清" to "http://ivi.bupt.edu.cn/hls/btv9hd.m3u8",
            "北京冬奥纪实高清" to "http://ivi.bupt.edu.cn/hls/btv11hd.m3u8",
            "湖南卫视高清" to "http://ivi.bupt.edu.cn/hls/hunanhd.m3u8",
            "江苏卫视高清" to "http://ivi.bupt.edu.cn/hls/jshd.m3u8",
            "东方卫视高清" to "http://ivi.bupt.edu.cn/hls/dfhd.m3u8",
            "安徽卫视高清" to "http://ivi.bupt.edu.cn/hls/ahhd.m3u8",
            "黑龙江卫视高清" to "http://ivi.bupt.edu.cn/hls/hljhd.m3u8",
            "辽宁卫视高清" to "http://ivi.bupt.edu.cn/hls/lnhd.m3u8",
            "深圳卫视高清" to "http://ivi.bupt.edu.cn/hls/szhd.m3u8",
            "广东卫视高清" to "http://ivi.bupt.edu.cn/hls/gdhd.m3u8",
            "天津卫视高清" to "http://ivi.bupt.edu.cn/hls/tjhd.m3u8",
            "湖北卫视高清" to "http://ivi.bupt.edu.cn/hls/hbhd.m3u8",
            "山东卫视高清" to "http://ivi.bupt.edu.cn/hls/sdhd.m3u8",
            "重庆卫视高清" to "http://ivi.bupt.edu.cn/hls/cqhd.m3u8",
            "上海纪实高清" to "http://ivi.bupt.edu.cn/hls/docuchina.m3u8",
            "金鹰纪实高清" to "http://ivi.bupt.edu.cn/hls/gedocu.m3u8",
            "福建东南卫视高清" to "http://ivi.bupt.edu.cn/hls/dnhd.m3u8",
            "四川卫视高清" to "http://ivi.bupt.edu.cn/hls/schd.m3u8",
            "河北卫视高清" to "http://ivi.bupt.edu.cn/hls/hebhd.m3u8",
            "江西卫视高清" to "http://ivi.bupt.edu.cn/hls/jxhd.m3u8",
            "河南卫视高清" to "http://ivi.bupt.edu.cn/hls/hnhd.m3u8",
            "广西卫视高清" to "http://ivi.bupt.edu.cn/hls/gxhd.m3u8",
            "吉林卫视高清" to "http://ivi.bupt.edu.cn/hls/jlhd.m3u8",
            "CETV-1高清" to "http://ivi.bupt.edu.cn/hls/cetv1hd.m3u8",
            "海南卫视高清" to "http://ivi.bupt.edu.cn/hls/lyhd.m3u8",
            "贵州卫视高清" to "http://ivi.bupt.edu.cn/hls/gzhd.m3u8",
            "CCTV-1综合" to "http://ivi.bupt.edu.cn/hls/cctv1.m3u8",
            "CCTV-2财经" to "http://ivi.bupt.edu.cn/hls/cctv2.m3u8",
            "CCTV-3综艺" to "http://ivi.bupt.edu.cn/hls/cctv3.m3u8",
            "CCTV-4中文国际" to "http://ivi.bupt.edu.cn/hls/cctv4.m3u8",
            "CCTV-6电影" to "http://ivi.bupt.edu.cn/hls/cctv6.m3u8",
            "CCTV-7国防军事" to "http://ivi.bupt.edu.cn/hls/cctv7.m3u8",
            "CCTV-8电视剧" to "http://ivi.bupt.edu.cn/hls/cctv8.m3u8",
            "CCTV-9纪录" to "http://ivi.bupt.edu.cn/hls/cctv9.m3u8",
            "CCTV-10科教" to "http://ivi.bupt.edu.cn/hls/cctv10.m3u8",
            "CCTV-11戏曲" to "http://ivi.bupt.edu.cn/hls/cctv11.m3u8",
            "CCTV-12社会与法" to "http://ivi.bupt.edu.cn/hls/cctv12.m3u8",
            "CCTV-13新闻" to "http://ivi.bupt.edu.cn/hls/cctv13.m3u8",
            "CCTV-14少儿" to "http://ivi.bupt.edu.cn/hls/cctv14.m3u8",
            "CCTV-15音乐" to "http://ivi.bupt.edu.cn/hls/cctv15.m3u8",
            "CGTN" to "http://ivi.bupt.edu.cn/hls/cctv16.m3u8",
            "CCTV-17农业农村" to "http://ivi.bupt.edu.cn/hls/cctv17.m3u8",
            "北京卫视" to "http://ivi.bupt.edu.cn/hls/btv1.m3u8",
            "北京文艺" to "http://ivi.bupt.edu.cn/hls/btv2.m3u8",
            "北京科教" to "http://ivi.bupt.edu.cn/hls/btv3.m3u8",
            "北京影视" to "http://ivi.bupt.edu.cn/hls/btv4.m3u8",
            "北京财经" to "http://ivi.bupt.edu.cn/hls/btv5.m3u8",
            "北京生活" to "http://ivi.bupt.edu.cn/hls/btv7.m3u8",
            "北京青年" to "http://ivi.bupt.edu.cn/hls/btv8.m3u8",
            "北京新闻" to "http://ivi.bupt.edu.cn/hls/btv9.m3u8",
            "北京卡酷少儿" to "http://ivi.bupt.edu.cn/hls/btv10.m3u8",
            "北京冬奥纪实" to "http://ivi.bupt.edu.cn/hls/btv11.m3u8",
            "湖南卫视" to "http://ivi.bupt.edu.cn/hls/hunantv.m3u8",
            "江苏卫视" to "http://ivi.bupt.edu.cn/hls/jstv.m3u8",
            "东方卫视" to "http://ivi.bupt.edu.cn/hls/dftv.m3u8",
            "深圳卫视" to "http://ivi.bupt.edu.cn/hls/sztv.m3u8",
            "安徽卫视" to "http://ivi.bupt.edu.cn/hls/ahtv.m3u8",
            "河南卫视" to "http://ivi.bupt.edu.cn/hls/hntv.m3u8",
            "陕西卫视" to "http://ivi.bupt.edu.cn/hls/sxtv.m3u8",
            "吉林卫视" to "http://ivi.bupt.edu.cn/hls/jltv.m3u8",
            "广东卫视" to "http://ivi.bupt.edu.cn/hls/gdtv.m3u8",
            "山东卫视" to "http://ivi.bupt.edu.cn/hls/sdtv.m3u8",
            "湖北卫视" to "http://ivi.bupt.edu.cn/hls/hbtv.m3u8",
            "广西卫视" to "http://ivi.bupt.edu.cn/hls/gxtv.m3u8",
            "河北卫视" to "http://ivi.bupt.edu.cn/hls/hebtv.m3u8",
            "西藏卫视" to "http://ivi.bupt.edu.cn/hls/xztv.m3u8",
            "内蒙古卫视" to "http://ivi.bupt.edu.cn/hls/nmtv.m3u8",
            "青海卫视" to "http://ivi.bupt.edu.cn/hls/qhtv.m3u8",
            "四川卫视" to "http://ivi.bupt.edu.cn/hls/sctv.m3u8",
            "天津卫视" to "http://ivi.bupt.edu.cn/hls/tjtv.m3u8",
            "山西卫视" to "http://ivi.bupt.edu.cn/hls/sxrtv.m3u8",
            "辽宁卫视" to "http://ivi.bupt.edu.cn/hls/lntv.m3u8",
            "厦门卫视" to "http://ivi.bupt.edu.cn/hls/xmtv.m3u8",
            "新疆卫视" to "http://ivi.bupt.edu.cn/hls/xjtv.m3u8",
            "黑龙江卫视" to "http://ivi.bupt.edu.cn/hls/hljtv.m3u8",
            "云南卫视" to "http://ivi.bupt.edu.cn/hls/yntv.m3u8",
            "江西卫视" to "http://ivi.bupt.edu.cn/hls/jxtv.m3u8",
            "福建东南卫视" to "http://ivi.bupt.edu.cn/hls/dntv.m3u8",
            "贵州卫视" to "http://ivi.bupt.edu.cn/hls/gztv.m3u8",
            "宁夏卫视" to "http://ivi.bupt.edu.cn/hls/nxtv.m3u8",
            "甘肃卫视" to "http://ivi.bupt.edu.cn/hls/gstv.m3u8",
            "重庆卫视" to "http://ivi.bupt.edu.cn/hls/cqtv.m3u8",
            "兵团卫视" to "http://ivi.bupt.edu.cn/hls/bttv.m3u8",
            "延边卫视" to "http://ivi.bupt.edu.cn/hls/ybtv.m3u8",
            "三沙卫视" to "http://ivi.bupt.edu.cn/hls/sstv.m3u8",
            "海南卫视" to "http://ivi.bupt.edu.cn/hls/lytv.m3u8",
            "CETV-1" to "http://ivi.bupt.edu.cn/hls/cetv1.m3u8",
            "CETV-3" to "http://ivi.bupt.edu.cn/hls/cetv3.m3u8",
            "CETV-4" to "http://ivi.bupt.edu.cn/hls/cetv4.m3u8",
            "山东教育" to "http://ivi.bupt.edu.cn/hls/sdetv.m3u8"
    )

    override fun getSourceId(): String {
        return "150173b652964f71806264c07245587d"
    }

    override fun getUrl(): String {
        return "https://tv.cctv.com/"
    }

    override fun getName(): String {
        return "电视直播"
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
        val menu1 = CategoryMenu("电视", listOf(
            CategoryTab("直播", "liveplay")
        ))
        return listOf(menu1)
    }

    override fun getCategoryList(url: String): Category {
        val currentPage = 1
        val totalPage = 1
        val list = ArrayList<Book>()
        list.add(Book("", "cctv", "央视(不含画面)", "", "").apply {
            this.sourceId = getSourceId()
        })
        list.add(Book("", "cctv2", "电视直播", "", "").apply {
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