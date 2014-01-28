package org.humsat.demo.opstools.passes;

import java.util.Date;

public class Experiments {
	
	private String ID = null;
	private Date StartTime = null;
	private String Name = null;
	private String Configuration = null;
	
	public Experiments(String ID, Date StartTime, String Name, String Configuration) {
		setID(ID);
		setStartTime(StartTime);
		setName(Name);
		setConfiguration(Configuration);
	}
	
	public void setID(String ID){
		this.ID=ID;
	}
	public void setStartTime(Date StartTime){
		this.StartTime=StartTime;
	}
	public void setName(String Name){
		this.Name=Name;
		
	}
	public void setConfiguration(String Configuration){
		this.Configuration=Configuration;
	}
	
	public String getID(){
		return ID;
	}
	
	public Date getStartTime(){
		return StartTime;
	}
	public String getName(){
		return Name;
	}
	public String getConfiguration(){
		return Configuration;
	}
}
