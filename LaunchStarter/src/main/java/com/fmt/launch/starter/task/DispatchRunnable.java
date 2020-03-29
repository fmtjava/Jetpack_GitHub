package com.fmt.launch.starter.task;

import android.os.Looper;
import android.os.Process;

import androidx.core.os.TraceCompat;

import com.fmt.launch.starter.TaskDispatcher;
import com.fmt.launch.starter.stat.TaskStat;
import com.fmt.launch.starter.utils.DispatcherLog;


/**
 * 任务真正执行的地方
 */

public class DispatchRunnable implements Runnable {
    private Task mTask;
    private TaskDispatcher mTaskDispatcher;

    public DispatchRunnable(Task task) {
        this.mTask = task;
    }
    public DispatchRunnable(Task task,TaskDispatcher dispatcher) {
        this.mTask = task;
        this.mTaskDispatcher = dispatcher;
    }

    @Override
    public void run() {
        TraceCompat.beginSection(mTask.getClass().getSimpleName());
        DispatcherLog.i(mTask.getClass().getSimpleName()
                + " begin run" + "  Situation  " + TaskStat.getCurrentSituation());

        Process.setThreadPriority(mTask.priority());

        long startTime = System.currentTimeMillis();

        mTask.setWaiting(true);
        mTask.waitToSatisfy();

        long waitTime = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();

        // 执行Task
        mTask.setRunning(true);
        mTask.run();

        // 执行Task的尾部任务
        Runnable tailRunnable = mTask.getTailRunnable();
        if (tailRunnable != null) {
            tailRunnable.run();
        }

        if (!mTask.needCall() || !mTask.runOnMainThread()) {
            printTaskLog(startTime, waitTime);

            TaskStat.markTaskDone();
            mTask.setFinished(true);
            if(mTaskDispatcher != null){
                mTaskDispatcher.satisfyChildren(mTask);
                mTaskDispatcher.markTaskDone(mTask);
            }
            DispatcherLog.i(mTask.getClass().getSimpleName() + " finish");
        }
        TraceCompat.endSection();
    }

    /**
     * 打印出来Task执行的日志
     *
     * @param startTime
     * @param waitTime
     */
    private void printTaskLog(long startTime, long waitTime) {
        long runTime = System.currentTimeMillis() - startTime;
        if (DispatcherLog.isDebug()) {
            DispatcherLog.i(mTask.getClass().getSimpleName() + "  wait " + waitTime + "    run "
                    + runTime + "   isMain " + (Looper.getMainLooper() == Looper.myLooper())
                    + "  needWait " + (mTask.needWait() || (Looper.getMainLooper() == Looper.myLooper()))
                    + "  ThreadId " + Thread.currentThread().getId()
                    + "  ThreadName " + Thread.currentThread().getName()
                    + "  Situation  " + TaskStat.getCurrentSituation()
            );
        }
    }

}
