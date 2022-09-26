package com.app.bookslibrary

import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.bookslibrary.dataManager.Apis
import com.app.bookslibrary.model.Book
import com.app.bookslibrary.viewModels.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apis: Apis
    private val booksViewModel: BooksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        booksViewModel.getBooks("android")
        booksViewModel.bookLiveData.observe(this) { elm ->
            try {
                getbooks(elm.toString())
            } catch (e: Exception) {
                Log.d("ex :", "onCreate: "+e.message)
            }
        }
    }
    fun getbooks(elm: String): List<Book> {
        var books = ArrayList<Book>()
        var html = elm
        val table = Jsoup.parse(html).getElementsByClass("c")
        val tbody: Elements = table.select("tbody")
        val tr: Elements = tbody.select("tr")
        for (e in tr.next()) {
            /*  Log.d("line",e.select("td").get(0).text().toString() + "--"+e.select("td").get(1).text().toString() + "||"+e.select("td").get(2).text().toString()+" line :"+e.select("a").attr("href"))
              Log.d("line"," line :"+e.select("a").last()?.attr("href"))*/
            var map = getDoawloadUrl(
                "https://libgen.rocks/ads.php?md5=${
                    e.select("a").last()?.attr("href")?.split("/")!!.last()
                }"
            )
            var book = map.get("image")?.let {
                Book(
                    e.select("a").last()?.attr("href")?.split("/")!!.last(),
                    e.select("td").get(2).text().toString(),
                    map.get("download")!!,
                    it,
                    e.select("td").get(1).text().toString(),
                    e.select("td").get(4).text().toString(),
                    e.select("td").get(5).text().toString(),
                    e.select("td").get(1).text().toString(),
                )

            }
            if (book != null) {
                books.add(book)
            }
            Log.d("book : --->", book.toString())

        }
        return books

    }

    fun getDoawloadUrl(path: String): Map<String, String> {
        var url = ""
        var html = Jsoup.parse(URL(path), 80 * 2000)
        url = html.getElementById("main")?.select("tbody")?.select("tr")?.select("tr")?.select("a")
            ?.attr("href").toString()
        var urlImage = html.getElementById("main")?.select("tbody")?.select("tr")?.select("td")
            ?.select("table")?.select("tbody")?.select("tr")?.select("td")?.select("a")
            ?.attr("href").toString()
        return mapOf<String, String>("image" to urlImage, "download" to url)
    }
}