package bdiagent;

import org.json.JSONException;
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
		try {
			where = event.getString("where");
			day = event.getInt("day");
			month = event.getInt("month");
			explanation = event.getString("explanation");
			type = event.getString("event-type");
			try {
				period = event.getString("period");
			} catch (JSONException e) {
				// TODO: handle exception
				System.out.println("period not found");
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.err.println("wrong json sent into event class");
		}
		
		
	}
	//Typeına göre importance ayarlayacak
	public void setImportance() {
		
	}
	
}
