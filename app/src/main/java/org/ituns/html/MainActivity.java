package org.ituns.html;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;

import org.ituns.android.html.HtmlCompat;
import org.ituns.android.html.HtmlConfig;
import org.ituns.android.html.HtmlParser;

public class MainActivity extends AppCompatActivity {
    private HtmlParser htmlParser;
    private AppCompatTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeData();
        initializeView();
    }

    private void initializeData() {
        HtmlConfig config = new HtmlConfig.Builder(this).build();
        htmlParser = new HtmlParser(config);
    }

    private void initializeView() {
        textView = findViewById(R.id.text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String html = "<img src=\"https://th.bing.com/th/id/R6db6860271318927587a87fd6567dd6f?rik=y5%2b8igJZu0EHlA&riu=http%3a%2f%2fcdn.onlinewebfonts.com%2fsvg%2fimg_410627.png&ehk=p63TYgSBCvUMbpTENUMhcDPz8PxxOMX45HkO3CGDY3k%3d&risl=&pid=ImgRaw\"/>AAAAA";
        htmlParser.injectHtml(html, textView);
    }
}
