package com.mobi.loadsir;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.mobi.util.ResourceUtil;


/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class LoadingCallback extends Callback {

    @Override
    protected int onCreateView() {
        return ResourceUtil.getIdentifierLayout("loadsir_layout_loading");
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
