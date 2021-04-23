package ga.matthewtgm.lib.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Multithreading {

    private static final AtomicInteger threadCount = new AtomicInteger(0);
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 50, 0l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), (r) -> new Thread(r, String.format("Thread %s", threadCount.incrementAndGet())));

    /**
     * @param runnable The code to run asynchronously.
     * @author MatthewTGM
     */
    public static void runAsync(Runnable runnable) {
        executor.execute(runnable);
    }

}