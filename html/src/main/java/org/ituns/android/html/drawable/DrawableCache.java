package org.ituns.android.html.drawable;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrawableCache implements DrawableTask.Callback {
    private String mSource;
    private Handler mHandler;
    private Drawable mDrawable;
    private DrawableLoader mLoader;
    private AtomicBoolean mLoadState;
    private LinkedList<Callback> mCallbacks;

    public DrawableCache(String source, DrawableLoader loader) {
        mSource = source;
        mLoader = loader;
        mDrawable = null;
        mCallbacks = new LinkedList<>();
        mHandler = new Handler(Looper.getMainLooper());
        mLoadState = new AtomicBoolean(false);
    }

    public void attach(Callback callback) {
        Drawable drawable = mDrawable;
        if(drawable != null) {
            callback.onDrawable(mSource, drawable);
            return;
        }

        mCallbacks.addLast(callback);
        if(mLoadState.compareAndSet(false, true)) {
            mLoader.load(mSource, this);
        }
    }

    @Override
    public void onDrawableLoaded(final String source, final Drawable drawable) {
        if(drawable == null) {
            mLoadState.set(false);
            return;
        }

        mDrawable = drawable;
        Callback callback = null;
        while ((callback = mCallbacks.poll()) != null) {
            final Callback c = callback;
            mHandler.post(() -> c.onDrawable(source, drawable));
        }
    }

    public interface Callback {
        void onDrawable(String source, Drawable drawable);
    }
}
