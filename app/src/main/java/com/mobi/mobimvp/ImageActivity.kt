package com.mobi.mobimvp

import com.bumptech.glide.Glide
import com.mobi.base.BaseActivity
import com.mobi.thrid.images.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_image.*

/**
 * @author  zhousaito
 * @date  2020/7/9 20:04
 * @version 1.0
 * @Dec 略
 */
class ImageActivity : BaseActivity() {
    companion object {
//        const val imagePath = "https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2597615846,3725603705&fm=26&gp=0.jpg"
        const val imagePath = "https://pics2.baidu.com/feed/fd039245d688d43f7c682fb025a8231d0cf43be8.jpeg?token=ec328129bfec7f7f8fbc4625973f3dbf"
    }

    override fun getLayoutId() = R.layout.activity_image

    override fun isFitWindow(): Boolean {
        return super.isFitWindow()
    }

    override fun initView() {

        //正常使用
        ImageLoader.with(this)
            .url(imagePath)
            .into(ivLoad1)

        //圆角
        ImageLoader.with(this)
            .url(imagePath)
            .pixelationFilter(30f)
            .asCircle()
            .into(ivLoad2)

        Glide.with(this).load(imagePath).into(ivLoad3)

        ImageLoader.with(this)
            .url(imagePath)
            .sketchFilter()
            .into(ivLoad4)

    }
}