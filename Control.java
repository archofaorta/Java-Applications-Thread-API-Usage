/**
 * 
 * Class: Control
 *  
 *  Implements Interfaces IControl & ICheck And Defines Following Methods
 *   
 *   Methods:
 *   (1) Start()
 *      	If Called with no prior call to Stop and 20 seconds have not elapsed an exception is thrown.
 *   Interpretation:-   	
 *      Since Stop Cannot be Called before Start, It is assumed here thta Subsequent Calls To Start should be after one call To Stop	
 *    
 *      If Start(2 or more) is called with no prior calls to Stop, and 20 Seconds have not elapsed between subsequents
 *      calls, it throws User Defined Exception 'ProgramExistException'
 *      
 *      If Start(2 or more) is called with no prior calls to Stop, and 20 Seconds have elapsed between subsequents
 *      calls, no Exception is Thrown (conrol returns successfully)
 *      
 *   (2) Stop()
 *   	 Throws User Defined Exception 'ProgramExistException' If Called Before Start
 *    
 *   (3) HasCompleted
 *     After Start is called, HasCompleted will return true 
 *     		(Stop is not called and 20 Seconds have not elapsed after Start was called) 
 *     
 *     If Stop is called or 20 seconds have elapsed  it returns false.
 *     
 *     If Stop is called after Start and Before HasCompleted, it returns false	
 *     
 *     
 * When Executed, Program Output would be as below (User Defined Exception is Thrown, Based on Conditions):- 
 *  
 *  Thread1: Timer Started At: **** Seconds
 *	Thread1: Start Invoked 1 Time
 *	Current Time: **** Seconds
 *	Exception in thread "Thread1" Thread1: HasCompleted: Not Stopped, And Elapsed Time < 20 Seconds, Returning True
 *	Thread1: HasCompleted Returned Value: true
 *	Thread1: Stopped Invoke
 *	Thread1: Stopped Invoke
 *	ProgramExitException: Thread1: Exception in Stop: Called Before Start
 *	at Control.Stop(Control.java:72)
 *	at Control$1.run(Control.java:121)
 *
 */

public class Control implements  IControl , ICheck   {

	public Control(){
		reset();
	}
	
	private void reset(){
		m_cntStarted = 0;
		m_cntStopped = -1;
	    m_startedOnce = -1;
	    m_isStarted = 0;
	}
	
	/**
	 * Method Start  
	 * 
	 *  If Start(2 or more) is called with no prior calls to Stop, and 20 Seconds have not elapsed between subsequents
	 *      calls, it throws User Defined Exception 'ProgramExistException'
	 *      	
	 * If Start(2 or more) is called with no prior calls to Stop, and 20 Seconds have elapsed between subsequents
	 *      calls, no Exception is Thrown (conrol returns successfully)
	 */
	public  void Start()  {
		
		m_startedOnce++;
		m_cntStarted++;
		m_isStarted = 1;
		
		//Print - Status Message
		System.out.println( Thread.currentThread().getName() + ":" +  " Start Invoked " + m_cntStarted + " Time");
		
		//If Called 2 or more Time Before Stop(), and TimeElapased < 20 
		if ((m_startedOnce > 0 && m_cntStopped < 0) && (this.timeElaspsed() <= 20)) {

			try {
				//Throw ProgramExitException
				throw new ProgramExitException(Thread.currentThread().getName()+ ":" +  
						" Exception In Start: Start Called Again Before Prior Call to Stop",1);

			} finally {}

		} 
		//If Called After Stop(), or 20 Seconds Elapsed Before Calling Start() again
		else if (m_startedOnce > 0) {
			
			//No Exception - Invoked Success  
			System.out.println(Thread.currentThread().getName() + ":" +  
						" No Exception In Start: Called Again Before Prior Call To Stop Elapsed Time > 20");
		}
		
		return;
	}
	
