/*
 * Requisite - Minecraft library mod
 * Copyright (C) 2021 MatthewTGM
 *
 * Requisite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * Requisite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Requisite. If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.matthewtgm.requisite.util;

import xyz.matthewtgm.requisite.Requisite;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class Multithreading {

    private static final AtomicInteger threadCount = new AtomicInteger(0);
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 50, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), (r) -> new Thread(r, String.format(Requisite.NAME + " Thread %s", threadCount.incrementAndGet())));
    private static final ScheduledExecutorService runnableExecutor = new ScheduledThreadPoolExecutor(6);

    private Multithreading() {}

    /**
     * @param runnable The code to run asynchronously.
     * @author MatthewTGM
     */
    public static void runAsync(Runnable runnable) {
        executor.execute(runnable);
    }

    /**
     * @param runnable The runnable code to be ran.
     * @param delay    The delay before running.
     * @param timeUnit The time unit of the delay.
     * @author MatthewTGM
     */
    public static ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
        return runnableExecutor.schedule(runnable, delay, timeUnit);
    }

    /**
     * @param runnable   The runnable code to be ran.
     * @param startDelay The initial delay before running the first time.
     * @param delay      The delay before running.
     * @param timeUnit   The time unit of the delay.
     * @author MatthewTGM
     */
    public static ScheduledFuture<?> schedule(Runnable runnable, long startDelay, long delay, TimeUnit timeUnit) {
        return runnableExecutor.scheduleAtFixedRate(runnable, startDelay, delay, timeUnit);
    }

    /**
     * @param runnable The code to submit.
     * @author MatthewTGM
     */
    public static Future<?> submit(Runnable runnable) {
        return executor.submit(runnable);
    }

}