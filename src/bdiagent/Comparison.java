package bdiagent;

import org.json.JSONException;
import org.json.JSONObject;

public class Comparison {
	String first;
	String second;
	double amount;
	JSONObject comparison;
	public Comparison(JSONObject comp) {
		this.comparison=comp;
		fillData();
		
	}
	//Fill data like event
	public void fillData(){
		try {
		amount = comparison.getDouble("amount");
		first=comparison.getString("first");
		second=comparison.getString("second");
		}
		catch(JSONException e){
			System.err.println("Json problem");
		}
	}
	public String toString() {
		return "New Belief: "+first+" is more important then "+second+ "with the amount "+amount;
	}
}
