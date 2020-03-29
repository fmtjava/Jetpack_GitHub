package com.fmt.launch.starter;

import android.os.Looper;
import android.os.MessageQueue;
import com.fmt.launch.starter.task.DispatchRunnable;
import com.fmt.launch.starter.task.Task;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 延迟初始化：使用MessageQueue中的IdleHandler在(系统空闲的时候)进行延迟初始化
 */
public class DelayInitDispatcher {

    private Queue<Task> mDelayTasks = new LinkedList<>();//队列保存延迟初始化任务

    //核心点IdleHandler
    private MessageQueue.IdleHandler mIdleHandler = new MessageQueue.IdleHandler() {
        @Override
        public boolean queueIdle() {
            //空闲时，每次只执行一次任务
            if(mDelayTasks.size()>0){
                Task task = mDelayTasks.poll();
                new DispatchRunnable(task).run();
            }
            return !mDelayTasks.isEmpty();
        }
    };

    //添加延迟初始化任务
    public DelayInitDispatcher addTask(Task task){
        mDelayTasks.add(task);
        return this;
    }

    //开启延迟初始化
    public void start(){
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

}
