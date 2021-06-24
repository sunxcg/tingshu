import assertk.assertThat
import assertk.assertions.isGreaterThan
import com.github.eprendre.tingshu.utils.Book
import com.github.eprendre.tingshu.utils.Episode
import org.jsoup.Jsoup
import org.junit.Test
import java.net.URLEncoder

class I275Test {

    @Test
    fun search() {
        val keywords = "修仙"
        val encodedKeywords = URLEncoder.encode(keywords, "utf-8")
        val page = 1
        val url = "http://www.i275.com/pc/index/search.html?keyword=${encodedKeywords}&submit="
        val doc = Jsoup.connect(url).testConfig(false).get()
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
            })
        }
        list.forEach { println(it) }
        assertThat(list.size).isGreaterThan(0)
    }

    @Test
    fun bookDetail() {
        val doc = Jsoup.connect("http://www.i275.com/book/11944.html").testConfig(false).get()

        val episodes = doc.select(".playul > li > a").map {
            Episode(it.text(), it.absUrl("href"))
        }

        episodes.take(20).forEach { println(it) }
        assertThat(episodes.size).isGreaterThan(0)
    }

    @Test
    fun category() {
        val doc = Jsoup.connect("http://www.i275.com/category/6.html").testConfig(false).get()
        var totalPage = 1
        var currentPage = 1
        var nextUrl = ""

        val pages = doc.select(".pagination > li")
        currentPage = pages.first { it.hasClass("active") }.text().toInt()
        if (!pages.last().hasClass("disabled")) {
            nextUrl = pages.last().child(0).absUrl("href")
            totalPage = currentPage + 1
        }

        println("$currentPage/$totalPage")
        println("nextUrl: $nextUrl")

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
            })
        }
        list.forEach { println(it) }
        assertThat(list.size).isGreaterThan(0)

    }
}