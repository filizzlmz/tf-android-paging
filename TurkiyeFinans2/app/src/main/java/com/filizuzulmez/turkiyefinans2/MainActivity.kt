package com.filizuzulmez.turkiyefinans2

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.filizuzulmez.turkiyefinans2.view.SearchPageFragment

class MainActivity : AppCompatActivity() {
    var frame: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        frame = findViewById(R.id.frameLayout)

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, SearchPageFragment())
            .commit()

    }
}