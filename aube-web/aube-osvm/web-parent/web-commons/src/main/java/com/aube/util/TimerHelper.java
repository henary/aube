package com.aube.util;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.aube.Config;
import com.aube.support.AubeExecutorThreadFactory;
import com.aube.util.log.AubeLogger;
import com.aube.util.log.LoggerUtils;


public enum TimerHelper {
	TIMER;
	
	private static final transient AubeLogger dbLogger = LoggerUtils.getLogger(TimerHelper.class, Config.getServerIp(), Config.SYSTEMID);
	private ScheduledExecutorService scheduler;

	private TimerHelper(){
		scheduler = Executors.newScheduledThreadPool(50, new AubeExecutorThreadFactory("AubeGlobalScheduler"));
	}
	
	public void schedule(Runnable task, long delay){
		scheduler.schedule(new DelegateTask(task), delay, TimeUnit.MILLISECONDS);
	}
	
	public void schedule(Runnable task, Date time){
		scheduler.schedule(new DelegateTask(task), time.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 每次执行完成后，间隔多长时间执行下次。如果上次一直没执行完，下次无法执行
	 * @param task
	 * @param delay
	 * @param period
	 */
	public void scheduleWithFixedDelay(Runnable task, long delay, long period){
		scheduler.scheduleWithFixedDelay(new DelegateTask(task), delay, period, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 每次执行完成后，间隔多长时间执行下次。如果上次一直没执行完，下次无法执行
	 * @param task
	 * @param firstTime
	 * @param period
	 */
	public void scheduleWithFixedDelay(Runnable task, Date firstTime, long period){
		scheduler.scheduleWithFixedDelay(new DelegateTask(task), firstTime.getTime() - System.currentTimeMillis(), period, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 间隔多长时间执行一次（不管上次是否执行完）
	 * @param task
	 * @param delay
	 * @param period
	 */
	public void scheduleAtFixedRate(Runnable task, long delay, long period) {
		scheduler.scheduleAtFixedRate(new DelegateTask(task), delay, period, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 间隔多长时间执行一次（不管上次是否执行完）
	 * @param task
	 * @param firstTime
	 * @param period
	 */
	public void scheduleAtFixedRate(Runnable task, Date firstTime, long period) {
		scheduler.scheduleAtFixedRate(new DelegateTask(task), firstTime.getTime() - System.currentTimeMillis(), period, TimeUnit.MILLISECONDS);
	}
	
	private class DelegateTask implements Runnable {
		private Runnable task;
		
		DelegateTask(Runnable task){
			this.task = task;
		}

		@Override
		public void run() {
			try {
				task.run();
			} catch (Throwable t) {
				dbLogger.warn(t, 100);
			}
			
		}		
	}
}
