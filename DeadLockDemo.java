/**
 * 
 * Java Class To Demo DeadLock
 *
 */
public class DeadLockDemo {
	
	//Objects
	final static Object m_obj1 = new Object();
	final static Object m_obj2 = new Object();

	
	public static void main(String[] args) {
	
		// t1 tries to lock Object1 then Object2
		Thread t1 = new Thread() {
			
			public void run() {
				
				// Lock Object 1
				synchronized (m_obj1) {
					
					System.out.println("Thread 1: locked Object 1");
					try {
						 //Sleep for 20 MilliSecs
						Thread.sleep(20);  
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//Lock Object 2
					synchronized (m_obj2) {
						System.out.println("Thread 1: locked Object 2");
						
					}
				}
			}
		};

		// t2 tries to lock Object2 then Object1
		Thread t2 = new Thread() {
			
			public void run() {
				
				//Lock Object 2
				synchronized (m_obj2) {
					
					System.out.println("Thread 2: locked Object 2");
					
					try {
						 //Sleep for 20 MilliSecs
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//Lock Object 1
					synchronized (m_obj1) {
						System.out.println("Thread 2: locked Object 1");
					}
				}
			}
		};

		t1.start();
		t2.start();
	}
}