package csvreader;

import java.io.*;
import java.util.*;

import Passes.CommunicationPass;


public class CSVReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			FileReader f= new FileReader("C:\\Users\\Aaron\\Documents\\Universidad\\4� Curso Puente\\Proyecto\\STK_Reports\\Access_Times.csv");
			BufferedReader br = new BufferedReader(f);
			String line;
			String[] fields;
			int num=0;
			
			CommunicationPass readpass;
			String ST;
			
			ArrayList<CommunicationPass> Passes_list = new ArrayList <CommunicationPass>();
			
			//We read the first line which only contains format information
			br.readLine();
			
			//Read the rest of the file
			while((line=br.readLine())!=null){
				
				//now we get all the different fields separated by commas in each line
				fields=line.split(",");
				
				//Obtain size of fields. If it is 4, we are right.
				String Pass_number=fields[0];
				String Start_time=fields[1];
				String Stop_time=fields[2];
				String Duration=fields[3];
				
				Passes_list.add(new CommunicationPass(Pass_number, Start_time,Stop_time, Duration));
				
				readpass=Passes_list.get(num);
				ST=readpass.getDuration();
				System.out.println(line);
			}
			br.close();
		}catch (IOException e){
			System.out.println(e.getMessage());
		}
	}

}
