package com.github.eprendre.tingshu.extensions

import org.jsoup.Connection
import java.net.URL
import java.net.URLDecoder

fun splitQuery(url: URL): LinkedHashMap<String, String> {
    val queryPairs = LinkedHashMap<String, String>()
    val query = url.query
    val pairs = query.split("&")
    for (pair in pairs) {
        val idx = pair.indexOf("=")
        queryPairs[URLDecoder.decode(pair.substring(0, idx), "UTF-8")] =
            URLDecoder.decode(pair.substring(idx + 1), "UTF-8")
    }
    return queryPairs
}

/**
 * 是否使用 PC 版的 UA，UA 字段会自动从 app 里的配置读取。
 */
fun Connection.config(isDesktop: Boolean = false): Connection {
    throw RuntimeException("Stub!")
}

/**
 * 获取 PC 版 UA
 */
fun getDesktopUA(): String {
    throw RuntimeException("Stub!")
}

/**
 * 获取手机版 UA
 */
fun getMobileUA(): String {
    throw RuntimeException("Stub!")
}

/**
 * 加载多页章节列表时用到这个
 * 调用之后相关界面上会显示：正在加载章节列表: $pageInfo
 * 如果 pageInfo 传空，代表加载完毕
 */
fun notifyLoadingEpisodes(pageInfo: String?) {
    throw RuntimeException("Stub!")
}