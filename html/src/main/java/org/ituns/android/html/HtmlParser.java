package org.ituns.android.html;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.TextView;

import org.ituns.android.html.convert.ConverterGetter;
import org.ituns.android.html.drawable.DrawableCreator;
import org.ituns.android.html.inject.InjectorGetter;
import org.ituns.android.html.utils.HtmlUtils;

public final class HtmlParser {
    private static final String IMG_TAG = "<img";

    private DrawableCreator mCreator;

    public HtmlParser(HtmlConfig config) {
        HtmlUtils.initializeNetworkCache(config.context());
        mCreator = new DrawableCreator();
    }

    public Spanned convertHtml(String html, TextPaint textPaint) {
        if(TextUtils.isEmpty(html)) {
            return new SpannableString("");
        }

        if(html.contains(IMG_TAG)) {
            return HtmlCompat.fromHtml(html, new ConverterGetter(textPaint, mCreator));
        } else {
            return HtmlCompat.fromHtml(html);
        }
    }

    public void injectHtml(String html, TextView textView) {
        if(textView == null) {
            return;
        }

        if(TextUtils.isEmpty(html)) {
            textView.setText(new SpannableString(""));
            return;
        }

        if(html.contains(IMG_TAG)) {
            textView.setText(HtmlCompat.fromHtml(html, new InjectorGetter(textView, mCreator)));
        } else {
            textView.setText(HtmlCompat.fromHtml(html));
        }
    }

    public void release() {
        DrawableCreator creator = mCreator;
        if(creator != null) {
            creator.release();
            mCreator = null;
        }
    }
}