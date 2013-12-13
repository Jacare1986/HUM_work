
package org.humsat.demo.opstools.csvreader;
//package csvreader;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.humsat.demo.opstools.passes.CommunicationPass;


public class CSVReader {

	public static ArrayList<CommunicationPass> getpasses(int M1, int M2,String path) throws ParseException, IOException{
		
		SimpleDateFormat sdf  = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS",Locale.ENGLISH);
		Date start_time,stop_time;
		ArrayList<CommunicationPass> passes_list = new ArrayList<CommunicationPass>();	
		String line;
		String[] fields;
					
			FileReader f= new FileReader(path);
			BufferedReader br = new BufferedReader(f);
			//We read the first line which only contains format information
			br.readLine();
			
			//Read the rest of the file
			while((line=br.readLine())!=null){
				/**
				 * Don't know why it reads an extra line with "" and not null, so it cannot split and makes an exception
				 * I have to make sure at least the line read is longer than that to obtain values. 
				 * Then, it reaches an null correctly.
				 */
				
				//Obtain different fields from the line read to build a CommunicationPass
				if(line.length()>=4){
					
					fields=line.split(",");
					
					//Obtain size of fields. If it is 4, we are right.
					
					String pass_number=fields[0];
					//We substract M1 to Start_Time
					start_time=sdf.parse(fields[1]);
					//System.out.println(start_time);
					long fecha=start_time.getTime()-(M1*1000);
					start_time.setTime(fecha);
					//System.out.println(start_time);
					stop_time=sdf.parse(fields[2]);
					//we add margin M2 to Duration
					long duration = (long)(Float.parseFloat(fields[3])+M2);
					
					//we build a new CommunicationPass and we add it to a list o pases.
					passes_list.add(new CommunicationPass(pass_number, start_time,stop_time, duration));
					//System.out.println(line);
				}
				
			}
			br.close();
		return passes_list;
	}
	
	public static void PassesType(ArrayList<CommunicationPass> pases, String path) throws IOException, ParseException {
		
		/**
		 * We compare passes with lightning times
		 * Then we assign to each pass a case type depending on lightning times
		 */
		
		String line;
		String[] fields;
		SimpleDateFormat sdf  = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS",Locale.ENGLISH);
		Date light_start,light_stop;
		int it=0;
			
			FileReader f= new FileReader(path);
			BufferedReader br = new BufferedReader(f);
			//We read the first line which only contains format information
			br.readLine();
			
			while(((line=br.readLine())!=null)&(it<pases.size())){
	
				if(line.length()>=4){
					
					fields=line.split(",");
			
					light_start=sdf.parse(fields[0]);
					light_stop=sdf.parse(fields[1]);
					
					//Read Pass Start & Pass Stop
					Date pass_start = (pases.get(it)).getStartTime();
					//System.out.println(pass_start);
					Date pass_stop = (pases.get(it)).getStopTime();
					
					/**
					 * we check in which case we are
					 */
					
					if(pass_start.after(light_start)==true){
						if(pass_start.before(light_stop)==true){
							//Start_Time with light
							if(pass_stop.before(light_stop)==true){
								//pass within light --> case 1
								(pases.get(it)).setCaseType(1);
								//Light Duration during pass
								long light_pass_duration=(pases.get(it).getDuration());
								(pases.get(it)).setPassLightDuration(light_pass_duration);
								//update new pass to compare
								it++;		
							}else{
								//part of the pass is within light and the other in eclipse
								//--> case 3
								(pases.get(it)).setCaseType(3);
								//Light Duration during pass
								long light_pass_duration=((light_stop.getTime())-(pass_start.getTime()))/1000; //getTime() returns time since UNIX in ms so we divide by 1000 to obtain in sec.
								(pases.get(it)).setPassLightDuration(light_pass_duration);
								//update new pass to compare
								it++;
							}
						}else{
							//do nothing, compare same pass with next lightning time
						}
					}else{
						//pass_start in eclipse
						if(pass_stop.before(light_start)==true){
							//Eclipse pass --> case 2
							(pases.get(it)).setCaseType(2);
							(pases.get(it)).setPassLightDuration(0);
							it++;
						}else if(pass_stop.before(light_stop)==true){
							//Pas start in eclipse and end with light
							(pases.get(it)).setCaseType(4);
							long light_pass_duration=((pass_stop.getTime())-(light_start.getTime()))/1000;
							(pases.get(it)).setPassLightDuration(light_pass_duration);
							it++;
						}
					}
				}
			}
			br.close();
		
	}

}
