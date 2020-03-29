package com.fmt.launch.starter.task;

import android.content.Context;
import android.os.Process;


import com.fmt.launch.starter.TaskDispatcher;
import com.fmt.launch.starter.utils.DispatcherExecutor;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public abstract class Task implements ITask {
    protected String mTag = getClass().getSimpleName().toString();
    protected Context mContext = TaskDispatcher.getContext();
    protected boolean mIsMainProcess = TaskDispatcher.isMainProcess();// 当前进程是否是主进程
    private volatile boolean mIsWaiting;// 是否正在等待
    private volatile boolean mIsRunning;// 是否正在执行
    private volatile boolean mIsFinished;// Task是否执行完成
    private volatile boolean mIsSend;// Task是否已经被分发
    private CountDownLatch mDepends = new CountDownLatch(dependsOn() == null ? 0 : dependsOn().size());// 当前Task依赖的Task数量（需要等待被依赖的Task执行完毕才能执行自己），默认没有依赖

    /**
     * 当前Task等待，让依赖的Task先执行
     */
    public void waitToSatisfy() {
        try {
            mDepends.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 依赖的Task执行完一个
     */
    public void satisfy() {
        mDepends.countDown();
    }

    /**
     * 是否需要尽快执行，解决特殊场景的问题：一个Task耗时非常多但是优先级却一般，很有可能开始的时间较晚，
     * 导致最后只是在等它，这种可以早开始。
     *
     * @return
     */
    public boolean needRunAsSoon() {
        return false;
    }

    /**
     * Task的优先级，运行在主线程则不要去改优先级
     *
     * @return
     */
    @Override
    public int priority() {
        return Process.THREAD_PRIORITY_BACKGROUND;
    }

    /**
     * Task执行在哪个线程池，默认在IO的线程池；
     * CPU 密集型的一定要切换到DispatcherExecutor.getCPUExecutor();
     *
     * @return
     */
    @Override
    public ExecutorService runOn() {
        return DispatcherExecutor.getIOExecutor();
    }

    /**
     * 异步线程执行的Task是否需要在被调用await的时候等待，默认不需要
     *
     * @return
     */
    @Override
    public boolean needWait() {
        return false;
    }

    /**
     * 当前Task依赖的Task集合（需要等待被依赖的Task执行完毕才能执行自己），默认没有依赖
     *
     * @return
     */
    @Override
    public List<Class<? extends Task>> dependsOn() {
        return null;
    }

    @Override
    public boolean runOnMainThread() {
        return false;
    }

    @Override
    public Runnable getTailRunnable() {
        return null;
    }

    @Override
    public void setTaskCallBack(TaskCallBack callBack) {}

    @Override
    public boolean needCall() {
        return false;
    }

    /**
     * 是否只在主进程，默认是
     *
     * @return
     */
    @Override
    public boolean onlyInMainProcess() {
        return true;
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public void setRunning(boolean mIsRunning) {
        this.mIsRunning = mIsRunning;
    }

    public boolean isFinished() {
        return mIsFinished;
    }

    public void setFinished(boolean finished) {
        mIsFinished = finished;
    }

    public boolean isSend() {
        return mIsSend;
    }

    public void setSend(boolean send) {
        mIsSend = send;
    }

    public boolean isWaiting() {
        return mIsWaiting;
    }

    public void setWaiting(boolean mIsWaiting) {
        this.mIsWaiting = mIsWaiting;
    }

}
