package co.com.jvm.concurrency;

class Worker extends Thread {

	public void trabaje(Runnable task) {
		task.run();
	}
}