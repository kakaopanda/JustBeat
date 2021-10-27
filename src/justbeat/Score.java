package justbeat;

import java.io.*;

public class Score extends Thread{
	
	public static Game game;
	
	public void record(String data) {
    	 
   	  try {
   		  	String filePath = "C:\\Users\\»ï¼º\\Desktop\\Personal Project\\Perfect Beat\\Score/PerfectBeat_Score_Record.txt";
   		  	FileWriter fileWriter = new FileWriter(filePath,true);
   		  	
   		  	fileWriter.write(data);
   		  	fileWriter.close();
   		
   	  } catch (IOException e) {
   	   e.printStackTrace();
   	  }
   }
	
	public void data(String data) {
   	 
	   	  try {
	   		  	String filePath = "C:\\\\Users\\\\»ï¼º\\\\Desktop\\\\Personal Project\\\\Perfect Beat\\\\Score/PerfectBeat_Score_Record.txt";
	   		  	FileWriter fileWriter = new FileWriter(filePath,true);
	   		  	
	   		  	fileWriter.write(data);
	   		  	fileWriter.close();
	   		
	   	  } catch (IOException e) {
	   	   e.printStackTrace();
	   	  }
	   }
}

 