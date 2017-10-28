package bdiagent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.*;
public class Agent extends Thread {
	private String name;
	private int sday;
	private int eday;
	private ArrayList<Belief> beliefs;
	private ArrayList<Desire> desires;
	private ArrayList<Intention> intentions;
	private ArrayList<Event> events;
	private ArrayList<Comparison> comparisons;
	private ArrayList<Relation> relations;
	public  Agent (String name, int startday, int endday){
		this.name = name;
		this.beliefs = new ArrayList<Belief> ();
		this.desires = new ArrayList<Desire>();
		this.intentions = new ArrayList<Intention> ();
		this.sday=startday;
		this.eday=endday;
		new Thread(this).start();
		
	}
	public void run() {
		for(int i=sday;i<=eday;i++) {
			String belief;
			if (i==sday) {
				belief=readInitialBelief();
			}
			else {
				belief = getBeliefFromConsole();
			}
			String[] arr=belief.split("!!!");
			for(int j=0;j<arr.length;j++) {
				System.out.println(arr[j]);
				System.out.println();
				try {
					JSONObject json = new JSONObject(arr[j]);

					try {
						if(json.getJSONObject("event-schedule")!=null){
							Event event = new Event(json.getJSONObject("event-schedule"));
							events.add(event);
						}else if(json.getJSONObject("comparison")!=null){
							Comparison comparison = new Comparison(json.getJSONObject("comparison"));
							comparisons.add(comparison);
						}else if(json.getJSONObject("relation")!=null) {
							Relation relation = new Relation(json.getJSONObject("relation"));
							relations.add(relation);
						}else if(json.getJSONObject("change")!=null) {
							JSONObject change = json.getJSONObject("change");
							if(change.getString("type").equalsIgnoreCase("update-event")){
								int number= getEventID(change.getString("explanation"));
								if(number!=-1) {
									events.get(number).setDate(change.getInt("time"));
								}
							}else if(change.getString("type").equalsIgnoreCase("drop-event")) {
								int number= getEventID(change.getString("explanation"));
								if(number!=-1) {
									events.remove(number);
								}
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						System.err.println("json cannot be parsed");
					}

					//Buraya json dataların isminden anlayıp kalan objeyi ilgili classa koyucaz JSONObject java diye arattır görürsün functionlarını
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public int getEventID(String a) {
		for (int i = 0; i < events.size(); i++) {
			if(a.equalsIgnoreCase(events.get(i).getExplanation())) {
				return i;
			}
		}
			
		
		return -1;
	}
	public String readInitialBelief() {
		File file = new File("belief.txt");
		try {
			Scanner sc = new Scanner(file);
			StringBuffer sb= new StringBuffer();
			while(sc.hasNextLine()) {
				sb.append(sc.nextLine());
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "File Not Found";
		}
	}
	public String getBeliefFromConsole() {
		Scanner sc = new Scanner(System.in);
		StringBuffer sb= new StringBuffer();
		while(sc.hasNextLine()) {
			sb.append(sc.nextLine());
		}
		return sb.toString();
		
	}

}
