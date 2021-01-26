package org.ituns.android.html.drawable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DrawableLoader {
    private ExecutorService mExecutorService;

    public DrawableLoader() {
        mExecutorService = new ThreadPoolExecutor(
                0, 64,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>()
        );
    }

    public void load(String source, DrawableTask.Callback callback) {
        ExecutorService service = mExecutorService;
        if(service == null) {
            return;
        }
        service.execute(new DrawableTask(source, callback));
    }

    public void release() {
        ExecutorService service = mExecutorService;
        if(service != null) {
            service.shutdown();
            mExecutorService = null;
        }
    }
}
