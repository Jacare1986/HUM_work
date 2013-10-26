package Passes;

public class CommunicationPass {

	private String pass_number;
	private String start_time;
	private String stop_time;
	private String duration;
	
	public CommunicationPass(String pass_number, String start_time, String stop_time, String duration){
		setPassNumber(pass_number);
		setStartTime(start_time);
		setStopTime(stop_time);
		setDuration(duration);	
	}
	
	//Pass Number
	public String getPassNumber(){
		return pass_number;
	}
	public void setPassNumber(String pass_number){
		this.pass_number=pass_number;
	}
	
	//Start Time
	public String getStartTime(){
		return start_time;
	}
	public void setStartTime(String start_time){
		this.start_time=start_time;
	}
	
	//Stop Time
	public String getStopTime(){
		return stop_time;
	}
	public void setStopTime(String stop_time){
		this.stop_time=stop_time;
	}
	
	//Duration
	public String getDuration(){
		return duration;
	}
	public void setDuration(String duration){
		this.duration=duration;
	}
}
