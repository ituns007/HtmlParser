package org.ituns.android.html.inject;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.widget.TextView;

import org.ituns.android.html.drawable.DrawableCache;

import java.lang.ref.WeakReference;

public class InjectDrawable extends BitmapDrawable implements DrawableCache.Callback {
    private Drawable mDrawable;
    private WeakReference<TextView> mTextView;

    public InjectDrawable(TextView textView) {
        mTextView = new WeakReference<>(textView);
    }

    @Override
    public void onDrawable(String source, Drawable drawable) {
        if(drawable == null) {
            return;
        }

        Drawable.ConstantState constantState = drawable.getConstantState();
        if(constantState != null) {
            drawable = constantState.newDrawable().mutate();
        }

        TextView textView = mTextView.get();
        if(textView == null) {
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            drawable.setBounds(0,0, drawableWidth, drawableHeight);
        } else {
            TextPaint textPaint = textView.getPaint();
            float textHeight = textPaint.descent() - textPaint.ascent();
            float textScale = textHeight / drawable.getIntrinsicHeight();
            int drawableWidth = (int) (drawable.getIntrinsicWidth() * textScale);
            int drawableHeight = (int) (drawable.getIntrinsicHeight() * textScale);
            drawable.setBounds(0, 0, drawableWidth, drawableHeight);
        }

        refreshDrawable(drawable);
    }

    private void refreshDrawable(Drawable drawable) {
        mDrawable = drawable;
        if(drawable != null) {
            super.setBounds(drawable.getBounds());
        }

        TextView textView = mTextView.get();
        if(textView != null) {
            textView.invalidate();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Drawable drawable = mDrawable;
        if(drawable != null) {
            drawable.draw(canvas);
        }
    }
}
