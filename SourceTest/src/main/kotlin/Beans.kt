data class Book(
    val coverUrl: String,//封面链接
    val bookUrl: String,//书籍链接
    val title: String,//标题
    val author: String,//作者
    val artist: String,//演播
    val status: String,//书籍状态，比如更新到多少章、已完结
    val intro: String//书籍简介
)

data class Episode(val title: String, val url: String)