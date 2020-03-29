package com.fmt.launch.starter.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DispatcherExecutor {
    private static ThreadPoolExecutor sCPUThreadPoolExecutor;
    private static ExecutorService sIOThreadPoolExecutor;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // We want at least 2 threads and at most 4 threads in the core pool,
    // preferring to have 1 less than the CPU count to avoid saturating
    // the CPU with background work

    public static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 5));
    private static final int MAXIMUM_POOL_SIZE = CORE_POOL_SIZE;
    private static final int KEEP_ALIVE_SECONDS = 5;
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<>();
    private static final DefaultThreadFactory sThreadFactory = new DefaultThreadFactory();
    private static final RejectedExecutionHandler sHandler = new RejectedExecutionHandler() {// 一般不会到这里
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Executors.newCachedThreadPool().execute(r);
        }
    };

    /**
     * 获取CPU线程池
     * @return
     */
    public static ThreadPoolExecutor getCPUExecutor() {
        return sCPUThreadPoolExecutor;
    }

    /**
     * 获取IO线程池
     * @return
     */
    public static ExecutorService getIOExecutor() {
        return sIOThreadPoolExecutor;
    }

    /**
     * The default thread factory.
     */
    private static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "TaskDispatcherPool-" +
                    poolNumber.getAndIncrement() +
                    "-Thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

    static {
        sCPUThreadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory, sHandler);
        sCPUThreadPoolExecutor.allowCoreThreadTimeOut(true);
        sIOThreadPoolExecutor = Executors.newCachedThreadPool(sThreadFactory);
    }

}
