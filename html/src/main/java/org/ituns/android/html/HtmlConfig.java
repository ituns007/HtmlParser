package org.ituns.android.html;

import android.content.Context;

public class HtmlConfig {
    private Context context;

    private HtmlConfig(Builder builder) {
        this.context = builder.context;
    }

    public Context context() {
        return context;
    }

    public static class Builder {
        private Context context;

        public Builder(Context context) {
            this.context = context;
        }

        public HtmlConfig build() {
            return new HtmlConfig(this);
        }
    }
}
