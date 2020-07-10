package com.mobi.mobimvp

import android.os.Handler
import android.widget.TextView
import com.mobi.base.BaseActivity
import com.mobi.loadsir.EmptyCallback

class LoadActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_load

    override fun initView() {
        Handler().postDelayed({
            loadService.setCallBack(EmptyCallback::class.java) { context, view ->
                val emptyText = view.findViewById<TextView>(R.id.tv_empty)
                emptyText.setText("我是修改后的空数据")
            }
            loadService.showCallback(EmptyCallback::class.java)
        }, 3000)
    }
}