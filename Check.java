/**
 * 
 * Class: Check
 *  
 *  Implements Interfaces IControl & ICheck And Defines Following Methods
 *   
 *   Methods:
 *   (1) Start()
 * 		 If Start is called a second (or more) time with no call to Stop in between 
 * 			and 35 seconds have not elapsed an exception is thrown.	
 *    
 *      If Start(2 or more) is called with no prior calls to Stop, and 35 Seconds have not elapsed between subsequents
 *      calls, it throws User Defined Exception 'ProgramExistException'
 *      
 *      If Start(2 or more) is called with no prior calls to Stop, and 35 Seconds have elapsed between subsequents
 *      calls, no Exception is Thrown (conrol returns successfully)
 *      
 *   (2) Stop()
 *   	 	
 *    
 *   (3) HasCompleted
 *     After Start is called, HasCompleted will return true 
 *     		(Stop is not called and 30 Seconds have not elapsed after Start was called) 
 *     
 *     If Stop is called or 30 seconds have elapsed  it returns false.
 *     
 *     If Stop is called after Start and Before HasCompleted, it returns false	
 *     
 *     If HasCompleted is called after a call to Stop and before any subsequent Start calls 
 *     		an User Defined Exception 'PromgramExitException' is Thrown
 *     
 *     
 *     
 * When Executed, Program Output would be as below (User Defined Exception is Thrown, Based on Conditions):-
 
 *  Thread1: Timer Started At: *** Seconds
 *	Thread1: Start Invoked 1 Time
 *	Current Time: *** Seconds
 *	Thread1: HasCompleted: Not Stopped, And Elapsed Time < 30 Seconds, Returning True
 *	Thread1: HasCompleted Returned Value: true
 *	Thread1: Stopped Invoke
 *	Thread1: Start Invoked 2 Time
 *	Thread1: No Exception In Start: Called Again Before Prior Call To Stop Elapsed Time > 35
 *	Current Time: 1
 *	Thread1: HasCompleted: 6 Seconds Elapsed Before Call To Stop, Returning False
 *	Thread1: HasCompleted Returned Value: false
 *
 */
public class Check implements  IControl , ICheck   {

	public Check(){
		reset();
	}
	
	private void reset(){
		m_cntStarted = 0;
		m_cntStopped = -1;
	    m_startedOnce = -1;
	}
	
	/**
	 * Method Start 
	 */
	public  void Start()  {
		
		m_startedOnce++;
		m_cntStarted++;
		
		System.out.println( Thread.currentThread().getName() + ":" +  " Start Invoked " + m_cntStarted + " Time");
		
		
		if ((m_startedOnce > 0 && m_cntStopped < 0) && (this.timeElaspsed() <= 35)) {

			try {

				throw new ProgramExitException(Thread.currentThread().getName()+ ":" +  
						" Exception In Start: Start Called Again Before Prior Call to Stop",1);

			} finally {}

		} else if (m_startedOnce > 0) {
			
			System.out.println(Thread.currentThread().getName() + ":" +  
						" No Exception In Start: Called Again Before Prior Call To Stop Elapsed Time > 35");
		}
		
		return;
	}
	/**
	 * Method Stop()
	 */
	public   void Stop() {
		
		//Print Status Message
		System.out.println(Thread.currentThread().getName() + ":" +" Stopped Invoke");
		
		/*if(m_started <= 0){
			try{
				throw new ProgramExitException(Thread.currentThread().getName() + ":" 
									+  " Exception in Stop: Called Before Start",1);
			}finally{ }	
			
		}*/
		
		m_cntStopped++;
		return;
	}
	/**
	 * Method HasCompleted
	 *     After Start is called, HasCompleted will return true 
	 *     		(Stop is not called and 30 Seconds have not elapsed after Start was called) 
	 *     	
	 *     If Stop is called or 30 seconds have elapsed  it returns false.
	 *     
	 *     If Stop is called after Start and Before HasCompleted, it returns false	
	 *     		
	 *     If HasCompleted is called after a call to Stop and before any subsequent Start calls 
	 *     			an User Defined Exception 'PromgramExitException' is Thrown
	 */
	
	public   boolean HasCompleted() {
		
		//If Started 
		if(m_cntStarted > 0){
			
			//If Stopped Not Called Or 30 Seconds Not Elapsed Before Call To Stop
			if( (m_cntStopped < 0) && (this.timeElaspsed() <= 30) ){
				
				//Return True
				System.out.println(Thread.currentThread().getName() + ":" +  
						" HasCompleted: Not Stopped, And Elapsed Time < 30 Seconds, Returning True");
				return true;
			}
			
			//If Called Before Subsequent Calls To Start, After Stop is Called
			if( (m_cntStarted == 1) && (m_cntStopped == 0) ){
				try{
					//Raise Exception
					throw new ProgramExitException(Thread.currentThread().getName() + ":" 
										+  " Exception in HasCompleted: Called Before Subsequent Call To Start " +
												" After A Call To Stop",1);
				}finally{ }	
			}
			
			//If Stopped Called 
			if ((m_cntStopped > 0)){
				
				return false;
			}
			
		}
		//If 30 Seconds Elasped Before Call To Stop(), Return False
		System.out.println(Thread.currentThread().getName() + ":" +  " HasCompleted: " + timeElaspsed() 
					+  " Seconds Elapsed Before Call To Stop, Returning False");
		return false;
	}
	
	public static void main(String[] args) throws Exception {
		
		//New Thread To Access Classs Methods
		Thread t1 = new Thread("Thread1") {
			
			public void run() {
				try {
						//Sync Access To Object 
						synchronized (check){
							//Start Timer
							timer.start();
							System.out.println(Thread.currentThread().getName() + ":" + 
										" Timer Started At: " + timer.getStartTime() + " Seconds");
							
							
							check.Start();	//Call Start
							//check.Stop();
							
							//Sleep For 6 Seconds
							Thread.sleep(6000);
							
							//Call HasCompleted
							System.out.println(Thread.currentThread().getName() + ":" + "" +
										" HasCompleted Returned Value: " + check.HasCompleted());
							//check.Start();
							
							check.Stop(); //Call Stop
							check.Start();	//Call Start
							System.out.println(Thread.currentThread().getName() + ":" + "" +
									" HasCompleted Returned Value: " + check.HasCompleted());
							
							check.notifyAll(); //Notify Waiting Threads
							timer.reset();
							check.reset();
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
			}
		};
		
		/*Thread t2 = new Thread() {
			public void run() {
					
				try {
						synchronized (check){
							check.reset();
							timer.reset();
							timer.start();
							System.out.println("Timer Started At: " + t.getStartTime() + " Seconds");
							check.Start();
							Thread.sleep(300);
							System.out.println("HasCompleted Returned Value: " + check.HasCompleted());
							check.Stop();
							
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
	
	final static Timer timer = new Timer("Check"); //Timer
	final static Check check = new Check();		   //Class Object
	public int m_cntStarted;		//Counter For No Of Calls To Start
	public int m_cntStopped ;		//Counter For Calls To Stop 
	public int m_startedOnce;		//Variable To Determine Start Was Called Once
	public int m_isStarted;	 		//Variable To Determine Start Was Called Before Stop
	
	
	
	
}
