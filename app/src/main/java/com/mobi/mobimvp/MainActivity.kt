package com.mobi.mobimvp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.mobi.thrid.images.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSkipTest.setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }

        btnSkipDownload.setOnClickListener {
            startActivity(Intent(this, DownloadActivity::class.java))
        }


        btnSkipImage.setOnClickListener {
            startActivity(Intent(this, ImageActivity::class.java))
        }

//        ImageLoader.load(this, "https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2597615846,3725603705&fm=26&gp=0.jpg", ivLoad)
//        ImageLoader.with(this)
//            .asCircle()
//            .sketchFilter()
//            .url("https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2597615846,3725603705&fm=26&gp=0.jpg")
//            .into(ivLoad)
    }
}
