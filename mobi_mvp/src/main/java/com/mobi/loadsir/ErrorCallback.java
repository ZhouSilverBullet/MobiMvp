package com.mobi.loadsir;


import com.kingja.loadsir.callback.Callback;
import com.mobi.util.ResourceUtil;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return ResourceUtil.getIdentifierLayout("loadsir_layout_error");
    }
}
