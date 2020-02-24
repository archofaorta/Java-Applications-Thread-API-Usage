/**
 * 
 * Timer Class To Simulate Elapsed Time 
 * (Used in Control & Check Classes
 */
public class Timer {

	/**
	 * 
	 * Class Constructor 
	 */
	public Timer(String name) {
		this.name = name;
		this.reset();
	}

	/**
	 * Reset Timer
	 */
	public void reset() {
		this.startTimeSecs = 0;
		this.startTimeSecs = 0;
	}

	/**
	 * Start Timer, Sets Time To Seconds(Current System Time Seconds) When Started
	 */
	public void start() {
		
		//Get Current System Time MilliSeconds
		this.startTimeMilliSecs = System.currentTimeMillis();
		long rmdr = startTimeMilliSecs;
		rmdr /= 1000;
		startTimeSecs = (int) (rmdr % 60);
	}

	/**
	 * 
	 * @return Current Time in Seconds
	 */
	public int currTime() {
		
		//Get Current System Time MilliSeconds
		long startTimeMilliSecs = System.currentTimeMillis();
		long rmdr = startTimeMilliSecs;
		
		//Convert To Seconds
		rmdr /= 1000;
		int secs = (int) (rmdr % 60);

		System.out.println("Current Time: " + secs + "Seconds");
		return secs;

	}

	/**
	 * @return Timer Start Time
	 */
	public int getStartTime() {
		return startTimeSecs;
	}

	//Class Variables
	private String name;
	private long startTimeMilliSecs;
	private int startTimeSecs;

}
