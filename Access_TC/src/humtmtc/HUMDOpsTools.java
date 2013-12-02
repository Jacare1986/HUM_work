/**
 * Copyright 2012 - University of Vigo - Spain (www.uvigo.es)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
//package org.humsat.demo.gssw.opstools;
package humtmtc;

import com.xatcobeo.gssw.tmtc.*;
import com.xatcobeo.gssw.tmtc.DataTypes.NumericField;
import com.xatcobeo.gssw.tmtc.DataTypes.TcScheduler;
import com.xatcobeo.gssw.tmtc.DataTypes.TimeStamp;
import com.xatcobeo.gssw.tmtc.exceptions.UnknownException;
import com.xatcobeo.gssw.tmtc.exceptions.UnknownTCCodeException;
import com.xatcobeo.gssw.tmtc.interfaces.TMTCInterface;
import com.xatcobeo.gssw.tmtc.services.OBOpsProcedureService.TC.TCChangeToCommunicationMode;
import com.xatcobeo.gssw.tmtc.services.OBOpsSchedulingService.TC.TCAddTelecommandAbsoluteTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import passes.*;
import windowbuilder.ErrorWindow;
import csvreader.*;

/**
 *
 * @author Alberto Gonzalez (alberto.gonzalez (at) humsat.org)
 */

public class HUMDOpsTools {

    private static ArrayList<CommunicationPass> pases= new ArrayList <CommunicationPass>();	

	/**
     * Builds a SC_RQ_ADDABS request containing a OP_RQ_TOCOMM request (TMTC).
     * @param passObject, object containing all the information about the communication pass to be scheduled.
     * @return schTc, TMTC object of the SC_RQ_ADDABS request.
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
     */
	
