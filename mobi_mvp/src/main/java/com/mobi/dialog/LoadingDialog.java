package com.mobi.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mobi.util.ResourceUtil;

public class LoadingDialog extends BaseDialog {
    public Context context;
    private TextView tvLoading;

    public LoadingDialog(Context context) {
        this(context, true);
    }

    @Override
    protected Context getActivityContext() {
        return context;
    }

    public LoadingDialog(Context context, boolean isCancel) {
        super(context, ResourceUtil.getIdentifierStyle("common_loading_dialog"));
        this.context = context;
        this.setCancelable(isCancel);
        this.setCanceledOnTouchOutside(false);
        Window window = this.getWindow();
        window.setWindowAnimations(ResourceUtil.getIdentifierStyle("MobiLoadingDialogWindowStyle"));
    }

    public void setDialogCancelable(boolean isCancel) {
        setCancelable(isCancel);
    }

    /**
     *
     * @param message null 为default 默认 加载中...
     */
    public void setMessage(@Nullable String message) {
        if (tvLoading != null) {
            if (message == null) {
                tvLoading.setVisibility(View.GONE);
            } else {
                tvLoading.setText(message);
                tvLoading.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(ResourceUtil.getIdentifierLayout("common_dialog_loading"));

        tvLoading = findViewById(ResourceUtil.getIdentifierId("tv_loading_text"));
    }
}
