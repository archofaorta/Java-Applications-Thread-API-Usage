/**
 * Java Class To Demo Bytes Reversal In File
 * (Code is Commented For Clearity) 
 * 
 * 
 * The Same can be implemented in PREL with fewer lines of code
 * PERL Implementation (One Line)
 * For Eg :- Reversing File Bytes in PERL 
 * 		perl -e 'print scalar reverse <>' File_Name
 * 
 * 
 * C/C++ Implementation 
 *	 #include <iostream>
 *	 #include <fstream>
 *
 *	char* readFileBytes(const char *name)
 *	{
 *       ifstream fl(name);
 *       fl.seekg( 0, ios::end );
 *       size_t len = fl.tellg();
 *       char *ret = new char[len];
 *       fl.seekg(0, ios::beg);
 *       fl.read(ret, len);
 *       {
 *       		 Reverse Logic Here
 *       }
 *       fl.close();
 *       return ret;
 *	}
 *
 **/

import java.io.*;

public class FileBytesReverseDemo {

	public FileBytesReverseDemo(){}
	
	public static void main(String[] args){
		
		//Check Command Line Input For File Path
		if(args.length < 1){
			System.out.println("File Argument Missing: Enter Absolute File Path" + "eg. C:/test.txt");
			System.exit(1);
		}
		
		try {
			
			m_fileName = new File(args[0]); 			   
			m_inStream = new FileInputStream(m_fileName);  //Create File InputStream
			m_Size = m_inStream.available();			   //Get File Size (Bytes) 	
			
			
	        if (m_Size > Integer.MAX_VALUE) {
	        	System.out.println("File Too Large");
	        	System.exit(1);
	        } //If File To Large , exit
	        
	       
	        //Buffer To Hold File Bytes
	        byte buffer[] = new byte[(int)m_Size]; 
	        
	        //Fetch File Bytes in Buffer
	        m_inStream.read(buffer);			  
	        
	        //OutPut Stream To Write File (Reveresed Bytes)
	        m_outStream = new FileOutputStream(m_fileName);  
	        
	        //Delete Original File (OverWritten By Reversed Bytes)
	        m_fileName.delete();		
	        
	        //Write Reversed Bytes To File (Same File As Input)
	        for(int i=(buffer.length-1);i>=0;i--){
	        	m_outStream.write((char)buffer[i]);
	        }
	        
	        //Close File Streams
	        m_inStream.close();
	        m_outStream.close();
	        
		}
		catch (FileNotFoundException e){
			System.out.println("File Not Found");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("Exception While File Processing");
			e.printStackTrace();
		}
		finally {
			System.out.println(m_fileName + " File Bytes Reversed Successfully ");
		}
	}
	
	//Class Variables
	private static long m_Size;
	private static InputStream m_inStream;
	private static File m_fileName;
	private static OutputStream m_outStream ;
	
}