	public static void TCListCreator(int M1, int M2,int case_type1,int case_type2,int case_type3,int case_type4,String AccessTimesPath, String LightningPath, String TCList_path, String Output_file_name) throws ParseException, ClassNotFoundException, IOException{	
		
		//Telecomand List
		ArrayList<TMTC> TC_List = new ArrayList<TMTC>();
		
		try {
			//First --> Obtain passes from Acces_Times report
			pases= CSVReader.getpasses(M1, M2,AccessTimesPath);		
			
			//Second --> Classify each pass in cases depending on light conditions	
			CSVReader.PassesType(pases, LightningPath);
 
			//Third --> Create a TC_List
		    /**
		     * We create a list of TC. The first and last position has a TC_sumarized.
		     */
			int it=0;
			int k=0;
		    TC_List.add(createSchSummarizedTC());
		    for(it=0;it<pases.size();it++){
		    	if((pases.get(it).getCaseType()==case_type1)||(pases.get(it).getCaseType()==case_type2)||(pases.get(it).getCaseType()==case_type3)||(pases.get(it).getCaseType()==case_type4)){
		    		TC_List.add(HUMDOpsTools.createSchCommPassTC(pases.get(it)));
			    	System.out.println("Pase numero " +it+ " "+pases.get(it).getPassLightDuration());
			    	k++;
		    	}	
		    }
		    TC_List.add(createSchSummarizedTC());
		    System.out.println("Total Passes programmed: "+k);
		    if(k==0){
		    	ErrorWindow ew = new ErrorWindow("No passes matched your selection");
				ew.setVisible(true);
		    }else{
		    	//we write this TC_List in a file.
				writeSequenceTofile(TC_List,TCList_path,(Output_file_name+".ser"));			
				writeSequenceTXT(TC_List, TCList_path, (Output_file_name+".txt"),case_type1,case_type2,case_type3,case_type4);
		    }
			
		} catch (UnknownTCCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
		
	}
	

	public static TMTC createSchCommPassTC(CommunicationPass passObject) throws UnknownTCCodeException, UnknownException, ParseException {
        
        // Start time of the COMMS task
        TimeStamp time = new TimeStamp();        
        // TODO complete the groundTime (StartPass + margin), M, epoch and offset fields with actual values
        long groundTime=0;
        double M=0;
        long epoch=0;
        long offset=0;
        
        
        // end of TODO        
       
        //Obtain groundTime and epoch since UNIX in ms
        SimpleDateFormat sdf  = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS",Locale.ENGLISH);          
        Date date_epoch = sdf.parse("01 Jan 2012 00:00:00.000");
        
        groundTime=(passObject.getStartTime()).getTime();
        epoch=date_epoch.getTime();
        M=100;
        
        time.setGroundTime(groundTime, M, epoch, offset);
        
        // duration of the COMMS task
        // TODO obtain duration of the communication pass in seconds
        long duration=0;
        // end of TODO  
        
        duration =passObject.getDuration();
        
        NumericField durationNF = new NumericField(NumericField.UINT16);
        durationNF.setIntValue(duration);
        
        // OP_RQ_TOCOMM TC datatype to be injected in the Add Telecommand TC 
        TMTC commTC = TMTCSet.createTC(TMTCInterface.TC_CHANGE_TO_COMMUNICATION_MODE_ID);
        HashMap commParams = new HashMap<>();
        commParams.put(TCChangeToCommunicationMode.COMMUNICATIONS_TIME, durationNF);
        //ArrayList commKeys = commTC.getParamsKeys();
        commTC.setParams(commParams);
        
        TcScheduler tc= new TcScheduler();
        tc.setTC(commTC);
        
        // SC_RQ_ADDABS TC construction
        TMTC schTC = TMTCSet.createTC(TMTCInterface.TC_ADD_TELECOMMAND_ABSOLUTE_TIME_ID);
        // ArrayList schKeys = schTC.getParamsKeys();
        HashMap schParams = new HashMap();
        schParams.put(TCAddTelecommandAbsoluteTime.TIME_SCH, time);
        schParams.put(TCAddTelecommandAbsoluteTime.TC_SCH, tc);
        schTC.setParams(schParams);
        
        return (schTC);
    }
    
    /**
     * Builds a SC_RQ_SUMMARIZED request (TMTC).
     * @return tc, TMTC object of the SC_RQ_SUMMARIZED request.
     */
    public static TMTC createSchSummarizedTC() throws UnknownTCCodeException, UnknownException {
        TMTC tc = TMTCSet.createTC(TMTCInterface.TC_REPORT_COMMAND_SCHEDULE_IN_SUMMARY_FORM_ID);
        return (tc);
    }
    
    public static void writeSequenceTXT(ArrayList<TMTC> tclist, String path, String filename, int case_type1, int case_type2, int case_type3, int case_type4)
    {
    	try{
    		Calendar cal = Calendar.getInstance();
    		SimpleDateFormat sdf=  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    		String currentDate = sdf.format(cal.getTime());
    		
    		File f= new File(path+filename);
    		FileWriter fw = new FileWriter(f);
    		//add path, name and date
    		fw.write(path);
    		fw.write(System.getProperty("line.separator"));
    		fw.write(filename);
    		fw.write(System.getProperty("line.separator"));
    		fw.write(currentDate);
    		fw.write(System.getProperty("line.separator"));
    		fw.write("Passes programmed : ");
    		if(case_type1==1){
    			fw.write("Light |");
    		}if(case_type2==2){
    			fw.write("Eclipse |");
    		}if(case_type3==3){
    			fw.write("Light+Eclipse |");
    		}if(case_type4==4){
    			fw.write("Eclipse + Light |");
    		}	
    		//add white line
    		fw.write(System.getProperty("line.separator"));
    		fw.write(System.getProperty("line.separator"));
    		
    		int k=0;
    		for (k=0;k<tclist.size();k++){
    			fw.write(tclist.get(k).print());
    			//change line
    			fw.write(System.getProperty("line.separator"));
    		}
    		fw.close();
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	
    }
    /**
     * Serializes and writes a telecommands sequence to a file.
     * @param sequence, sequence of telecommands.
     * @param path, path to the sequence file.
     * @param filename, name of the file.
     */
    public static void writeSequenceTofile(ArrayList<TMTC> sequence, String path, String filename) throws IOException, ClassNotFoundException {

       // File folder = new File(path);
        //folder.mkdirs();
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path + filename, false)))    
        {
            os.writeObject(sequence);
            os.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Reads a serialized sequence of telecommands from a file.
     * @param path, path to the sequence file.
     * @param filename, name of the file.
     * @return sequence of telecommands
     */
    public static ArrayList<TMTC> readSequenceFromFile(String path, String filename) throws IOException, ClassNotFoundException {

        ArrayList<TMTC> sequence = new ArrayList<>();
        TMTC TC;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path + filename))) {
            Object aux = new Object();

            sequence.addAll((ArrayList<TMTC>) ois.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (sequence);
    }

}


