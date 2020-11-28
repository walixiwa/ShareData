package com.walixiwa.compose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.walixiwa.sharedata.ShareData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ShareData.init(this, version = 1)
        ShareData.instance.put("testKey", "存入的值", "owner")
        val value = ShareData.instance.get("testKey", "默认值", "owner")
        ShareData.instance.get<String>().forEach {
            Log.e("fuck", it.toString())
        }
    }
}