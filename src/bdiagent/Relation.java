package bdiagent;

import org.json.*;

public class Relation {
	JSONObject relation;
	String first;
	String second;
	int time;
	String type;
	public Relation(JSONObject relation){
		this.relation=relation;
		fillData();
	}
	public void fillData(){
		try {
		type=relation.getString("type");
		first=relation.getString("first");
		second=relation.getString("second");
		time=relation.getInt("time");
		}
		catch(JSONException e){
			System.err.println("Json problem");
		}
	}
}
