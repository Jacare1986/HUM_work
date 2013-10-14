package Passes;

public class CommunicationPass {

	private String Pass_number;
	private String Start_time;
	private String Stop_time;
	private String Duration;
	
	public CommunicationPass(String Pass_number, String Start_time, String Stop_time, String Duration){
		setPassNumber(Pass_number);
		setStartTime(Start_time);
		setStopTime(Stop_time);
		setDuration(Duration);	
	}
	
	//Pass Number
	public String getPassNumber(){
		return Pass_number;
	}
	public void setPassNumber(String Pass_number){
		this.Pass_number=Pass_number;
	}
	
	//Start Time
	public String getStartTime(){
		return Start_time;
	}
	public void setStartTime(String Start_time){
		this.Start_time=Start_time;
	}
	
	//Stop Time
	public String getStopTime(){
		return Stop_time;
	}
	public void setStopTime(String Stop_time){
		this.Stop_time=Stop_time;
	}
	
	//Duration
	public String getDuration(){
		return Duration;
	}
	public void setDuration(String Duration){
		this.Duration=Duration;
	}
}
