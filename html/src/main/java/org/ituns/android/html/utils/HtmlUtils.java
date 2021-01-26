package org.ituns.android.html.utils;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class HtmlUtils {

    public static void initializeNetworkCache(Context context) {
        if(HttpResponseCache.getInstalled() == null) {
            try {
                File cacheDir = cacheDir(context, "html");
                long cacheSize = 100 * 1024 * 1024; // 10 MiB
                HttpResponseCache.install(cacheDir, cacheSize);
            } catch (Exception e) {}
        }
    }

    private static File cacheDir(Context context, String uniqueName) {
        String cachePath;
        if(Environment.MEDIA_MOUNTED.endsWith(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
