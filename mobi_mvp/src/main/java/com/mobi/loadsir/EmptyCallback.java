package com.mobi.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.mobi.R;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.loadsir_layout_empty;
    }

}
