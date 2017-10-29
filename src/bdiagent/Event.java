package bdiagent;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.javafx.runtime.VersionInfo;

public class Event {
	String where;
	int day;
	int month;
	String type;
	String eventType;
	double importance;
	String explanation;
	String period;
	JSONObject event;
	public Event(JSONObject event) {
		this.event=event;
		filldata();
		
	}
	//JSONObjeyi parçalayıp atıcak
	public void filldata() {

		try {
			where = event.getString("where");
			type = event.getString("type");	
			explanation = event.getString("explanation");
			eventType = event.getString("event-type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.err.println("Main Items Json error ");
		}
		try {
			if (event.has("day")){
				day = event.getInt("day");
			}else{
				day = -1;
			}
			
			month = event.getInt("month");
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Day details not found in Json");
		}
		try {
			if (event.has("period")){
				period = event.getString("period");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Period not found in Json");
		}
		
	}
	public String getExplanation() {
		return this.explanation;
	}
	public void setDate(int time) {
		this.day+=time;
	}
	//Typeına göre importance ayarlayacak
	public void setImportance(double imp) {
		this.importance=imp;
	}
	@Override
	public String toString(){
		return explanation+" in day"+day+ " importance is around " + importance  ;
	}
}
