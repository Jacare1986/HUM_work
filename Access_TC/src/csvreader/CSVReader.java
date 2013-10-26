package csvreader;

import java.io.*;
import java.util.*;

import Passes.*;


public class CSVReader {

	public static ArrayList<CommunicationPass> getpasses(){
		
		ArrayList<CommunicationPass> passes_list = new ArrayList<CommunicationPass>();
		
		try{		
			FileReader f= new FileReader("C:\\Users\\Aaron\\Documents\\Universidad\\4� Curso Puente\\Proyecto\\STK_Reports\\Access_Times.csv");
			BufferedReader br = new BufferedReader(f);
			String line;
			String[] fields;
						
			//We read the first line which only contains format information
			br.readLine();
			
			//Read the rest of the file
			while((line=br.readLine())!=null){
				/**
				 * Don't know why it reads an extra line with "" and not null, so it cannot split and makes an exception
				 * I have to make sure at least the line read is longer than that to obtain values. 
				 * Then, it reaches an null correctly.
				 */
				
				//now we get all the different fields separated by commas in each line
				if(line.length()>=4){
					
					fields=line.split(",");
					
					//Obtain size of fields. If it is 4, we are right.
					String pass_number=fields[0];
					String start_time=fields[1];
					String stop_time=fields[2];
					String duration=fields[3];
					
					passes_list.add(new CommunicationPass(pass_number, start_time,stop_time, duration));
					System.out.println(line);
				}
				
			}
			br.close();
		}catch (IOException e){
			System.out.println(e.getMessage());
		}
		return passes_list;
	}

}
