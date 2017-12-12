package com.jiang.rpc.service;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jiang.rpc.core.MessageSendExecutor;

public class CalcParallelRequestThread implements Runnable {

	private CountDownLatch signal;
	private CountDownLatch finish;
	private MessageSendExecutor executor;
	private int taskNumber = 0;

	public CalcParallelRequestThread(MessageSendExecutor executor, CountDownLatch signal, CountDownLatch finish,
			int taskNumber) {
		this.signal = signal;
		this.finish = finish;
		this.taskNumber = taskNumber;
		this.executor = executor;
	}

	public void run() {
		try {
			signal.await();

			Calculate calc = executor.execute(Calculate.class);
			int add = calc.add(taskNumber, taskNumber);
			System.out.println("calc add result:[" + add + "]");

			finish.countDown();
		} catch (InterruptedException ex) {
			Logger.getLogger(CalcParallelRequestThread.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}