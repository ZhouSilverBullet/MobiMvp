package com.mobi.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.mobi.util.ResourceUtil;

public class LoadingSpinView extends AppCompatImageView {
    private float mRotateDegrees;
    private int mFrameTime;
    private boolean mNeedToUpdateView;
    private Runnable mUpdateViewRunnable;
    private Drawable drawable;

    public LoadingSpinView(Context context) {
        this(context, null);
    }

    public LoadingSpinView(Context context, AttributeSet attrs) {
        super(context, attrs);

        int[] loadingSpinViews = ResourceUtil.getResourceDeclareStyleableIntArray("LoadingSpinView");
        TypedArray a = getResources().obtainAttributes(attrs, loadingSpinViews);
        drawable = a.getDrawable(ResourceUtil.getStyleable("LoadingSpinView_lsv_loadSrc"));
        a.recycle();

        this.init();
    }

    private void init() {
        if (drawable != null) {
            setImageDrawable(drawable);
        } else {
            this.setImageResource(ResourceUtil.getIdentifierDrawable("loading"));
        }
        this.mFrameTime = 83;
        this.mUpdateViewRunnable = new Runnable() {
            @Override
            public void run() {
                mRotateDegrees = mRotateDegrees + 30.0F;
                mRotateDegrees = mRotateDegrees < 360.0F ? mRotateDegrees : mRotateDegrees - 360.0F;
                invalidate();
                if (mNeedToUpdateView) {
                    postDelayed(this, (long) mFrameTime);
                }

            }
        };
    }

    public void setAnimationSpeed(float scale) {
        this.mFrameTime = (int) (83.0F / scale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(this.mRotateDegrees, (float) (this.getWidth() / 2), (float) (this.getHeight() / 2));
        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mNeedToUpdateView = true;
        this.post(this.mUpdateViewRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        this.mNeedToUpdateView = false;
        super.onDetachedFromWindow();
    }
}
