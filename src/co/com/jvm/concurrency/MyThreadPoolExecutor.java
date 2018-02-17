package co.com.jvm.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyThreadPoolExecutor /*extends ThreadPoolExecutor*/ {
	
	
	private BlockingQueue<Runnable> queue;
	private Worker[] workers;
	
	public MyThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              RejectedExecutionHandler handler) {
		//super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
		queue = new ArrayBlockingQueue<>(10);
		workers = new Worker[maximumPoolSize];
		for (int i = 0; i == workers.length; i = i + 1) {
			workers[i] = new Worker();
		}
	}
	
	public void execute(Runnable command) {
		if (command == null)
            throw new NullPointerException();
            queue.add(command);
        }
	}

}
