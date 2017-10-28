package bdiagent;

import org.json.JSONObject;

public class Comparison {
	String type;
	String first;
	String second;
	JSONObject comparison;
	public Comparison(JSONObject comp) {
		this.comparison=comp;
	}
	//Fill data like event
	public void fillData() {
		
	}
}
