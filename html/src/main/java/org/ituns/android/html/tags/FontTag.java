package org.ituns.android.html.tags;

import android.text.Editable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;

import org.xml.sax.Attributes;

import java.util.regex.Matcher;

public class FontTag extends HtmlTag {

    @Override
    public boolean startHandleTag(Editable text, Attributes attributes) {
        String style = attributes.getValue("", "style");
        if(style == null) {
            return false;
        }

        int weight = 0;
        Matcher m = getForegroundColorPattern().matcher(style);
        if (m.find()) {
            int c = getHtmlColor(m.group(1));
            if (c != -1) {
                start(text, new Foreground(c | 0xFF000000));
                weight += 1;
            }
        }

        m = getBackgroundColorPattern().matcher(style);
        if (m.find()) {
            int c = getHtmlColor(m.group(1));
            if (c != -1) {
                start(text, new Background(c | 0xFF000000));
                weight += 2;
            }
        }

        m = getTextDecorationPattern().matcher(style);
        if (m.find()) {
            String textDecoration = m.group(1);
            if (textDecoration.equalsIgnoreCase("line-through")) {
                start(text, new Strikethrough());
                weight += 4;
            }
        }
        return weight > 0;
    }

    @Override
    public boolean endHandleTag(Editable text) {
        int weight = 0;
        Strikethrough s = getLast(text, Strikethrough.class);
        if (s != null) {
            setSpanFromMark(text, s, new StrikethroughSpan());
            weight += 1;
        }

        Background b = getLast(text, Background.class);
        if (b != null) {
            setSpanFromMark(text, b, new BackgroundColorSpan(b.mBackgroundColor));
            weight += 2;
        }

        Foreground f = getLast(text, Foreground.class);
        if (f != null) {
            setSpanFromMark(text, f, new ForegroundColorSpan(f.mForegroundColor));
            weight += 4;
        }
        return weight > 0;
    }
}
