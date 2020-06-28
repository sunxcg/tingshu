package com.github.eprendre.sources_java_demo;

import com.github.eprendre.tingshu.extensions.MyExtKt;
import com.github.eprendre.tingshu.sources.AudioUrlExtractor;
import com.github.eprendre.tingshu.sources.AudioUrlWebViewExtractor;
import com.github.eprendre.tingshu.sources.TingShu;
import com.github.eprendre.tingshu.utils.*;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class M56TingShuJava extends TingShu {
    public static final M56TingShuJava INSTANCE;

    private M56TingShuJava() {
    }

    static {
        INSTANCE = new M56TingShuJava();
    }

    @NotNull
    public String getSourceId() {
        return "bba5e52d9944494fb8caf1473500d26e";
    }

    @NotNull
    public String getUrl() {
        return "http://m.ting56.com";
    }

    @NotNull
    public String getName() {
        return "56听书网 测试 java";
    }

    @NotNull
    public String getDesc() {
        return "服务器位于: 美国";
    }

    @NotNull
    public List<CategoryMenu> getCategoryMenus() {
        List<CategoryTab> list = new ArrayList<>();
        list.add(new CategoryTab("玄幻武侠", "http://m.ting56.com/book/1.html"));
        list.add(new CategoryTab("都市言情", "http://m.ting56.com/book/2.html"));
        list.add(new CategoryTab("恐怖悬疑", "http://m.ting56.com/book/3.html"));
        list.add(new CategoryTab("综艺娱乐", "http://m.ting56.com/book/45.html"));
        list.add(new CategoryTab("网游竞技", "http://m.ting56.com/book/4.html"));
        list.add(new CategoryTab("军事历史", "http://m.ting56.com/book/6.html"));
        list.add(new CategoryTab("刑侦推理", "http://m.ting56.com/book/41.html"));
        CategoryMenu menu1 = new CategoryMenu("有声小说", list);

        List<CategoryTab> list2 = new ArrayList<>();
        list2.add(new CategoryTab("单田芳", "http://m.ting56.com/byy/shantianfang.html"));
        list2.add(new CategoryTab("刘兰芳", "http://m.ting56.com/byy/liulanfang.html"));
        list2.add(new CategoryTab("袁阔成", "http://m.ting56.com/byy/yuankuocheng.html"));
        list2.add(new CategoryTab("田连元", "http://m.ting56.com/byy/tianlianyuan.html"));
        list2.add(new CategoryTab("连丽如", "http://m.ting56.com/byy/lianliru.html"));
        list2.add(new CategoryTab("王玥波", "http://m.ting56.com/byy/wangyuebo.html"));
        list2.add(new CategoryTab("孙一", "http://m.ting56.com/byy/sunyi.html"));
        list2.add(new CategoryTab("更多", "http://m.ting56.com/book/9.html"));
        CategoryMenu menu2 = new CategoryMenu("评书", list2);

        List<CategoryTab> list3 = new ArrayList<>();
        list3.add(new CategoryTab("职场商战", "http://m.ting56.com/book/7.html"));
        list3.add(new CategoryTab("百家讲坛", "http://m.ting56.com/book/10.html"));
        list3.add(new CategoryTab("广播剧", "http://m.ting56.com/book/40.html"));
        list3.add(new CategoryTab("幽默笑话", "http://m.ting56.com/book/44.html"));
        list3.add(new CategoryTab("相声", "http://m.ting56.com/book/43.html"));
        list3.add(new CategoryTab("儿童读物", "http://m.ting56.com/book/11.html"));

        CategoryMenu menu3 = new CategoryMenu("其它", list3);
        return CollectionsKt.listOf(menu1, menu2, menu3);
    }

    @NotNull
    public AudioUrlExtractor getAudioUrlExtractor() {
        //java代码不能出现 lambda，否则 dx 命令报错。
        AudioUrlWebViewExtractor.INSTANCE.setUp(new Function1<String, String>() {
            @Override
            public String invoke(String html) {
                Document doc = Jsoup.parse(html);
                Element audioElement = doc.selectFirst("#jp_audio_0");
                if (audioElement == null) {
                    return null;
                }
                return audioElement.attr("src");
            }
        });
        return AudioUrlWebViewExtractor.INSTANCE;
    }

    @NotNull
    public BookDetail getBookDetailInfo(String bookUrl, boolean loadEpisodes, boolean loadFullPages) {
        ArrayList<Episode> episodes = new ArrayList<>();
        String currentIntro = "";
        Connection connection = Jsoup.connect(bookUrl);
        try {
            Document doc = MyExtKt.config(connection, false).get();
            Elements elements = doc.select("#playlist a");
            for (Element element : elements) {
                episodes.add(new Episode(element.text(), element.absUrl("href")));
            }
            currentIntro = doc.selectFirst(".book_intro").ownText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BookDetail(episodes, currentIntro, "", "", 0, "");
    }

    @NotNull
    public Category getCategoryList(@NotNull String url) {
        int currentPage = 1;
        int totalPage = 1;
        String nextUrl = "";
        ArrayList<Book> list = new ArrayList<>();
        try {
            Connection connection = Jsoup.connect(url);
            Document doc = MyExtKt.config(connection, false).get();
            Element pageElement = doc.selectFirst("#page_num1");
            if (pageElement != null) {
                String[] pageArray = pageElement.text().split("/");
                currentPage = Integer.parseInt(pageArray[0]);
                totalPage = Integer.parseInt(pageArray[1]);
            }
            Element nextPageElement = doc.selectFirst("#page_next1");
            if (nextPageElement != null) {
                nextUrl = nextPageElement.absUrl("href");
            }
            Elements elements = doc.select(".list-ov-tw");
            for (Element item : elements) {
                String coverUrl = item.selectFirst(".list-ov-t a img").attr("original");
                if (coverUrl.startsWith("/")) {
                    coverUrl = "http://www.ting56.com" + coverUrl;
                }
                Element ov = item.selectFirst(".list-ov-w");
                String bookUrl = ov.selectFirst(".bt a").attr("abs:href");
                String title = ov.selectFirst(".bt a").text();
                Elements zz = ov.select(".zz");
                String author = zz.get(0).text();
                String artist = zz.get(1).text();
                String intro = ov.selectFirst(".nr").text();
                Book book = new Book(coverUrl, bookUrl, title, author, artist);
                book.setIntro(intro);
                book.setSourceId(getSourceId());
                list.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Category(list, currentPage, totalPage, url, nextUrl);
    }

    @NotNull
    public Pair<List<Book>, Integer> search(@NotNull String keywords, int page) {
        int totalPage = 1;
        ArrayList<Book> list = new ArrayList<>();
        try {

            String url = "http://m.ting56.com/search.asp?searchword=" + URLEncoder.encode(keywords, "gb2312") + "&page=" + page;

            Connection connection = Jsoup.connect(url);
            Document doc = MyExtKt.config(connection, false).get();
            Element container = doc.selectFirst(".xsdz");
            Element pageNumElement = container.selectFirst("#page_num1");
            if (pageNumElement != null) {
                totalPage = Integer.parseInt(pageNumElement.text().split("/")[1]);
            }

            Elements elements = container.select(".list-ov-tw");
            for (Element item : elements) {
                String coverUrl = item.selectFirst(".list-ov-t a img").attr("original");
                if (coverUrl.startsWith("/")) {
                    coverUrl = "http://www.ting56.com" + coverUrl;
                }
                Element ov = item.selectFirst(".list-ov-w");
                String bookUrl = ov.selectFirst(".bt a").attr("abs:href");
                String title = ov.selectFirst(".bt a").text();
                Elements zz = ov.select(".zz");
                String author = zz.get(0).text();
                String artist = zz.get(1).text();
                String intro = ov.selectFirst(".nr").text();
                Book book = new Book(coverUrl, bookUrl, title, author, artist);
                book.setIntro(intro);
                book.setSourceId(getSourceId());
                list.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Pair<>(list, totalPage);
    }

}
