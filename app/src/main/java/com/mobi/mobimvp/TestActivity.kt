package com.mobi.mobimvp

import android.Manifest
import com.mobi.base.BaseMvpActivity
import com.mobi.mobimvp.presenter.TestPresenter
import com.mobi.util.LogMvpUtils
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseMvpActivity<TestPresenter>() {
    val TAG = "TestActivity"

    override fun getPresenter(): TestPresenter {
        return TestPresenter()
    }

    override fun getLayoutId(): Int {
       return R.layout.activity_test
    }

    override fun initView() {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe {

            }
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