	/**
	 * Method Stop 
	 *  
	 *  Throws User Defined Exception 'ProgramExistException' If Called Before Start
	 *  
	 */
	public   void Stop() {
		
		//Print - Status Message
		System.out.println(Thread.currentThread().getName() + ":" +" Stopped Invoke");
		
		//If Called Before Start()
		if(m_isStarted == 0){
			try{
				
				//Throw ProgramExitException
				throw new ProgramExitException(Thread.currentThread().getName() + ":" +  
								" Exception in Stop: Called Before Start",1);
				
			}finally{ }	
			
		}
		
		m_isStarted = 0;
		m_cntStopped++;
		return;
	}
	
	/**
	 * Method HasCompleted
	 * 
	 * After Start is called, HasCompleted will return TRUE 
	 *     		(Stop is not called and 20 Seconds have not elapsed after Start was called) 
	 *     
	 *     If Stop is called or 20 seconds have elapsed  it returns FALSE.
	 *     
	 *     If Stop is called after Start and Before HasCompleted, it returns FALSE	
	 */
	public   boolean HasCompleted() {
		
		//If Start Called
		if(m_cntStarted > 0){
			
			//If Stopped Not Called Or 20 Seconds Not Elapsed Before Call To Stop  
			if( (m_cntStopped < 0) && (this.timeElaspsed() <= 20) ){
				
				//Return True
				System.out.println(Thread.currentThread().getName() + ":" +  
						" HasCompleted: Not Stopped, And Elapsed Time < 20 Seconds, Returning True");
				return true;
			}
			
			//If Stopped Called 
			if ((m_cntStopped > 0)){
				
				//Return False
				return false;
			}
		}
		
		//If 20 Seconds Elasped Before Call To Stop(), Return False
		System.out.println(Thread.currentThread().getName() + ":" +  " HasCompleted: " + timeElaspsed() 
					+  " Seconds Elapsed Before Call To Stop, Returning False");
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		
		//New Thread To 
		Thread t1 = new Thread("Thread1") {
			
			public void run() {
				try {
						//Syncronize Access To Object 
						synchronized (control){
							//Start Timer (To Simulate Elapsed Time)
							timer.start();
							System.out.println(Thread.currentThread().getName() + ":" + 
										" Timer Started At: " + timer.getStartTime() + " Seconds");
							
							
							control.Start();
							
							//Sleep For 3 Seconds
							Thread.sleep(3000);
							
							//Call HasCompleted
							System.out.println(Thread.currentThread().getName() + ":" + "" +
										" HasCompleted Returned Value: " + control.HasCompleted());
							//control.Start()
							control.Stop();
							control.Stop();
							
							//Notify Threads Waiting on Object
							control.notifyAll();
							
							//reset
							timer.reset();
							control.reset();
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
			}
		};
		
		/*Thread t2 = new Thread() {
			public void run() {
					
				try {
						synchronized (control){
							control.reset();
							t.reset();
							t.start();
							System.out.println("Timer Started At: " + t.getStartTime() + " Seconds");
							control.Start();
							Thread.sleep(300);
							System.out.println("HasCompleted Returned Value: " + control.HasCompleted());
							control.Stop();
							
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		};*/
		
		t1.start();
		//t2.start();
		

	}
	
	/**
	 * 
	 * @return Elapsed Time 
	 */
	private int timeElaspsed(){
		
		int elspTime = timer.currTime()-timer.getStartTime();
		return (elspTime) > 0 ? (elspTime) : (elspTime+60); 
	}
	
	
	//Class Variables
	final static Timer timer = new Timer("Control"); //Timer
	final static Control control = new Control();	//Classs Object
	public int m_cntStarted;		//Counter For No Of Calls To Start
	public int m_cntStopped ;		//Counter For Calls To Stop 
	public int m_startedOnce;		//Variable To Determine Start Was Called Once
	public int m_isStarted;	 		//Variable To Determine Start Was Called Before Stop
	
}
