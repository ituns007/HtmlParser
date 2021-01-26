package org.ituns.android.html.convert;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;

import org.ituns.android.html.drawable.DrawableCache;

import java.lang.ref.WeakReference;

public class ConvertDrawable extends BitmapDrawable implements DrawableCache.Callback {
    private Drawable mDrawable;
    private WeakReference<TextPaint> mTextPaint;

    public ConvertDrawable(TextPaint textPaint) {
        mTextPaint = new WeakReference<>(textPaint);
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

        TextPaint textPaint = mTextPaint.get();
        if(textPaint == null) {
            int drawableWidth = drawable.getIntrinsicWidth();
            int drawableHeight = drawable.getIntrinsicHeight();
            drawable.setBounds(0,0, drawableWidth, drawableHeight);
        } else {
            float textHeight = textPaint.descent() - textPaint.ascent();
            float textScale = textHeight / drawable.getIntrinsicHeight();
            int drawableWidth = (int) (drawable.getIntrinsicWidth() * textScale);
            int drawableHeight = (int) (drawable.getIntrinsicHeight() * textScale);
            drawable.setBounds(0,0, drawableWidth, drawableHeight);
        }

        refreshDrawable(drawable);
    }

    private void refreshDrawable(Drawable drawable) {
        mDrawable = drawable;
        if(drawable != null) {
            super.setBounds(drawable.getBounds());
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