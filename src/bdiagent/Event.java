package bdiagent;

import org.json.JSONObject;

public class Event {
	String where;
	int day;
	int month;
	String type;
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
		
	}
	//Typeına göre importance ayarlayacak
	public void setImportance() {
		
	}
	
}
