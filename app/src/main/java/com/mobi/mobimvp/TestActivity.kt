package com.mobi.mobimvp

import com.mobi.base.BaseMvpActivity
import com.mobi.mobimvp.presenter.TestPresenter
import com.mobi.util.LogMvpUtils
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseMvpActivity<TestPresenter>() {
    val TAG = "TestActivity"

    override fun getPresenter(): TestPresenter {
        return TestPresenter()
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_test
    }

    override fun initEvent() {
        btnSkipSelf.setOnClickListener {
            showProgressDialog(true, "加载中...")
        }
    }


    override fun onResume() {
        super.onResume()
        LogMvpUtils.i(TAG, "activity onResume")
    }


    override fun onPause() {
        super.onPause()
        LogMvpUtils.i(TAG, "activity onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogMvpUtils.i(TAG, "activity onDestroy")
    }
}
