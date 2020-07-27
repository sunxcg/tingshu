package com.github.eprendre.tingshu.sources

import com.github.eprendre.tingshu.utils.Book
import com.github.eprendre.tingshu.utils.BookDetail
import com.github.eprendre.tingshu.utils.Category
import com.github.eprendre.tingshu.utils.CategoryMenu

abstract class TingShu {
    /**
     *  用来判断一本书属于哪个源，不能和其它源有重复的情况，可以用测试里的一个类生成一个 UUID。
     */
    abstract fun getSourceId(): String
    /**
     * 需要提供合法的网址，有时候用来判断链接是否属于某个源。
     */
    abstract fun getUrl(): String

    /**
     * 站点名称
     */
    abstract fun getName(): String

    /**
     * 站点简介
     */
    open fun getDesc() = ""

    /**
     * 返回 Pair 的第二个 Int 参数为最大页数，用来判断是否有下一页。
     * 若部分网站无法获取总页数，但确定有下一页的，可以返回当前页数+1。
     * 异步方法，里面的网络请求同步调用即可。
     */
    abstract fun search(keywords: String, page: Int): Pair<List<Book>, Int>

    /**
     * 选择合适的音频提取逻辑
     */
    abstract fun getAudioUrlExtractor(): AudioUrlExtractor

    /**
     * 分类菜单
     * 异步方法，里面的网络请求同步调用即可。
     */
    abstract fun getCategoryMenus(): List<CategoryMenu>

    /**
     * 分类页面的列表
     * 异步方法，里面的网络请求同步调用即可。
     */
    abstract fun getCategoryList(url: String): Category


    /**
     * 当前站点的章节列表是否需要分页加载
     * app端会优化分页加载的逻辑
     */
    open fun isMultipleEpisodePages() = false

    /**
     * 两个地方会调用这个方法，一是列表页弹窗，二是播放页获取所有章节。<br>
     * 因为有些源在列表提供的部分信息丢失，可以从播放详情页返回更加全面的封面、作者、播音等信息。
     * 如果章节数一个页面即可加载完，可以不用理会 loadEpisodes 为 false 的情况，直接返回章节列表以统计实际章节数。
     * 但是如果章节需要翻页获取，loadEpisodes 为 true， loadFullPages 为 false 时只加载第一页。
     * loadEpisodes 为 true, loadFullPages 也为 true 时，遍历所有页的章节。
     * 异步方法，里面的网络请求同步调用即可。
     */
    abstract fun getBookDetailInfo(bookUrl: String, loadEpisodes: Boolean = true, loadFullPages: Boolean = true): BookDetail

    /**
     * 翻页加载是耗时操作，如果此方法触发，需要取消掉翻页加载的操作。
     */
    open fun reset() = run { }

    /**
     * 当前源是否可搜索
     */
    open fun isSearchable() = true

    /**
     * 当前源是否有发现分类的相关内容
     */
    open fun isDiscoverable() = true

    /**
     * 当前源的音频地址是否能被缓存
     */
    open fun isCacheable() = true

    /**
     * 当前源的解析是否没有用到 WebView <br>
     * 如果返回 false 代表当前源的解析不需要 WebView 的介入，那么这个源会在未集成 WebView 的设备上展示出来(比如手表)。
     */
    open fun isWebViewNotRequired() = false
}

interface AudioUrlExtractor {
    fun extract(url: String, autoPlay: Boolean, isCache: Boolean)
}

/**
 * 如果源需要展示广告就实现这个接口
 * 1.7.3 开始加入
 */
interface IAd {
    /**
     * 广告页的网址，如果返回空，app 自动展示当前章节页面。
     */
    fun adUrl(): String = ""

    /**
     * 是否默认展示广告
     */
    fun showAdByDefault(): Boolean = false
}

/**
 * 请求音频地址时是否需要额外的 header
 * 1.7.3 开始加入
 */
interface AudioUrlExtraHeaders {
    /**
     * 返回音频地址需要添加的 headers
     */
    fun headers(audioUrl: String): Map<String, String>
}