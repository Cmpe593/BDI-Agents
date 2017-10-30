package bdiagent;

import org.json.*;

public class Relation {
	JSONObject relation;
	String first;
	String second;
	int time;
	String firstperiod;
	String secondperiod;
	public Relation(JSONObject relation){
		this.relation=relation;
		fillData();
	}
	public void fillData(){
		try {
		firstperiod=relation.getString("first-period");
		secondperiod=relation.getString("second-period");
		first=relation.getString("first");
		second=relation.getString("second");
		time=relation.getInt("time");
		
		}
		catch(JSONException e){
			System.err.println("Json problem");
		}
	}
	public String toString() {
		if(time>0)
			return "New Belief: "+second+" and its period is "+secondperiod+" should be "+time+" day after "+first+" and is period is "+firstperiod+".";
		else {
			int newtime=-1*time;
			return "New Belief: "+second+" and its period is "+secondperiod+" should be "+newtime+" day before "+first+" and is period is "+firstperiod+".";
		}
	}
}
