package org.ituns.android.html.inject;

import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import org.ituns.android.html.drawable.DrawableCreator;

import java.lang.ref.WeakReference;

public class InjectorGetter implements Html.ImageGetter {
    private final WeakReference<TextView> mView;
    private final WeakReference<DrawableCreator> mCreator;

    public InjectorGetter(TextView view, DrawableCreator creator) {
        mView = new WeakReference<>(view);
        mCreator = new WeakReference<>(creator);
    }

    @Override
    public Drawable getDrawable(String source) {
        DrawableCreator creator = mCreator.get();
        if(creator != null) {
            return creator.create(source, mView.get());
        }
        return null;
    }
}
