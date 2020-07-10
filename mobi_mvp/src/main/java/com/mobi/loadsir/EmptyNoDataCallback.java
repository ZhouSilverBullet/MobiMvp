package com.mobi.loadsir;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.kingja.loadsir.callback.Callback;
import com.mobi.R;

/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class EmptyNoDataCallback extends Callback {
    private String emptyText;

    public EmptyNoDataCallback(String emptyText) {
        this.emptyText = emptyText;
    }

    public EmptyNoDataCallback() {
        emptyText = "暂无数据";
    }

    @Override
    protected void onViewCreate(Context context, View view) {
        TextView textView = view.findViewById(R.id.tv_empty);
        textView.setText(emptyText);
    }

    @Override
    protected int onCreateView() {
        return R.layout.loadsir_layout_text_empty;
    }

}
