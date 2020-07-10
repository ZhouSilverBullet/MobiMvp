package com.mobi.loadsir;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kingja.loadsir.callback.Callback;
import com.mobi.util.ResourceUtil;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class EmptyTextCallback extends Callback {
    private String emptyText;

    public EmptyTextCallback(String emptyText) {
        this.emptyText = emptyText;
    }

    public EmptyTextCallback() {
        emptyText = "没有打卡动态哦~";
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        TextView textView = view.findViewById(ResourceUtil.getIdentifierId("tv_empty"));
        textView.setText(emptyText);
    }

    @Override
    protected int onCreateView() {
        return ResourceUtil.getIdentifierLayout("loadsir_layout_text_empty");
    }

}
