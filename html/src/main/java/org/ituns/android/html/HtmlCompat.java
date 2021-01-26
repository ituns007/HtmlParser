package org.ituns.android.html;

import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

public class HtmlCompat {

    public static Spanned fromHtml(String html){
        return fromHtml(html, null);
    }

    public static Spanned fromHtml(String html, Html.ImageGetter imageGetter) {
        if(TextUtils.isEmpty(html)) {
            return new SpannableString("");
        }

        try {
            return fromHtmlCompat(html, imageGetter);
        } catch (Exception e) {
            return new SpannableString(html);
        }
    }

    private static Spanned fromHtmlCompat(String html, Html.ImageGetter imageGetter) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, imageGetter, new HtmlTagHandler());
        } else {
            return Html.fromHtml(html, imageGetter, new HtmlTagHandler());
        }
    }
}
