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
