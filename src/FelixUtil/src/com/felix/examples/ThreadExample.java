package com.felix.examples;

import java.util.Date;


public class ThreadExample {
	MyThread _myThread;
	boolean _playing;
	private final int THREAD_TIMEOUT = 5000;
	public void doItWithThread() {
		_myThread = new MyThread();
		_myThread.start();
	}

	public void doItWithRunnable() {
		Runnable runner = new Runnable() {
			public void run() {
					_playing = true;
					while (true) {
						if (! _playing)
							break;
					}
					_playing = false;
			}
		};
		Thread myThread = new Thread(runner);
		myThread.start();
	    Date start = new Date();
		while (_playing) {
		      Date end = new Date();
			try {
				Thread.sleep(1000);				
			} catch (Exception e) {e.printStackTrace();}
			if (end.getTime() - start.getTime()>THREAD_TIMEOUT) {
				System.err.println("thread execution took too long");
				break;
			}
		}

	}
	
	
	private class MyThread extends Thread {
		public void run() {
			// do something
		}
	}

	
}
