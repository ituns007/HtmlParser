package org.ituns.android.html.drawable;

import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.LruCache;
import android.widget.TextView;

import org.ituns.android.html.convert.ConvertDrawable;
import org.ituns.android.html.inject.InjectDrawable;

public class DrawableCreator {
    private DrawableLoader mLoader;
    private final LruCache<String, DrawableCache> mLruCache;

    public DrawableCreator() {
        mLoader = new DrawableLoader();
        mLruCache = new LruCache<>(10 * 1024 * 1024);
    }

    public Drawable create(String source, TextPaint textPaint) {
        if(source == null || textPaint == null) {
            return null;
        }

        ConvertDrawable callback = new ConvertDrawable(textPaint);
        attachDrawableCache(source, callback);
        return callback;
    }

    public Drawable create(String source, TextView textView) {
        if(source == null || textView == null) {
            return null;
        }

        InjectDrawable callback = new InjectDrawable(textView);
        attachDrawableCache(source, callback);
        return callback;
    }

    private void attachDrawableCache(String source, DrawableCache.Callback callback) {
        DrawableCache cache = mLruCache.get(source);
        if(cache == null) {
            cache = new DrawableCache(source, mLoader);
            mLruCache.put(source, cache);
        }
        cache.attach(callback);
    }

    public void release() {
        DrawableLoader loader = mLoader;
        if(loader != null) {
            loader.release();
            mLoader = null;
        }
    }
}