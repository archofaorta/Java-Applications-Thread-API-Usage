# JavaApplications


Files:-

(1) File Bytes Reverse 
	program to reverse the bytes in a file. 


(2) DeadLock Demo
	program to demonstrate DeadLock


(3) Thread Simulation
	program to simulate java thread behavior

	(i) ICheck.java  - Interface  ( Method HasCompleted)
	
	(ii) IControl.java - Interface ( Methods Start() , Stop())

	(iii) Control.java - Class Implements ICheck, IControl
	
	    Implements Following Behavior	
		1.   After Start is called, HasCompleted will return true unless Stop is called or 20 seconds have elapsed when it returns false.
   		2.   If Start is called with no prior call to Stop and 20 seconds have not elapsed an exception is thrown.
		3.   If Stop is called before Start an exception is thrown	
 
	
	(iv) Checek.java - Class Implements ICheck, IControl
	
	   Implements Following Behavior
	  	1.   After Start is called, HasCompleted will return true unless Stop is called or 30 seconds have elapsed when it returns false.
  		2.   If Start is called a second (or more) time with no call to Stop in between and 35 seconds have not elapsed an exception is thrown.
	  	3.   If HasCompleted is called after a call to Stop and before any subsequent Start calls an exception is thrown.

	(v) ProgramExitException - User Defined Exception Class (To Be Used To Throw Exception)
	
	(vi) Timer.java - Class To Simulate Elapsed Timing
