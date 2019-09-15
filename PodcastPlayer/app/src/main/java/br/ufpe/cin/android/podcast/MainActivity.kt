package br.ufpe.cin.android.podcast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = ItemFeedAdapter(listOf<ItemFeed>(), this)

        doAsync {
            try {
                var xml =
                    URL("https://s3-us-west-1.amazonaws.com/podcasts.thepolyglotdeveloper.com/podcast.xml").readText()
                var itemFeedList = Parser.parse(xml)

                uiThread {
                    list.adapter = ItemFeedAdapter(itemFeedList, applicationContext)
                }

            } catch (e: Throwable) {
                Log.w("ERROR", e.message.toString())
            }
        }
    }
}
