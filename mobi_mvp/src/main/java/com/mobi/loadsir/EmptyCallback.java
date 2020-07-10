package com.mobi.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.mobi.util.ResourceUtil;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return ResourceUtil.getIdentifierLayout("loadsir_layout_empty");
    }

}
