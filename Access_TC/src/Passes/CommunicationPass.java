package passes;

import java.util.Date;

public class CommunicationPass {

	private String pass_number;
	private Date start_time;
	private Date stop_time;
	private float duration;
	private int case_type=0; //Define which kind of lightning case we have.
	private float pass_light_duration=0;
	
	public CommunicationPass(String pass_number, Date start_time, Date stop_time, float duration){
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
	public Date getStartTime(){
		return start_time;
	}
	public void setStartTime(Date start_time){
		this.start_time=start_time;
	}
	
	//Stop Time
	public Date getStopTime(){
		return stop_time;
	}
	public void setStopTime(Date stop_time){
		this.stop_time=stop_time;
	}
	
	//Duration
	public float getDuration(){
		return duration;
	}
	public void setDuration(float duration){
		this.duration=duration;
	}
	
	//Case Type
	public void setCaseType(int case_type){
		this.case_type=case_type;
	}
	public int getCaseType(){
		return case_type;
	}
	
	//Pass Light Duration
	public void setPassLightDuration(float pass_light_duration){
		this.pass_light_duration=pass_light_duration;
	}
	public float getPassLightDuration(){
		return pass_light_duration;
	}
}
