package bdiagent;

import org.json.*;

public class Relation {
	JSONObject relation;
	String first;
	String second;
	int time;
	String period;
	public Relation(JSONObject relation){
		this.relation=relation;
		fillData();
	}
	public void fillData(){
		try {
		period=relation.getString("period");
		first=relation.getString("first");
		second=relation.getString("second");
		time=relation.getInt("time");
		}
		catch(JSONException e){
			System.err.println("Json problem");
		}
	}
}
