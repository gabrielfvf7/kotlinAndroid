package com.example.twitterdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class MainActivity : AppCompatActivity() {

    var listOfTweets = ArrayList<Ticket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listOfTweets.add(Ticket("0", "Hi", "url", "add"))
        listOfTweets.add(Ticket("0", "Hi", "url", "gabriel"))
    }

    inner class  MyNotesAdpater(var context: Context, var listTweetsAdapter: ArrayList<Ticket>): BaseAdapter() {

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val myTweet = listTweetsAdapter[p0]

            return if (myTweet.tweetPersonUID.equals("add")) {
                layoutInflater.inflate(R.layout.add_ticket, null)
            } else {
                layoutInflater.inflate(R.layout.tweets_ticket, null)
            }
        }

        override fun getItem(p0: Int): Any {
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getCount(): Int {
        }



    }
}
