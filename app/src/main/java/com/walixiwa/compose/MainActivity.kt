package com.walixiwa.compose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.walixiwa.sharedata.ShareData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ShareData.init(this,1)
        ShareData.instance.put("fuck", "you", "owner")
        ShareData.instance.get().forEach {
            Log.e("fuck", it.toString())
        }
    }
}