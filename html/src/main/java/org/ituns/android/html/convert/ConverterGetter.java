package org.ituns.android.html.convert;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextPaint;

import org.ituns.android.html.drawable.DrawableCreator;

import java.lang.ref.WeakReference;

public class ConverterGetter implements Html.ImageGetter {
    private final WeakReference<TextPaint> mPaint;
    private final WeakReference<DrawableCreator> mCreator;

    public ConverterGetter(TextPaint paint, DrawableCreator creator) {
        mPaint = new WeakReference<>(paint);
        mCreator = new WeakReference<>(creator);
    }

    @Override
    public Drawable getDrawable(String source) {
        DrawableCreator creator = mCreator.get();
        if(creator != null) {
            return creator.create(source, mPaint.get());
        }
        return null;
    }
}
