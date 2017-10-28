package bdiagent;

import org.json.JSONException;
import org.json.JSONObject;

public class Comparison {
	String type;
	String first;
	String second;
	JSONObject comparison;
	public Comparison(JSONObject comp) {
		this.comparison=comp;
		fillData();
		
	}
	//Fill data like event
	public void fillData(){
		try {
		type=comparison.getString("type");
		first=comparison.getString("first");
		second=comparison.getString("second");
		}
		catch(JSONException e){
			System.err.println("Json problem");
		}
	}
}
